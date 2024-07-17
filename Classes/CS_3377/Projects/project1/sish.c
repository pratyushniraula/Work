#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdbool.h>

//this struct makes it so that individual words are stored into here for each command, sort of like its own argv
typedef struct argumentArray{
	//array of words for each command
	char **Arguments;
	//number of arguments
	int numOfArguments;
}argumentArray;

//this struct allows there to be multiple argv's (commands) 
typedef struct CommandStruct{
	//array of commands
	argumentArray *commands;
	//number of commands
	int numOfCommands;
}CommandStruct;

bool isNumber(char* s){
	//check if the string is a number
	for(int i = 0; i < strlen(s); i++){
		if(isdigit(s[i]) == 0){
			return false;
		}
	}
	return true;
}

void spaceRemover(char* s) {
	//removes spaces from the beginning and end of the string
    char* end = s + strlen(s) - 1;
    char* start = s;
	//remove spaces from the beginning for every space present
    while (isspace((unsigned char)*start)) {
        start++;
    }
	//remove spaces from the end for every space present
    while (end > start && isspace((unsigned char)*end)) {
        end--;
    }
	//move the string to the beginning until the end
    while (start <= end) {
        *s++ = *start++;
    }
	//add null terminator at the end
    *s = '\0'; 
}

//function that tokenizes spaces and puts them inside of an argument array
void argumentTokenizer(char* line, argumentArray *A){
	char *word, *breakingpointWord; //breakingpointWord is used to keep track of the position of the last token found and word is used to keep track of the current token
	char *space = " "; //space is the delimiter
	A->numOfArguments = 0; //initialize the number of arguments to 0
	A->Arguments = malloc(sizeof(char*)); //allocate space for the first argument
	if(A->Arguments == NULL){
		//handle error
		fprintf(stderr, "Error: argumentArray malloc failed\n");
		free(A->Arguments); //free the memory
		exit(EXIT_FAILURE);
	}

	//tokenize the the command by spaces
	for(word = strtok_r(line, space, &breakingpointWord); word; word = strtok_r(NULL, space, &breakingpointWord)){
		//allocate space for the next argument
		A->Arguments = realloc(A->Arguments, (A->numOfArguments + 1)* sizeof(char*));
		if(A->Arguments == NULL){
			//handle error for realloc
			fprintf(stderr, "Error: realloc failed\n");
			free(A->Arguments);
			exit(EXIT_FAILURE);
		}
		//remove spaces from the beginning and end of the word and store it in the argument array
        spaceRemover(word);
		A->Arguments[A->numOfArguments] = strdup(word);
		A->numOfArguments++;
		//error handling for strdup
		if(A->Arguments[A->numOfArguments-1] == NULL){
			//handle error
			fprintf(stderr, "Error: strdup failed\n");
			free(A->Arguments);
			exit(EXIT_FAILURE);
		}
	}

		//allocate space for null so that execvp can work
	A->Arguments = realloc(A->Arguments, (A->numOfArguments + 1)* sizeof(char*));
		
		if(A->Arguments == NULL){
			//handle error
			fprintf(stderr, "Error: realloc failed for adding NULL in argumentArray\n");
			free(A->Arguments);
			exit(EXIT_FAILURE);
		}
		//add null to the end of the argument array
		A->Arguments[A->numOfArguments] = NULL;
}

//function called whenever cd is called
void changeDirectory(CommandStruct *C){
	//cd command
	if(strcmp(C->commands[0].Arguments[0], "cd") == 0){
		//check if there is only 1 argument
		if(C->commands[0].numOfArguments == 2){
			//check if the directory exists
			if(chdir(C->commands[0].Arguments[1]) != 0){
				fprintf(stderr, "Error: %s is not a valid directory\n", C->commands[0].Arguments[1]);
			}
			//print out the current working directory so that the user knows where they are
			printf("Current working directory: %s\n", getcwd(NULL, 0));
			
		}
		else{
			//error handling
			printf("cd command takes 1 argument\n");
		}
	}
}
//execute the non built in commands
void executeCommand(CommandStruct *C){
	int fd[2]; //file descriptor
	int input = 0; //file descriptor for read but file decriptor for read was not working. Yet when I made it equal to input, it worked
	for(int i = 0; i < C->numOfCommands; i++){
		//create a pipe and check for error
		if(pipe(fd) == -1){
			//handle error
			fprintf(stderr, "Error: pipe failed\n");
			exit(EXIT_FAILURE);
		}
		//fork and check for error
		pid_t pid = fork();
		if(pid < 0){
			//handle error
			fprintf(stderr, "Error: fork failed\n");
			exit(EXIT_FAILURE);
		}

		//child process
		if(pid == 0){
			//check if it is the first command
			if(i != 0){
				//if it is not the first command, set the input to the file descriptor
				dup2(input, STDIN_FILENO);
			}
			//check if it is the last command
			if(i != C->numOfCommands - 1){
				//if it is not the last command, set the output to the file descriptor
				dup2(fd[1], STDOUT_FILENO);
			}
			//close the file descriptors
			close(fd[1]);
			close(fd[0]);
			//execute the command
			execvp(C->commands[i].Arguments[0], C->commands[i].Arguments);
			//if program runs past execvp handle error
			fprintf(stderr, "Error: %s is not a recognized command\n", C->commands[i].Arguments[0]);
			exit(EXIT_FAILURE);
		}
		else{
			//parent
			//close the file descriptor for writing since it is not necessary
			close(fd[1]);
			//wait for the child process to finish
			wait(NULL);
			//set the input to the file descriptor since that worked but the other way did not work for some reason
			input = fd[0];
		}
	}
}

void history(CommandStruct C, CommandStruct *History, int *historyOffset, int historySize){
		//history command with no arguments
	if(strcmp(C.commands[0].Arguments[0], "history") == 0 && C.commands[0].numOfArguments == 1){
			//print out the history if the offset is less than the history size
			if((*historyOffset) < historySize){
				for(int i = 0; i < (*historyOffset); i++){ //goes through History array
					//prints out the history number int he front
					printf("%d\t", i); 

					for(int j = 0; j < History[i].numOfCommands; j++){ //goes through each command in the command array
						for(int k = 0; k < History[i].commands[j].numOfArguments; k++){ //goes through each argument in the argument array
							printf("%s ", History[i].commands[j].Arguments[k]); //prints out each argument
						}
						if(j < History[i].numOfCommands - 1) //if there is a pipe, print out a pipe
							printf("| "); //seperate each command with a pipe if there is more than one command in the same line
					}
					printf("\n"); //seperate each command with a new line
				}
			}
			//print out the history if the offset is greater than the history size so that it can loop around
			else if((*historyOffset) >= historySize){
				//print out the history based off history size, since if offset is greater than history size, the array will be full and will loop around
				for(int i = 0; i < historySize; i++){
					//print the number of the history command
					printf("%d\t", i);
					
					for(int j = 0; j < History[(*historyOffset + i)%historySize].numOfCommands; j++){
						for(int k = 0; k < History[(*historyOffset + i)%historySize].commands[j].numOfArguments; k++){
							//print out arguments
							printf("%s ", History[(*historyOffset + i)%historySize].commands[j].Arguments[k]);
						}
						if(j < History[(*historyOffset + i)%historySize].numOfCommands - 1)
							//print out a pipe if there is more than one command in the same line
							printf("| ");
					}
					//print out a new line to seperate each command
					printf("\n");
				}
			}
	}
		//check if history has an argument
		if(strcmp(C.commands[0].Arguments[0], "history") == 0 && C.commands[0].numOfArguments == 2){
			
			//check if the argument is a number
			if(isNumber(C.commands[0].Arguments[1])){
				int num = atoi(C.commands[0].Arguments[1]); //convert the argument to an integer
				//error checks before running history [offset]
				if(num >= historySize || num >= (*historyOffset)){
					printf("Error: history offset is greater than history size\n");
				}
				else if (num < 0){
					printf("Error: history offset is less than 0\n");
				}
				else{
					//execute the command at the given number relative to the current offset
					int innerBuiltInCommands = 0;
					if((*historyOffset) > historySize){
						//execute if it is cd
						if(strcmp(History[(*historyOffset + num)%historySize].commands[0].Arguments[0], "cd") == 0){
							innerBuiltInCommands = 1;
							changeDirectory(&History[(*historyOffset + num)%historySize]);
						}
						//execute if it is history
						if(strcmp(History[(*historyOffset+num)%historySize].commands[0].Arguments[0], "history") == 0){
							innerBuiltInCommands = 1;
							history(History[num], History, historyOffset, historySize);
						}
						//execute non built in command
						if(innerBuiltInCommands == 0){
							executeCommand(&History[(*historyOffset + num)%historySize]);
						}
					}
					else{
						//execute if it is cd
						if(strcmp(History[num].commands[0].Arguments[0], "cd") == 0){
							innerBuiltInCommands = 1;
							changeDirectory(&History[num]);
						}
						//execute if it is history
						if(strcmp(History[num].commands[0].Arguments[0], "history") == 0){
							innerBuiltInCommands = 1;
							history(History[num], History, historyOffset, historySize);
						}
						
						//execute non built in command
						if(innerBuiltInCommands == 0){
							executeCommand(&History[num]);
						}
					}
					

				}
				
			}
			//check if the argument is -c
			else if(strcmp(C.commands[0].Arguments[1], "-c") == 0){
				//set the history offset to 0 if -c is called so that the history is "cleared"
				//this in turn will not have memory leaks since the history array is freed at the end of the program anyway
				//but this ensures that history offset starts at the interval at which 
				*historyOffset = 0;
			}
			//if the argument is not a number or -c, print out an error message
			else{
				printf("history command either takes no arguments, -c, or an integer offset\n");
			}
		}

}

int main(){
	CommandStruct *History = NULL; //history array
	int historySize = 100; //predefined history size
	char* line = NULL; //line for user input
	size_t sizeOfLine; //size of the line
	char *command; //command for strtok_r that is used to tokenize the line by pipes
	char *piper = "|"; //pipe delimiter
	char *breakingpointCmd; //breaking point for strtok_r
	int historyOffset = 0; //history offset to keep track of the history array looping around
	int didLoopRun = 0; //check if the loop ran so that memory can be freed if it did
    
	//label so that jump is possible
	loop:
	while(1){
		//check if history ran so that it does not run twice
		bool histRun = false;
		int BuiltInCommands = 0; //check if built in commands ran so that execvp does not run
		
		//gathering user input
		printf("sish> ");
		size_t read = getline(&line, &sizeOfLine, stdin);

	//check if getline failed all the while checking if it is the end of the file
	if(read == -1){
		//handle error
		if(feof(stdin)){
			printf("End of file reached\n");
		}
		else{
			fprintf(stderr, "Error: getline failed\n");
		}
		break;
	}

		//make it so that spaces are removed from the beginning and end of the line
		spaceRemover(line);

		//replace newline with null terminator at the end of the line
		line[strcspn(line, "\n")] = '\0';
		if(strcmp(line, "\0") == 0 || strcmp(line, "\n") == 0){
			goto loop;
		}
		if(strcmp(line, "exit") == 0){
			break;
		}
		didLoopRun = 1; //at this point the loop ran so that memory can be freed at the end of the program
		CommandStruct C; //C is the command struct that holds the commands and arguments for this iteration of the loop
		C.numOfCommands = 0; //initialize the number of commands to 0
		C.commands = malloc(sizeof(argumentArray)); //allocate space for the first command
		if(C.commands == NULL){
			//handle error
			fprintf(stderr, "Error: malloc failed to make space for C.commands\n");
			free(C.commands);
			exit(EXIT_FAILURE);
		}
		
		
		//tokenize the line by pipes
		for(command = strtok_r(line, piper, &breakingpointCmd); command; command = strtok_r(NULL, piper, &breakingpointCmd)){
			//allocate space for the next command
			C.commands = realloc(C.commands, (C.numOfCommands + 1)*sizeof(argumentArray));
			if(C.commands == NULL){
				//handle error
				fprintf(stderr, "Error: realloc failed on strtok_r\n");
				free(C.commands);
				exit(EXIT_FAILURE);
			}
			//make argument array to hold each command and its arguments
			argumentArray A;
			//initialize the number of arguments to 0
			A.numOfArguments = 0; 
			//tokenize the command by spaces
			argumentTokenizer(command, &A);
			//store the command and its arguments into the command struct
			C.commands[C.numOfCommands] = A;
			//increment the number of commands
			C.numOfCommands++;

		}
		//check if the command is history [offset]
		if(strcmp(C.commands[0].Arguments[0], "history") == 0 && C.commands[0].numOfArguments == 2){
			if(isNumber(C.commands[0].Arguments[1])){
				//call history function
				history(C, History, &historyOffset, historySize);
				//set histRun to true so that history along with other commands do not run in the same iteration
				histRun = true;
			}

		}
		//check if history is NULL
		if(History == NULL){
			//allocate memory for history
			History = malloc(historySize*sizeof(CommandStruct));
			if(History == NULL){
				//handle error
				fprintf(stderr, "Error: CommandStruct malloc failed\n");
				free(History);
				exit(EXIT_FAILURE);
			}
			//add the command to history
			History[historyOffset%historySize] = C;
			historyOffset++;
		}
		else{
			//check if history is full
			if(historyOffset > historySize){
				//deallocate memory from the command previously stored in history[historyOffset%historySize]
				for(int j = 0; j < History[historyOffset%historySize].numOfCommands; j++){
					free(History[historyOffset%historySize].commands[j].Arguments);
				}
				free(History[historyOffset%historySize].commands);
				//allocate memory for the commands that are going to be stored in history
				History[historyOffset%historySize].commands = malloc(C.numOfCommands * sizeof(CommandStruct));
				//malloc error handling
				if(History[historyOffset%historySize].commands == NULL){
					//handle error
					fprintf(stderr, "Error: CommandStruct malloc failed\n");
					free(History[historyOffset%historySize].commands);
					exit(EXIT_FAILURE);
				}
				//copy the commands into the history array
   				History[historyOffset%historySize].numOfCommands = C.numOfCommands;
    			for(int i = 0; i < C.numOfCommands; i++){
					//allocate memory for the arguments that are going to be stored in history
					History[historyOffset%historySize].commands[i].Arguments = malloc(C.commands[i].numOfArguments * sizeof(char*));
					//malloc error handling
					if(History[historyOffset%historySize].commands[i].Arguments == NULL){
						//handle error
						fprintf(stderr, "Error: CommandStruct malloc failed\n");
						free(History[historyOffset%historySize].commands[i].Arguments);

						exit(EXIT_FAILURE);
					}
					//copy the arguments into the history array
        			for(int j = 0; j < C.commands[i].numOfArguments; j++){
						History[historyOffset%historySize].commands[i].Arguments[j] = strdup(C.commands[i].Arguments[j]);
					}
					//copy the number of arguments into the history array
					History[historyOffset%historySize].commands[i].numOfArguments = C.commands[i].numOfArguments;
				}
				//increment the history offset so that it can loop around
				historyOffset++;
			}
			else{
				//if history offset is less than history size, copy the commands into the history array since history is not full
				History[historyOffset%historySize] = C;
				historyOffset++;
			}
		}
		//histrun is made so that history does not run twice since it runs before adding itself into history if it is history [offset]
		//also used so that execvp is not run twice if history in general is called
		//execute if it is history
		if(histRun == false){
			if(strcmp(C.commands[0].Arguments[0], "history") == 0){
				history(C, History, &historyOffset, historySize);
				BuiltInCommands = 1;
			}
		}
		
		//execute if it is cd
		if(histRun == false){
			if(strcmp(C.commands[0].Arguments[0], "cd") == 0){
				BuiltInCommands = 1;
				changeDirectory(&C);
			}

			//execute non built in command
			if(BuiltInCommands == 0){
				executeCommand(&C);
			}
		}

	}

	//freeing memory
	if(didLoopRun == 1){
		printf("Exiting sish\n");
		printf("Freeing memory\n");
		printf("...\n");
		//freeing memory
		for(int i = 0; i < historySize; i++){
			for(int j = 0; j < History[i].numOfCommands; j++){
				free(History[i].commands[j].Arguments);
			}
			free(History[i].commands);
		}
		free(History);
		free(line);
		printf("Memory freed\n");
	}
	printf("Exiting sish\n");
	exit(EXIT_SUCCESS);
}
