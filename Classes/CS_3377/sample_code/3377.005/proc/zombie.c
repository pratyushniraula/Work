#include <stdio.h>
#include <stdlib.h>


int main(){
     
    int rc = fork();

    if (rc == 0) { // child (new process)
        printf("hello, I am child (pid:%d)\n", (int) getpid());

    } else { 		// parent goes down this path (main)
        printf("hello, I am parent of %d  (pid:%d). I am sleeping.\n", rc, (int) getpid());
		printf("waiting \n");
		sleep(10);
		rc = wait();
		sleep(10);
    }
    return 0;
}

