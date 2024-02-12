#include <stdio.h>

void f();

int main(){
	int i = 0;
	int *p = &i;
	printf("main is located in %p\n", main);
	printf("integer i = 0 is located in %p\n", p);
	f();
	printf("function f is located in %p", f);
	return 0;
	
}

void f(){
	int br = 12;
}
