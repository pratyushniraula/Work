#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>

void sig_handler(int signo)
{
	if (signo == SIGINT)
    	printf("\nHAHA!! Caught sig %d \n", signo);
}


int main(int argc, char* argv[])
{

	int signum = atoi(argv[1]);
	if (signal(signum, sig_handler) == SIG_ERR){
  		printf("\ncan't catch sig %d\n", signum);
		exit(-1);
	}
	while(1);
}
