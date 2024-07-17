#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#include "common_threads.h"

void* threadA(void *arg);
void* threadB(void *arg);
 

int main(int argc, char *argv[]) {                    

	int* ret;
    if (argc != 1) {
	fprintf(stderr, "usage: main\n");
	exit(1);
    }

    printf("main: begin\n");

    pthread_t p1, p2;
    Pthread_create(&p1, NULL, threadA, "A"); 
    Pthread_create(&p2, NULL, threadB, "B");

    // join waits for the threads to finish
    Pthread_join(p1, (void**) &ret); 
	printf("returned by th A: %d \n", *ret);
    Pthread_join(p2, (void**) &ret); 
	printf("returned by th B: %d \n", *ret);

    printf("main: end\n");
    return 0;
}

void* threadA(void* arg) {
	char *a = (char *) arg;
	int *ret;
	int ret1;

	ret = malloc(sizeof(int));
	*ret = 9999;
	ret1 = 9999;
    printf("In Thread %s\n", a);
	//pthread_exit(&ret1);	
	pthread_exit(ret);	
    return NULL;
}
void* threadB(void* arg) {
	char *a = (char *) arg;
	int *ret;

	ret = malloc(sizeof(int));
	*ret = 1111;
    printf("In Thread %s\n", a);
	pthread_exit(ret);	
    return NULL;
}
