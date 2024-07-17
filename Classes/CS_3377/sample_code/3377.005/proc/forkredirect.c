#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>


int main(){
	
     
    int rc = fork();
	if (rc == 0){// child
		printf("Child: my pid is %d \n", getpid());
		close(1);
		int fd = open("out", O_TRUNC|O_RDWR|O_CREAT, S_IRWXU);
  		if (fd == -1) {
    		perror("open failed");
    		exit(EXIT_FAILURE);
  		}	
		printf("file desc %d\n", fd);

		char *myargs[] = {"ls", 0};
		execvp(myargs[0], myargs);
		printf("EXEC FAILED");
	}
	else { // parent takes this path
		printf("PARENT: I am waiting for my child to finish \n");
		int ret = wait(NULL);
		printf("PARENT: Child %d finished \n", ret);
	}

    return 0;
}

