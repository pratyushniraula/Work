/*v1
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
#include <string.h>
#include <sys/time.h>


// Print out the usage of the program and exit.
int GloblThreadCount = 0;
int threadnum = 0;
pthread_mutex_t mutex;
void Usage(char*);
uint32_t jenkins_one_at_a_time_hash(const uint8_t* , uint64_t );

typedef struct threadData{
  uint8_t* mapper; //this is known
  int16_t threadID;
  uint32_t start; 
  uint32_t numBlocksToRead; //this is known
  uint32_t hashValue;
  //store right and left child thread id
  pthread_t leftChild;
  pthread_t rightChild;
  bool hasLeftChild;
  bool hasRightChild;
} threadData;

double GetTime() {
    struct timeval tv;
    gettimeofday(&tv, NULL);
    return tv.tv_sec + tv.tv_usec / 1000000.0;
}

// block size
#define BSIZE 4096

uint32_t hashCombine(uint32_t parentHash, uint32_t leftChildHash, uint32_t rightChildHash, bool hasRightChild){
  char* combinedHash = (char*)malloc(64 * sizeof(char));
  //concatenate parent and child hash values
  if(hasRightChild){
    sprintf(combinedHash, "%u%u%u", parentHash, leftChildHash, rightChildHash);
  }
  else{
    sprintf(combinedHash, "%u%u", parentHash, leftChildHash);
  }
  //calculate the hash value of the concatenated hash values
  uint32_t hash = jenkins_one_at_a_time_hash((uint8_t*)combinedHash, 30);
  free(combinedHash);
  return hash;
}

void* treeInitializer(void* arg){
  int mutexer = pthread_mutex_init(&mutex, NULL);
  assert(mutexer == 0);
  threadData* data = (threadData*)arg;
  uint8_t* mapper = data->mapper;
  data->threadID = threadnum;
  data->start = data->threadID * BSIZE * data->numBlocksToRead; //starting offset to read from

  data->hasLeftChild = false;
  data->hasRightChild = false;
  
  //increment thread number
  //mutex lock
  int ml = pthread_mutex_lock(&mutex);
  assert(ml == 0);
  threadnum++;
  ml = pthread_mutex_unlock(&mutex);
  assert(ml == 0);

  threadData* leftData;
  threadData* rightData;

  if(data->threadID * 2 + 1 < GloblThreadCount){
    //call left child thread if thread number is less than number of threads
    pthread_t leftChild;

    leftData = (threadData*)malloc(sizeof(threadData));
    //initialize data for left child
    leftData->mapper = mapper;
    leftData->numBlocksToRead = data->numBlocksToRead;
    leftData->threadID = data->threadID*2+1;
    
    data->hasLeftChild = true;

    //mutex lock for thread number and increment thread number
    ml = pthread_mutex_lock(&mutex);
    assert(ml == 0);
    threadnum++;
    ml = pthread_mutex_unlock(&mutex);
    assert(ml == 0);
    //pthread create left child accounting for number of nodes made and number of threads active
    int rc = pthread_create(&leftChild, NULL, treeInitializer, (void*)leftData);
    assert(rc == 0);

    //add tid to the left child
    data->leftChild = leftChild;
  }
  //call right child thread
  if(data->threadID * 2 + 2 < GloblThreadCount){
    pthread_t rightChild;
    rightData = (threadData*)malloc(sizeof(threadData));
    //initialize data for right child
    rightData->mapper = mapper;
    rightData->numBlocksToRead = data->numBlocksToRead;
    rightData->threadID = data->threadID*2+2;
    
    data->hasRightChild = true;
    //mutex lock for thread number and increment thread number
    ml = pthread_mutex_lock(&mutex);
    assert(ml == 0);
    threadnum++;
    ml = pthread_mutex_unlock(&mutex);
    assert(ml == 0);

    //pthread create right child accounting for number of nodes made and number of threads active
    int rc = pthread_create(&rightChild, NULL, treeInitializer, (void*)rightData);
    assert(rc == 0);

    //add tid to the right child
    data->rightChild = rightChild;
  }
  //read the blocks and calculate hash value

  //calculate hash codes of each thread
  uint32_t hash = jenkins_one_at_a_time_hash(mapper + data->start, data->numBlocksToRead * BSIZE);
  
  //check if thread is leaf or not
  //if leaf just return hash value
  if(data->hasLeftChild == false && data->hasRightChild == false){
    data->hashValue = hash;
    uint32_t *tmp = (uint32_t*)malloc(sizeof(uint32_t));
    *tmp = data->hashValue;
    pthread_exit(tmp);
  }

  //if not leaf, join right and left child threads, take the hash value computed by the child threads and concatenate them with the parent hash value
  if(data->hasLeftChild == true && data->hasRightChild == true){
    uint32_t *leftHashPtr = NULL;
    uint32_t *rightHashPtr = NULL;
    int rc = pthread_join(data->leftChild, (void*)&leftHashPtr);
    assert(rc == 0);
    rc = pthread_join(data->rightChild, (void*)&rightHashPtr);
    assert(rc == 0);

    //concatenate parent and child hash values
    data->hashValue = hashCombine(hash, *leftHashPtr, *rightHashPtr, data->hasRightChild);
    free(leftHashPtr);
    free(rightHashPtr);
  }
  //if only left child
  else if(data->hasLeftChild == true){
    int32_t *leftHash = NULL;
    int32_t rightHash = 0;
    int rc = pthread_join(data->leftChild, (void*)&leftHash);
    assert(rc == 0);
    //concatenate parent and child hash values
    data->hashValue = hashCombine(hash, *leftHash, rightHash, data->hasRightChild);
    free(leftHash);
  }
  //calculate the hash value of the concatenated hash values

  //free the memory allocated for the left and right child data

  //return the hash value
  int32_t *tmp = (int32_t*)malloc(sizeof(int32_t));
  *tmp = data->hashValue;
  pthread_exit(tmp);
}

int 
main(int argc, char** argv) 
{
  //bool flag = false; // flag to check if there are remainder blocks
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
  
  //change the global variable to the number of threads
  GloblThreadCount = atoi(argv[2]);
  // calculate nblocks 
  nblocks = statStruct.st_size / BSIZE;
  printf(" no. of blocks = %u \n", nblocks);


  double start = GetTime();
  //calculate blocks per thread
  int blocksPerThread = nblocks / GloblThreadCount;
  //int remainder = nblocks % numThreads; // remainder blocks
  // if (remainder != 0){
  //   flag = true;
  // }

  // left child is 2*i+1 away from parent and right child is 2*i+2 away from parent
  //concatenate parent and child hash values and calculate the hash value of the concatenated hash values
  //this is done until we get the final hash value in the root node
  
  //create main thread
  pthread_t mainThread;
  threadData mainData;
  mainData.mapper = mapper;
  mainData.numBlocksToRead = blocksPerThread;
  int rc = pthread_create(&mainThread, NULL, treeInitializer, (void*)&mainData);
  assert(rc == 0);
  // calculate hash value of the input file

  uint32_t *hashPtr = 0;
  rc = pthread_join(mainThread, (void*)&hashPtr);
  assert(rc == 0);
  

  double end = GetTime();
  printf("hash value = %u \n", *hashPtr);
  printf("time taken = %f \n", (end - start));
  free(hashPtr);
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


