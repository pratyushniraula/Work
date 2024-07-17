/*
 * Write your name and netid
 * Write your partner's name and netid
 * Pratyush Niraula
 * pxn210033
 * Karl Azangue
 * kka210001
*/

//30 digits can be stored by 3 uint32_t variables
#include <stdio.h>     
#include <stdlib.h>   
#include <stdint.h>  
#include <inttypes.h>  
#include <errno.h>     // for EINTR
#include <fcntl.h>     
#include <unistd.h>    
#include <sys/types.h>
#include <sys/stat.h>

#include <pthread.h>
#include <sys/mman.h>
#include <stdbool.h>
#include <assert.h>


// Print out the usage of the program and exit.
int GloblThreadCount = 0;
void Usage(char*);
uint32_t jenkins_one_at_a_time_hash(const uint8_t* , uint64_t );

typedef struct threadData{
  uint8_t* mapper;
  int16_t threadID; //id of the thread
  pthread_t curThread;
  int32_t start; //start of the offset to read from
  int32_t numBlocksToRead;
  char* hashValue;
  //store right and left child thread id
  pthread_t leftChild;
  pthread_t rightChild;
} threadData;



// block size
#define BSIZE 4096

void* threadFunction(void* arg){
  threadData* data = (threadData*)arg;
  uint8_t* mapper = data->mapper;
  int16_t threadID = data->threadID;
  int32_t start = data->start;
  int32_t numBlocksToRead = data->numBlocksToRead;
  uint32_t hashValue = data->hashValue;
  // calculate the hash value of the blocks
  uint32_t hash = jenkins_one_at_a_time_hash(mapper + start * BSIZE, numBlocksToRead * BSIZE);
  data->hashValue = hashValue;
  return NULL;
}

char* hashCombine(char* parentHash, char* leftChildHash, char* rightChildHash){
  char* combinedHash = (char*)malloc(31 * sizeof(char));
  //concatenate parent and child hash values
  sprintf(combinedHash, "%s%s%s", parentHash, leftChildHash, rightChildHash);
  //calculate the hash value of the concatenated hash values
  uint32_t hash = jenkins_one_at_a_time_hash((uint8_t*)combinedHash, 30);
  return hash;
}

void* treeInitializaer(){


  return;
}

int 
main(int argc, char** argv) 
{
  bool flag = false; // flag to check if there are remainder blocks
  int32_t fd;
  uint32_t nblocks;

  // input checking 
  if (argc != 3)
    Usage(argv[0]);

  // open input file
  fd = open(argv[1], O_RDWR);
  if (fd == -1) {
    perror("open failed");
    exit(EXIT_FAILURE);
  }
  // use fstat to get file size
  struct stat statStruct;
  if(fstat(fd, &statStruct) == -1){
    perror("fstat failure");
    exit(EXIT_FAILURE);
  }
  // mmap the file to memory
  uint8_t *mapper = mmap(NULL, statStruct.st_size, PROT_READ, MAP_PRIVATE, fd, 0);
  if(mapper == MAP_FAILED){
    perror("mmap failure");
    exit(EXIT_FAILURE);
  }
  //number of threads
  int32_t numThreads = atoi(argv[2]);
  //change the global variable to the number of threads
  GloblThreadCount = numThreads;
  // calculate nblocks 
  nblocks = statStruct.st_size / BSIZE;
  printf(" no. of blocks = %u \n", nblocks);


  double start = GetTime();
  //calculate blocks per thread
  int blocksPerThread = nblocks / numThreads;
  int remainder = nblocks % numThreads; // remainder blocks
  if (remainder != 0){
    flag = true;
  }
  // create threads
  pthread_t threads[numThreads];
  threadData* data[numThreads]; //this array is NOT going to be used to return the hash values, it is only used for memory deallocation

  for (int i = 0; i < numThreads; i++){
    // create thread
    //the purpose of this array is to store the data for each thread so that we can deallocate the memory after the threads are done
    //we are NOT using this array to have returned hash values in the array
    data[i] = (threadData*)malloc(sizeof(threadData));
    data[i]->mapper = mapper;
    data[i]->threadID = i;
    data[i]->start = i * blocksPerThread;
    data[i]->numBlocksToRead = blocksPerThread;
    data[i]->hashValue = 0;
    // int rc = pthread_create(&threads[i], NULL, threadFunction, (void*)data);
    // assert(rc != NULL);
  }
  // left child is 2*i+1 away from parent and right child is 2*i+2 away from parent
  //concatenate parent and child hash values and calculate the hash value of the concatenated hash values
  //this is done until we get the final hash value in the root node

  // deallocate memory
  for (int i = 0; i < numThreads; i++){
    free(data[i]);
  }


  
  // calculate hash value of the input file
  

  double end = GetTime();
  //printf("hash value = %u \n", hash);
  printf("time taken = %f \n", (end - start));
  close(fd);
  return EXIT_SUCCESS;
}

uint32_t 
jenkins_one_at_a_time_hash(const uint8_t* key, uint64_t length) 
{
  uint64_t i = 0;
  uint32_t hash = 0;

  while (i != length) {
    hash += key[i++];
    hash += hash << 10;
    hash ^= hash >> 6;
  }
  hash += hash << 3;
  hash ^= hash >> 11;
  hash += hash << 15;
  return hash;
}


void 
Usage(char* s) 
{
  fprintf(stderr, "Usage: %s filename num_threads \n", s);
  exit(EXIT_FAILURE);
}
