#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#include "common.h"
#include "common_threads.h"

int max;
int counter = 0; // shared global variable

pthread_mutex_t m;

void *mythread(void *arg); 

                                                                             
int main(int argc, char *argv[]) {                    

    if (argc != 2) {
	fprintf(stderr, "usage: main <loopcount>\n");
	exit(1);
    }
    max = atoi(argv[1]);
    printf("main: begin [counter = %d] \n", counter );

    Pthread_mutex_init(&m, NULL);


    pthread_t p1, p2;
    Pthread_create(&p1, NULL, mythread, "A"); 
    Pthread_create(&p2, NULL, mythread, "B");

    // join waits for the threads to finish
    Pthread_join(p1, NULL); 
    Pthread_join(p2, NULL); 
    printf("main: done\n [counter: %d]\n [should be: %d]\n", 
	   counter, max*2);
    return 0;
}

void *mythread(void *arg) {
    char *letter = arg;
    int i; 

    printf("%s: begin \n", letter);
    for (i = 0; i < max; i++) {
		Pthread_mutex_lock(&m);
		counter = counter + 1; // shared: only one
		Pthread_mutex_unlock(&m);
		// do some work
    }
    printf("%s: done\n", letter);
    return NULL;
}
