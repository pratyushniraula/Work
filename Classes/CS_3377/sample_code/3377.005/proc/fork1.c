#include <stdio.h>
#include <stdlib.h>


int main(){
	
	int x = 10;
	printf("I am the parent... my pid is %d \n", getpid());
    fork();
	printf("who am I? ... my pid is %d x = %d \n", getpid(), x);

}

