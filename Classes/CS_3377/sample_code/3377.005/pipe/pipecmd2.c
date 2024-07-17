#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>


void err_exit(char *msg);

int
main(int argc, char *argv[])
{
    int fd[2];
    int fd2[2];
    pid_t cpid1, cpid2;

    if (argc != 2) {
     fprintf(stderr, "Usage: %s <pathname>\n", argv[0]);
     exit(EXIT_FAILURE);
    }

    if (pipe(fd) == -1) 
        err_exit("pipe");

	cpid1 = fork();
	if (cpid1 < 0){
		err_exit("fork cpid1 failed");		
	}
	if (cpid1 == 0){ // child 1
		close(fd[0]); // not reading from pipe
		if (dup2(fd[1], STDOUT_FILENO) == -1){
			err_exit("dup2 failed in Child 1");
		}
		close(fd[1]); // write end is no longer required because dup2
		char* myargs[] = {"cat", argv[1], NULL}; 
		execvp(myargs[0], myargs);
		err_exit("execvp failed in Child 1");
	}
	close(fd[1]); // parent no longer needs write end
    if (pipe(fd2) == -1) 
        err_exit("pipe");
	// only parent gets here
    cpid2 = fork();
    if (cpid2 < 0){
        err_exit("fork cpid2 failed");
    }
	if (cpid2 == 0){
		close(fd[1]); // wc doesnt write to pipe
		if(dup2(fd[0], STDIN_FILENO) == -1){
        	err_exit("dup2 in child 2 failed");
		}
		close(fd[0]); // no longer required because dup2
		close(fd2[0]);
		// redirect stdout to pipe 2
		if (dup2(fd2[1], STDOUT_FILENO) == -1){
			err_exit("dup2 failed in stdout of child 2");
		}
		close(fd2[1]);
		char* myargs[] = {"cat", NULL}; 
		execvp(myargs[0], myargs);
		err_exit("execvp failed in Child 1");
	}
	close(fd2[1]);
    int cpid3 = fork();
    if (cpid3 < 0){
        err_exit("fork cpid3 failed");
    }
	if (cpid3 == 0){
		//close(fd2[1]); // wc doesnt write to pipe
		if(dup2(fd2[0], STDIN_FILENO) == -1){
        	err_exit("dup2 in child 3 failed");
		}
		close(fd2[0]); // no longer required because dup2
		char* myargs[] = {"wc", NULL}; 
		//char* myargs[] = {"sleep", "10",  NULL}; 
		execvp(myargs[0], myargs);
		err_exit("execvp failed in Child 1");
	}
	close(fd[0]);
/*
	waitpid(cpid1, NULL, 0); // wait for child 1 to terminate
	waitpid(cpid2, NULL, 0); // wait for child 2 to terminate
	waitpid(cpid3, NULL, 0); // wait for child 3 to terminate
*/
	while(wait(NULL) >= 0);
}

void
err_exit(char *msg)
{
	perror(msg);
	exit(EXIT_FAILURE);
}

