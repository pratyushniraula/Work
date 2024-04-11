#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

#define DEBUG 0

/* 
 * This function creates a duplicate of string src
 * Memory for duplicate is allocated
 * Pointer to the duplicate is stored in *dup
*/
void strdupe(char* src, char** dup)
{
	// define required variables
	int counter = 0;
	// Find the length of source (src) string
	// Do not use any library call such as strlen()
	// Assume source string is terminated with null character ('\0')
	while(*src != '\0'){
		src++;
		counter++;
	}
	src -= counter;
	// allocate memory from heap for the duplicate and store the address 
	char* ptr = (char*)malloc(counter+1);
	// if memory allocation fails print appropriate error message 
	// and return
	if(ptr == NULL){
		puts("Malloc Error!");
		return;
	}

	// copy src to *dup. 
	// Do not use any library call such as strcpy()
	// Do not use array indexing. Use pointer arithmetic.
	*dup = ptr;	

	int i = 0;
	while(*src != '\0'){
		*ptr = *src;
		ptr++;
		src++;
	}
	//ptr++;
	*ptr = '\0';
	ptr -=counter;
	

#if DEBUG
	printf("str: %s \n", src);
	printf("str: %s \n", *dup);
#endif
	
	return;
}

