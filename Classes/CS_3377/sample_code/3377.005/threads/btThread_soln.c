#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/syscall.h>

#include "../../common_threads.h"

void *tree(void *arg);
int gettid();

int 
main(int argc, char *argv[]) 
{                    

	int ht;

    if (argc != 2) {
		fprintf(stderr, "usage: main #threads \n");
		exit(1);
    }

	ht = atoi(argv[1]);

    pthread_t p1;
	Pthread_create(&p1, NULL, tree, &ht);
	Pthread_join(p1, NULL);
    return 0;
}

void *tree(void *arg) {

	pthread_t p1, p2;
	int ht = *((int *) arg);
	if (ht == 0){
    	printf("Leaf Thread (id: %d) at height %d\n", (int)gettid(), ht);
	}
	else {
    	printf("Int. Thread (id: %d) at height %d\n", gettid(), ht);
		ht--;
		Pthread_create(&p1, NULL, tree, (void *) &ht);
		Pthread_create(&p2, NULL, tree, &ht);
		Pthread_join(p1, NULL);
		Pthread_join(p2, NULL);
	}
    return NULL;
}

int
gettid(){
	return (syscall(SYS_gettid));
}
