#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>


int main(){
	
	printf("Hello before fork...my pid is %d \n", getpid());
	int x = 10;
    int rc = fork();
	if (rc == 0){ // code for child only
		printf("I am CHILD...x %d \n", x);
		x = 100;
	} 
	else { // code for parent only
		wait(); // wait for child to exit
		printf("I am PARENT...x %d \n", x);
		
	}
}

