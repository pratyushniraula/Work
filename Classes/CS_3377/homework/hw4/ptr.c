// TASK 1: Include the appropriate files.
#include <stdio.h>
#include <stdlib.h>
int main(int argc, char** argv){

  int32_t x = 3377;

  // TASK 2: 
  // Understand the assignment statements below.
  // Do not modify them.
  // Appropriately define the variables p, a, m, z, t 
  int32_t *p = &x;       // p now contains the addr of x
  int32_t **a = &p;	//a now contains the address of p where p holds the address for x
  int32_t ***m = &a;	//m now contains the address of a where a holds the address of p and p holds the address of x
  int32_t ****z = &m;	//z now holds the address for m where what m holds is defined above
  int32_t *****t = &z;	//t now holds the address for z where what z holds is defined above

  // TASK 3: 
  // Your next task is to print the value of x by
  // recursively deferencing the above variables.
  // Complete the printf statments below.
  // The variable used should be as per the description
  // of the printf

  printf("x by recursive deref. of p: %d \n",  *p);
  printf("x by recursive deref. of t: %d \n",  **a);
  printf("x by recursive deref. of m: %d \n",  ***m);
  printf("x by recursive deref. of z: %d \n",  ****z);
  printf("x by recursive deref. of a: %d \n",  *****t);

  char* c;

  // TASK 4: 
  // Find the sizes of c and p
  // Complete the statements below. Hint: sizeof()

  printf("size of c %d \n", sizeof(*c));
  printf("size of p %d \n", sizeof(*p));

  // TASK 5: 
  // What do you observe? c and p are pointers to two
  // different types. Are their sizes different?
  // Write a brief note justifying your observation
  // at the bottom of the file. 
  // Make sure it is commented.


  return EXIT_SUCCESS;
}

/* your observation below
 *
 *yes, their sizes are different. Size allocated to the address that c is pointing to is one byte while size of the address that p is pointing to is 4 bytes
 *not only was x (the variable that p was initialized to hold the address of) assigned to have 32 bits where (32 bits)/(8 bits/byte) = (4 bytes),but a lot of the time an integer...
 *... by default is initialized to 4 bytes already. This being said, the address that the pointer c is pointing to is 1 byte due to how ascii characters only need 1 byte to...
 *... store the character in question.
 */
