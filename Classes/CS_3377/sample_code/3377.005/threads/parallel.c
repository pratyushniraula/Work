#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#include "common.h"
#include "common_threads.h"

void *worker(void *arg);

int main(int argc, char *argv[]) {                    

	int i, cnt;

    if (argc != 2) {
		fprintf(stderr, "usage: main #threads \n");
		exit(1);
    }

	cnt = atoi(argv[1]);
    printf("main: begin\n");

    pthread_t *pth;
	pth = (pthread_t *) malloc (sizeof(pthread_t) * cnt );
	double start = GetTime();
	for (i = 0; i < cnt; i++){
    	Pthread_create(&pth[i], NULL, worker, &i); 
	}

    // join waits for the threads to finish
	for (i = 0; i < cnt; i++){
    	Pthread_join(pth[i], NULL);
	}

	double end = GetTime();
    printf("main: end. time = %f \n", (end - start));
    return 0;
}

void *worker(void *arg) {

	int* id = (int *) arg;

    printf("Thread %d\n", *id);
    for (int i = 0; i < 1000000000; i++);
    return NULL;
}

