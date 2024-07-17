#include <stdio.h>
#include <stdlib.h>


int
main(int argc, char *argv[]){

	int i,n, rc;
	
	n  = 3;
	for (i = 1; i <=n; i++){
		rc = fork();
	}
	printf("I am (%d) and rc is %d \n", getpid(), rc );

	return 0;
}

