#include <stdio.h>
#include <stdlib.h>


int main(){
     
    int rc = fork();

    if (rc == 0) { // child (new process)
        printf("hello, I am child (pid:%d)\n", (int) getpid());
		while(1);

    } else { 		// parent goes down this path (main)
        printf("hello, I am parent of %d  (pid:%d). I am not waiting .\n", rc, (int) getpid());
    }
    return 0;
}

