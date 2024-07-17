#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/syscall.h>

#include "common_threads.h"

void *tree(void *arg);
int gettid();

int 
main(int argc, char *argv[]) 
{
	int ht;

    if (argc != 2) {
		fprintf(stderr, "usage: htree height \n");
		exit(1);
    }

	ht = atoi(argv[1]);

    pthread_t p1;
	Pthread_create(&p1, NULL, tree, &ht);
	Pthread_join(p1, NULL);
    return 0;
}

// It is easier to write a recursive function than an iterative one.
// Remember arg is an address in the stack of the caller.
// I wouldnt modify the value at that location.
void* 
tree(void* arg) 
{
	//casting arg into an int pointer and putting that value as height
	int height = *(int*)arg;
	pid_t curtid = gettid(); //tid_t did not work, but this stores the current tid
	
	//if height is less than 0 then it is not possible to execute the program in the manner it is meant for
	if(height < 0){
	printf("error, height is less than 0\n");
	return NULL;
	}

	//if height is greater than 0 it is an internal node
	if(height > 0){
		//start off by printing the current thread and current height before making modifications
		printf("Int. Thread (id: %d) at height %d\n", curtid, height);
		
		//create new thread values for the right and left subtrees or leafs
		pthread_t l;
		pthread_t r;
	
		//allocate memory for the left and right child heights
		//threads will manage the memory
		int *ChildHeightLeft = malloc(sizeof(int));
		*ChildHeightLeft = height-1;
		int *ChildHeightRight = malloc(sizeof(int));
		*ChildHeightRight = height-1;
		
		//create the threads for the left and right subtrees
		Pthread_create(&r, NULL, tree, ChildHeightRight);
		Pthread_create(&l, NULL, tree, ChildHeightLeft);

		//wait for the threads to finish
		Pthread_join(r, NULL);
		Pthread_join(l,NULL);
	}
	else if(height == 0){
		//if height is 0 then it is a leaf node
		printf("Leaf Thread (id: %d) at height %d\n", curtid, height);
	}

	return NULL;
}

int
gettid()
{
	return (syscall(SYS_gettid));
}
