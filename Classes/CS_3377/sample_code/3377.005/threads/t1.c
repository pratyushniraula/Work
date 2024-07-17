#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#include "common.h"
#include "common_threads.h"

void *tA(void *arg);
void *tB(void *arg);
 

int main(int argc, char *argv[]) {                    
    if (argc != 1) {
	fprintf(stderr, "usage: main\n");
	exit(1);
    }

    printf("main: begin\n");

    pthread_t p1, p2;
	double start = GetTime();
    Pthread_create(&p1, NULL, tA, "A"); 
    Pthread_join(p1, NULL); 
    Pthread_create(&p2, NULL, tB, "B");

    // join waits for the threads to finish
    Pthread_join(p2, NULL); 
	double end = GetTime();
    printf("main: end. time = %f \n", (end - start));
    return 0;
}

void *tA(void *arg) {

	char *a = (char *) arg;

    printf("Thread %s\n", a);
    Spin(2);
    return NULL;
}

void *tB(void *arg) {

	char *a = (char *) arg;

    printf("Thread %s\n", a);
    Spin(2);
    return NULL;
}
