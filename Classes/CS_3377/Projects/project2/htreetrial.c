/*
 * Write your name and netid
 * Write your partner's name and netid
 * Pratyush Nirauka
 * pxn210033
 * Karl Azangue
 * kka210001
*/

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
void Usage(char*);
uint32_t jenkins_one_at_a_time_hash(const uint8_t* , uint64_t );
double GetTime();

uint32_t max_thread;
uint32_t blocksPerThread;

// block size
#define BSIZE 4096

typedef struct threadData{
  uint8_t* mapper;
  uint32_t threadID;
}threadData;

void *worker(void *args)
{
    // Get the data
    threadData data = *((threadData *)args);
    pthread_t leftChild;
    pthread_t rightChild;
    bool hasLeftChild = false;
    bool hasRightChild = false;

    // Work with the data
    uint32_t left_hash = 0;
    uint32_t right_hash = 0;
    uint32_t hash = 0;

    // Left child
    if ((data.threadID * 2 + 1) < max_thread)
    {
      threadData leftData;
      leftData.mapper = data.mapper;
      leftData.threadID = data.threadID * 2 + 1;
      int rc = pthread_create(&leftChild, NULL, worker, &leftData);
      assert(rc == 0);
      hasLeftChild = true;
      
    }

    // Right child
    if ((data.threadID * 2 + 2) < max_thread)
    {
      threadData rightData;
      rightData.mapper = data.mapper;
      rightData.threadID = data.threadID * 2 + 2;
      int rc = pthread_create(&rightChild, NULL, worker, &rightData);
      assert(rc == 0);
      hasRightChild = true;
    }

    // do my own hashing
    hash = jenkins_one_at_a_time_hash(&data.mapper[data.threadID * BSIZE * blocksPerThread],BSIZE * blocksPerThread);
    // join the threads and get the data back
    //if two children
    if(hasLeftChild == true && hasRightChild == true){
      uint32_t *left_hash_ptr = NULL;
      uint32_t *right_hash_ptr = NULL;
      int LC = pthread_join(leftChild, (void*)&left_hash_ptr);
      assert(LC == 0);
      int RC = pthread_join(rightChild, (void*)&right_hash_ptr);
      assert(RC == 0);
        
      left_hash = *left_hash_ptr;
      right_hash = *right_hash_ptr;
    
      char* combinedHash = (char*)malloc(31 * sizeof(char));
      sprintf(combinedHash, "%u%u%u", hash, left_hash, right_hash);

    //////////////////////////////////////////////////////////
      printf("thread %d hash computed: %u\n", data.threadID, hash);
      printf("thread %d left child hash %u\n", data.threadID, left_hash);
      printf("thread %d right child hash %u\n", data.threadID, right_hash);
      printf("thread %d combined hash %s\n", data.threadID, combinedHash);
    /////////////////////////////////////////////////////////
      hash = jenkins_one_at_a_time_hash((uint8_t*)combinedHash, strlen(combinedHash));
      
      printf("thread %d concatenated hash computed: %u\n", data.threadID, hash);


      free(combinedHash);
      free(left_hash_ptr);
      free(right_hash_ptr);
    }

    //if one child
    if(hasLeftChild == true && hasRightChild == false){
      uint32_t *left_hash_ptr = NULL;

      int LC = pthread_join(leftChild, (void*)&left_hash_ptr);
      assert(LC == 0);
      left_hash = *left_hash_ptr;

      char* combinedHash = (char*)malloc(21 * sizeof(char));
      sprintf(combinedHash, "%u%u", hash, left_hash);

        //////////////////////////////////////////////////////////
        printf("thread %d hash computed: %u\n", data.threadID, hash);
        printf("thread %d left child hash %u\n", data.threadID, left_hash);
        printf("thread %d combined hash %s\n", data.threadID, combinedHash);  
        /////////////////////////////////////////////////////////

      hash = jenkins_one_at_a_time_hash((uint8_t*)combinedHash, strlen(combinedHash));
        printf("thread %d concatenated hash computed: %u\n", data.threadID, hash);
      free(combinedHash);
      free(left_hash_ptr);
    }

    //if no children
    if(hasLeftChild == false && hasRightChild == false){
      uint32_t *tmp = (uint32_t*)malloc(sizeof(uint32_t));
      *tmp = hash;

        //////////////////////////////////////////////////////////
        printf("thread %d hash computed: %u\n", data.threadID, hash);
        /////////////////////////////////////////////////////////
      pthread_exit(tmp);
    }

    // Send the data
    // void *ret = 0;
    // memcpy(&ret, &hash, sizeof(uint32_t));
    // return ret;
    uint32_t *tmp = (uint32_t*)malloc(sizeof(uint32_t));
    *tmp = hash;
    pthread_exit(tmp);
}

int 
main(int argc, char** argv) 
{
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
  max_thread = atoi(argv[2]);

  // calculate nblocks (number of blocks)
  nblocks = statStruct.st_size / BSIZE;
  

  printf(" no. of blocks = %u \n", nblocks);

  double start = GetTime();
  //calculate blocks per thread
  blocksPerThread = nblocks / max_thread;
  printf("blocks per thread %d\n", blocksPerThread);


  // calculate hash value of the input file
  pthread_t root;
  threadData rootData;
  rootData.mapper = mapper;
  rootData.threadID = 0;
  int rc = pthread_create(&root, NULL, worker, &rootData);
  assert(rc == 0);

  uint32_t hash = 0;
  uint32_t *hash_ptr = NULL;
  int R = pthread_join(root, (void*)&hash_ptr);
  assert(R == 0);
  hash = *hash_ptr;

  double end = GetTime();
  printf("hash value = %u \n", hash);
  printf("time taken = %f \n", (end - start));
  close(fd);
  free(hash_ptr);
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

double GetTime() {
    struct timeval tv;
    gettimeofday(&tv, NULL);
    return tv.tv_sec + tv.tv_usec / 1000000.0;
}