#include <stdio.h>     // for scanf, printf, perror
#include <stdlib.h>    // for malloc, exit, EXIT_SUCCESS, EXIT_FAILURE
#include <stdint.h>    // for int32_t
#include <errno.h>     // for EINTR
#include <fcntl.h>     // for open, O_RDONLY
#include <unistd.h>    // for read, close

#define READBUFSIZE 128

/* Copy a file one byte at a time */

int 
main(int argc, char **argv) {
  int fin, fout;
  char readbuf[READBUFSIZE];
  size_t readlen;

  // Take the filenames from command line arguments
  if (argc != 3) {
    fprintf(stderr, "usage: ./cpfile infile outfile\n");
    return EXIT_FAILURE;
  }

  // Open the input file
  fin = open(argv[1], O_RDONLY);  // read only
  if (fin == -1) {
    perror("open for read failed");
    return EXIT_FAILURE;
  }

  // Open the output file
  fout = open(argv[2], O_RDWR|O_TRUNC|O_CREAT, S_IRUSR|S_IWUSR);  // create if doesnt exist
  if (fout == -1) {
    perror("open for write failed");
    close(fin);
    return EXIT_FAILURE;
  }


  // Read from the file, write to fout.
  while ((readlen = read(fin, readbuf, READBUFSIZE)) > 0) {
    if (write(fout, readbuf, readlen) < readlen) {
      perror("write failed");
      close(fin);
      close(fout);
      return EXIT_FAILURE;
    }
  }

  // Test to see if we encountered an error while reading.
  if ( readlen < 0) {
    perror("read failed");
    close(fin);
    close(fout);
    return EXIT_FAILURE;
  }

  close(fin);
  close(fout);

  return EXIT_SUCCESS;
}
