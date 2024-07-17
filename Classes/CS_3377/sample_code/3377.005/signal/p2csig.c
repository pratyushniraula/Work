#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>


int main(int argc, char* argv[])
{

	int cpid = fork();
	if (cpid == 0){
		while(1);
	}
	else {
		sleep(10);
		kill(cpid, 9);
		printf("kill 9 sent to child \n");
		fflush(stdout);
		sleep(10);

	}
}
