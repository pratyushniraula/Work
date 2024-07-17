#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#include "common.h"
#include "common_threads.h"

void *worker(void *arg);

int 
main(int argc, char *argv[]) 
{                    

	int i, cnt;

    if (argc != 2) {
		fprintf(stderr, "usage: main #threads \n");
		exit(1);
    }

	cnt = atoi(argv[1]);
    printf("main: begin\n");

    pthread_t *pth;
	pth = (pthread_t *) malloc (sizeof(pthread_t) * cnt );

    int *store;
	store = (int *) malloc (sizeof(int) * cnt );
	for (i = 0; i < cnt; i++){
		store[i] = i;
    	Pthread_create(&pth[i], NULL, worker, (void *) &store[i]); 
	}
	Pthread_join(pth[0], NULL);
//	sleep(100);


    return 0;
}

void *worker(void *arg) {

	int* id = (int *) arg;

    printf("Thread %d\n", *id);
	while(1);
    return NULL;
}

