#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>


int main(){
     
    int rc = fork();

    if (rc == 0) { // child (new process)
        printf("hello, I am child (pid:%d)\n", (int) getpid());
		char* myargs[3];
		myargs[0] = strdup("wc");
		myargs[1] = strdup("./xxxxx.c");
		myargs[2] = NULL;

		// check the return value of strdup for error
		if ((myargs[0] == NULL) || (myargs[1] == NULL)){
			perror("main: strdup failed:");
			exit(-1);
		}

		if (execvp(myargs[0], myargs) == -1){
			perror("main: execvp failed:");
		//	exit(-1);
		}
		printf(" Never happens \n");

    } else { 		// parent goes down this path 
        int wc = wait(NULL);
        printf("hello, I am parent of %d (wc:%d) (pid:%d)\n",
        		rc, wc, (int) getpid());
    }
    return 0;
}

