#include <stdio.h>
#include <stdlib.h>


int
main(int argc, char *argv[]){

	int i,n, rc;
	
	n  = 3;
	for (i = 1; i <=n; i++){
		rc = fork();
		if (rc == 0) 
			break;
	}
	printf("I am (%d) and rc is %d \n", getpid(), rc );
	sleep(120);
	return 0;
}

