#include <iostream>
#include <fstream>
#include <cstdlib>
using namespace std;

char* poop(){
    ifstream poopie;
    
    poopie.open("hj");
    
    
    char *liner = (char*)malloc(50*sizeof(char));
    poopie>>(liner);
    poopie.close();
    
    return liner;
    
}

void rara(string bruh){
    ofstream tutu;
    tutu.open("3");
    tutu<<bruh;
    tutu.close();
}

int main()
{
    char* linetaken = poop();
    rara(linetaken);
    free(linetaken);
    return 0;
}