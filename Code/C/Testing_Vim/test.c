#include <stdio.h>
#include <math.h>

int main(int argc,char* argv[]){
	printf("hello world\n");
	char* x = "AHHHHH\n";
	
	int i =0;
	while (x[i] != '\0'){
	printf("%c",x[i]);
	i++;
	}
	
	return 0;
}

