#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*
 * This program counts the number of sentences in a given string and
 * the number of tokens in each sentence.
 * Terminates when user enters BYE
 */

int
main(int argc, char *argv[])
{

	// define the required variables
	int32_t tokenCount = 0;
	int32_t lineCount = 0;
	size_t sizeOfLine = 0;
	char *line = NULL;
	char *word;
	char *sep = ".?!";
	char *breakingPointLine;
	char *sentence;
	char *breakingPointWord;
	char *sep2 = " ";
	
	loop:
	while (1) {
		printf("Enter the string: ");
		// use getline() to get the input string
		getline(&line, &sizeOfLine, stdin);
		
		line[strcspn(line, "\n")] = '\0';	
		if (strcmp(line,"\n") == 0 || strcmp(line,"\0") == 0){
			goto loop;
		}

		else if(strcmp(line, "BYE") == 0){ break;}

		// The input string consists of several sentences separated by '.'
		// Each sentence consists of several tokens separated by ' ' (space).
		// Using strtok_r() find the number of sentences and the number of tokens
		for(sentence = strtok_r(line, sep, &breakingPointLine); sentence; sentence = strtok_r(NULL, sep, &breakingPointLine)){
			lineCount++;

			for(word = strtok_r(sentence, sep2, &breakingPointWord); word; word = strtok_r(NULL, sep2, &breakingPointWord)){
			tokenCount++;
			}
			printf("sentence %d: tokens: %d\n", lineCount, tokenCount);
			tokenCount = 0;
		}
		lineCount = 0;
		
		// in each sentence. HINT: man strtok and look at the sample program at the end.
	
		// Print the results.
		
		
		
		// If the first token is BYE, break from the while loop (check strcmp/strncmp)
		

		//end of while loop so I deallocate everything
		free(line);
		line = NULL;
		sizeOfLine = 0;
	}

    exit(EXIT_SUCCESS);
}
