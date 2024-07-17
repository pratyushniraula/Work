#include <stdio.h>
#include <stdlib.h>


int main(){
	
	int x  = getpid();
	fork();
	// sleep(100);
	if (x == getpid())
		printf("I am parent %d \n", x);
	else
		printf("I am child %d  \n", getpid());

}

