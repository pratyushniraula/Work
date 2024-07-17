#include <stdio.h>     // for sscanf, fprintf, perror
#include <stdint.h>    // for int32_t
#include <assert.h>    // for assert
#include "sr.h"

#include <unistd.h>
#include <stdlib.h>
#include <string.h>
// include files are not complete. 
// include the appropriate files related to read/write/lseek
// check man pages


// Get the values of all the fields of student record sr from stdin
// Put the record in the appropriate offset of the file described by fd
void
put(int32_t fd)
{
	sr s;

	printf("Enter the student name: ");	
	
	// WRITE THE CODE to read the name from stdin
	// store it in s.name
	// use fgets()
	// fgets doesnt remove newline. replace '\n' with '\0' in s.name. strlen() will be useful
	int sizeOfString = 40;
	//char* n = (char*)malloc(sizeOfString*sizeof(char));
	char n[sizeOfString];
	//if(n == NULL){
	//	puts("Malloc Error");
	//	exit(EXIT_FAILURE);
	//}
	fgets(n, sizeOfString, stdin);
	
	if(strlen(n)-1 < sizeOfString && n[strlen(n)-1] == '\n'){
		n[strlen(n)-1] = '\0';
	}

	strcpy(s.name,n);
	//free(n);
	
	printf("Enter the student id: ");
	//
	// WRITE THE CODE to read student id from stdin
	// store it in s.id
	char* strId = (char*)malloc(sizeof(char)*sizeOfString);

	fgets(strId, sizeOfString, stdin);
	int32_t identification = atoi(strId);
	s.id = identification;
	free(strId);	
	
	printf("Enter the record index: ");
	//
	// WRITE THE CODE to read record index from stdin
	// store it in s.index
	char* ind = (char*)malloc(sizeof(char)*sizeOfString);
	if(ind == NULL){
		puts("Malloc Error");
		exit(EXIT_FAILURE);
	}
	
	fgets(ind, sizeOfString, stdin);
	s.index = atoi(ind);
	free(ind);

	// WRITE THE CODE to seek to the appropriate offset in fd (lseek(), sizeof() will be useful)
	int64_t offset = s.index * sizeof(sr);
	lseek(fd, offset, SEEK_SET);	

	// WRITE THE CODE to write record s to fd
	write(fd, &s, sizeof(sr));
}

// read the student record stored at position index in fd
void
get(int32_t fd)
{
	sr s;
	int32_t index;

	printf("Enter the record index: ");
	//
	// WRITE THE CODE to get record index from stdin and store in it index
	char* n = (char*)malloc(sizeof(char)*32);
	fgets(n, 32, stdin);
	index = atoi(n);
	free(n);

    	// WRITE THE CODE to seek to the appropriate offset in fd
    	// The record index may be out of bounds. If so, 
    	// print appropriate message and return
    	// Check SEEK_DATA on the man page for lseek 
	int64_t offset = index*sizeof(sr);
	if(lseek(fd,offset, SEEK_SET) == -1){
		puts("Error with lseek");
		return;
	}    	


    // WRITE THE CODE to read record s from fd
    // If the record has not been put already, print appropriate message
    // and return
        int reading = read(fd,&s,sizeof(sr));
	if(reading == -1 || reading == 0){
		puts("Data Not Found!");
		return;
	}

	printf("Student name %s \n", s.name);	
	printf("Student id: %d \n", s.id);
	printf("Record index: %d \n", s.index);

	assert(index == s.index);
}

