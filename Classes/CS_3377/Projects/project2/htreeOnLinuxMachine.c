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

/*
 *
 * Do not compile with common.h or any other .h files
 * common.h and common_threads.h were not used in this project
 * I did add the function from common.h for GetTime() but that was because common.h did not include the library
 * for the elements inside of GetTime()
 * compile with gcc htree.c -o htree -Wall -Werror -std=gnu99 -pthread
 * */

// Print out the usage of the program and exit.
void Usage(char*);
uint32_t jenkins_one_at_a_time_hash(const uint8_t* , uint64_t );
double GetTime(); //taken from common.h
uint32_t max_thread;//keeps track of the amount of threads this iteration of the program will run
uint64_t blocksPerThread;//keeps track of how many blocks per thread will run

// block size
#define BSIZE 4096

//struct to keep track of the mmap and the individual thread id (not tid but "index")
typedef struct threadData{
  uint8_t* mapper; //mmap
  uint32_t threadID; //id of the thread
}threadData;

//worker function that will be called by the threads
void *worker(void *args)
{
    // Get the data
    threadData data = *((threadData *)args);
    pthread_t leftChild; //left child thread
    pthread_t rightChild; //right child thread
    bool hasLeftChild = false; //check if there is a left child
    bool hasRightChild = false; //check if there is a right child

    // Work with the data
    uint32_t left_hash = 0; //hash value of the left child
    uint32_t right_hash = 0; //hash value of the right child
    uint32_t hash = 0; //hash value of the current thread

    // Left child
    if ((data.threadID * 2 + 1) < max_thread)
    {
      
      threadData leftData; //create a new threadData struct for the left child
      //set the mapper and the threadID for the left child
      leftData.mapper = data.mapper; 
      leftData.threadID = data.threadID * 2 + 1;
      //create the left child thread and assert that it was created successfully
      int rc = pthread_create(&leftChild, NULL, worker, &leftData);
      assert(rc == 0);
      //set hasLeftChild to true
      hasLeftChild = true;
      
    }

    // Right child
    if ((data.threadID * 2 + 2) < max_thread)
    {
      threadData rightData; //create a new threadData struct for the right child
      //set the mapper and the threadID for the right child
      rightData.mapper = data.mapper;
      rightData.threadID = data.threadID * 2 + 2;
      //create the right child thread and assert that it was created successfully
      int rc = pthread_create(&rightChild, NULL, worker, &rightData);
      assert(rc == 0);
      //set hasRightChild to true
      hasRightChild = true;
    }

    // do the hashing for the current thread
    hash = jenkins_one_at_a_time_hash(&data.mapper[data.threadID * BSIZE * blocksPerThread],BSIZE * blocksPerThread);

    //if two children
    if(hasLeftChild == true && hasRightChild == true){
      uint32_t *left_hash_ptr = NULL; //pointer to the left child hash value
      uint32_t *right_hash_ptr = NULL;//pointer to the right child hash value
      //join the left child thread and get the left child hash value
      int LC = pthread_join(leftChild, (void*)&left_hash_ptr);
      assert(LC == 0);
      //join the right child thread and get the right child hash value
      int RC = pthread_join(rightChild, (void*)&right_hash_ptr);
      assert(RC == 0);
      //set the left and right hash values to the values obtained from the left and right children
      left_hash = *left_hash_ptr;
      right_hash = *right_hash_ptr;

      //combine the hash values of the parent and the children
      char* combinedHash = (char*)malloc(31 * sizeof(char));
      //malloc check
      if(combinedHash == NULL){
        perror("malloc failure");
        exit(EXIT_FAILURE);
      }

      //concatenate the hash values
      sprintf(combinedHash, "%u%u%u", hash, left_hash, right_hash);
      //calculate the hash value of the concatenated hash values
      hash = jenkins_one_at_a_time_hash((uint8_t*)combinedHash, strlen(combinedHash));
      
      //free the memory
      free(combinedHash);
      free(left_hash_ptr);
      free(right_hash_ptr);
    }

    //if one child
    if(hasLeftChild == true && hasRightChild == false){
      uint32_t *left_hash_ptr = NULL; //pointer to the left child hash value since there is no right child

      //join the left child thread and get the left child hash value while asserting that it was successful
      int LC = pthread_join(leftChild, (void*)&left_hash_ptr);
      assert(LC == 0);
      //set the left hash value to the value obtained from the left child
      left_hash = *left_hash_ptr;

      //allocate memory for the combined hash value
      char* combinedHash = (char*)malloc(21 * sizeof(char));
      //malloc check
      if(combinedHash == NULL){
        perror("malloc failure");
        exit(EXIT_FAILURE);
      }
      //concatenate the hash values
      sprintf(combinedHash, "%u%u", hash, left_hash);
      //calculate the hash value of the concatenated hash values
      hash = jenkins_one_at_a_time_hash((uint8_t*)combinedHash, strlen(combinedHash));

      //free the memory
      free(combinedHash);
      free(left_hash_ptr);
    }

    //if no children
    if(hasLeftChild == false && hasRightChild == false){
      //allocate memory for the hash valueq
      uint32_t *tmp = (uint32_t*)malloc(sizeof(uint32_t));
      //malloc check
      if(tmp == NULL){
        perror("malloc failure");
        exit(EXIT_FAILURE);
      }

      //set the hash value to the current thread hash value
      *tmp = hash;

      //return the hash value
      pthread_exit(tmp);
    }
    //allocate memory for the hash value if there are children
    uint32_t *tmp = (uint32_t*)malloc(sizeof(uint32_t));
    //malloc check
    if(tmp == NULL){
      perror("malloc failure");
      exit(EXIT_FAILURE);
    }
    //set the hash value to the current thread hash value
    *tmp = hash;
    //return the hash value
    pthread_exit(tmp);
}

int 
main(int argc, char** argv) 
{
  int32_t fd; //file descriptor
  uint32_t nblocks; //number of blocks

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
  
  //print the number of blocks and number of threads
  printf(" no. of blocks = %u \n", nblocks);
  printf(" no. of threads = %d \n", max_thread);

  //start the timer (keep in mind that GetTime is not a function in c, I had to get how to actually get the time in c from StackOverflow
  //I made it so that GetTime returns the time in seconds with the use of gettimeofday since we have never gone over that in class) 
  double start = GetTime();
  //calculate blocks per thread
  blocksPerThread = nblocks / max_thread;

  printf("blocks per thread = %lu \n", blocksPerThread);


  // calculate hash value of the input file
  pthread_t root; //root thread of index 0
  threadData rootData; //root thread data
  rootData.mapper = mapper; //set the mapper to the mmap
  rootData.threadID = 0; //set the threadID to 0 since it is the root

  //create the root thread and assert that it was created successfully
  int rc = pthread_create(&root, NULL, worker, &rootData);
  assert(rc == 0);

  //initialize the hash value and the hash pointer so that it is ready to be used
  uint32_t hash = 0;
  uint32_t *hash_ptr = NULL;

  //join the root thread and get the hash value (R for root) and assert that it was successful
  int R = pthread_join(root, (void*)&hash_ptr);
  assert(R == 0);
  //set the hash value to the value obtained from the root thread
  hash = *hash_ptr;

  //end the timer
  double end = GetTime();
  printf("hash value = %u \n", hash);
  printf("time taken = %f \n", (end - start));
  close(fd);
  free(hash_ptr); //free the memory
  return EXIT_SUCCESS;
}

//I do not know what is going on here as it was written by the professor
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

//written by professor
void 
Usage(char* s) 
{
  fprintf(stderr, "Usage: %s filename num_threads \n", s);
  exit(EXIT_FAILURE);
}
//taken from common.h
double GetTime(){
  struct timeval t;
  gettimeofday(&t, NULL);
  return (double)t.tv_sec + (double)t.tv_usec / 1000000.0;

}
