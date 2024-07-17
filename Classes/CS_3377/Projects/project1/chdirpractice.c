#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>

int main(){
    //chdir test
    char path1[1000];
    chdir("../..");
    chdir("/Users/pratyushniraula");
    printf("Current working directory: %s\n", getcwd(path1, 1000));
    return 0;

}
