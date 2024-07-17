#include <stdio.h>     // for sscanf, fprintf, perror
#include <stdlib.h>    // for malloc, exit, EXIT_SUCCESS, EXIT_FAILURE
#include <stdint.h>    // for int32_t
#include <inttypes.h>  // for SCNd32, PRId32
#include <errno.h>     // for EINTR
#include <fcntl.h>     // for open, O_RDONLY
#include <unistd.h>    // for read, close

extern void put(int32_t);
extern void get(int32_t);

// Print out the usage of the program and exit.
void Usage(char*);


// create a database of students record
// write a new record
// read an existing record
int 
main(int argc, char** argv) 
{
  int32_t fd;
  int8_t choice, flag;

  // input checking 
  if (argc != 2)
    Usage(argv[0]);

  // open input file
  fd = open(argv[1], O_RDWR|O_CREAT, S_IRUSR|S_IWUSR);
  if (fd == -1) {
    perror("open failed");
    exit(EXIT_FAILURE);
  }
  flag = 1;
  while (flag){
	printf("Enter your choice (1 to put, 2 to get, 0 to quit): ");
	scanf("%" SCNd8 "%*c", &choice);
	switch (choice){
		case 1: 
			put(fd);
			break;
		case 2:
			get(fd);
			break;
		default:
			flag = 0;
	}
  }
  close(fd);
  return EXIT_SUCCESS;
}


void 
Usage(char* s) {
  fprintf(stderr, "Usage: %s filename \n", s);
  exit(EXIT_FAILURE);
}
