#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#include "common_threads.h"

void* mythread(void *arg);
 
struct record {
	char* n;
	int id;
};

int main(int argc, char *argv[]) {                    

	struct record r;

    if (argc != 1) {
	fprintf(stderr, "usage: main\n");
	exit(1);
    }

    printf("main: begin\n");

    pthread_t p1, p2;
	r.n = "A";
	r.id = 1;
    Pthread_create(&p1, NULL, mythread, &r); 
	r.n = "B";
	r.id = 2;
    Pthread_create(&p2, NULL, mythread, &r);

    // join waits for the threads to finish
    Pthread_join(p1, NULL); 
    Pthread_join(p2, NULL); 

    printf("main: end\n");
    return 0;
}

void* mythread(void* arg) {
	struct record  *a = (struct record*) arg;

    printf("Thread %s \t %d \n", a->n, a->id);
    return NULL;
}
