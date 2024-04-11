#define MAX_NAME_LENGTH 40

typedef struct studentRecord{
	char name[MAX_NAME_LENGTH]; // student name
	int32_t id; // id of the student
	int32_t index; // index of this record in the data base file
} sr;

// format of the data base file (not a text file). 
// Not all records will have valid data
/*
record with index 0
record with index 1
.
.
.
record with index n
*/
