#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <inttypes.h>
#include <string.h>
#include "strdupe.c"
#define SIZE 20
#define DEBUG 0

extern void strdupe(char*, char**);

int main(int argc, char **argv) 
{

  char* dest = NULL;

	if (argc < 2){
		printf("Usage: a4Wrapper <string> \n");
		return EXIT_FAILURE;
	}

#if DEBUG
  printf("str: %s \n", argv[1]);
#endif
	
	strdupe(argv[1], &dest);

	if (!dest || strcmp(argv[1], dest)){
		printf("strdupe FAILED \n");
		return EXIT_FAILURE;
	}
	else
		printf("strdupe Successful \n");
  return EXIT_SUCCESS;
}

