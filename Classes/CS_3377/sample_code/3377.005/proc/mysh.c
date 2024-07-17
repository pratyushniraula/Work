#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>

int main(){
     
	char *myargs[3];
	char line[10];
	size_t len = 0;
    ssize_t read;

	myargs[0] = line;
	myargs[1] = NULL;
    printf("mysh> ");
	while (1){
		scanf("%s", line);

    	int rc = fork();

    	if (rc == 0) { // child (new process)
			execvp(myargs[0], myargs);
			printf(" Never happens \n");

    	} else { 		// parent goes down this path (main)
       		int wc = wait(NULL);
        	printf("mysh> ");
    	}
    }
    return 0;
}

