#include <stdio.h>     // for sscanf, fprintf, perror
#include <stdlib.h>    // for malloc, exit, EXIT_SUCCESS, EXIT_FAILURE
#include <stdint.h>    // for int32_t
#include <errno.h>     // for EINTR
#include <fcntl.h>     // for open, O_RDONLY
#include <unistd.h>    // for read, close


// Print out the usage of the program and exit.
void Usage(void);


// Reads n bytes from a specified file and outputs them to stdout
// using POSIX commands.
int 
main(int argc, char** argv) {
  int32_t fd, n, bytes_left, result;
  char* buf;

  // input checking (# args, 2nd arg is a positive number)
  if ( (argc != 3) || (sscanf(argv[2], "%d",  &n) != 1) || (n <= 0) )
    Usage();

  // open input file
  fd = open(argv[1], O_RDONLY);
  if (fd == -1) {
    perror("open failed");
    exit(EXIT_FAILURE);
  }

  // allocate buffer for requested number of bytes
  buf = (char*) malloc((n+1)*sizeof(char));
  if (buf == NULL) {
    perror("malloc failed");
    exit(EXIT_FAILURE);
  }

  // attempt to read in n bytes using POSIX read() with error handling
    result = read(fd, buf, n);
    if (result == -1) {
        perror("read failed");
        exit(EXIT_FAILURE);
      }
    else if (result < n) {
      fprintf(stderr, "end of file reached: %d  bytes read.\n", result );
    }

  buf[n] = '\0';  // null-terminate string
  printf("Read: %s\n", buf);
  close(fd);
  return EXIT_SUCCESS;
}


void 
Usage(void) {
  fprintf(stderr, "Usage: ./readn filename n\nn > 0\n");
  exit(EXIT_FAILURE);
}
