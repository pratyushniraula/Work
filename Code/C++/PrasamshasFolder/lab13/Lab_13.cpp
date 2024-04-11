#include <iostream>
#include <string>
#include <fstream>
#include <cstdlib>
#include <ctime>
using namespace std;

int main(){
    //Write a program that will generate between 1500 – 2000 random numbers in the range 1 to 2000 and store it in a file.
    //make a randome number generator between 1500 - 2000
    srand(time(0));
    ofstream file;
    file.open("dahal.txt");
    if(file.is_open()){
        for(int i = 0; i < 2000; i++){
            file << rand() % 2000 + 1 << endl;
        }
        file.close();
    }
    //read the file and find the average of the numbers at the same time display the amount of numbers that are in the file

    else{
        cout << "Error opening file" << endl;
    }
    return 0;
}