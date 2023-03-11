#include <fstream>
#include <iostream>
#include <string>
#include <cstring>
#include <cctype>
using namespace std;

int main() {
    string inputFile;
    string outputFile;
    char ch;
    char outputwriter;
    int marker;
    
    cout << "Enter the name of the file you want to find the vowels of: ";
    cin >> inputFile;
    fstream everything;
    everything.open(inputFile, ios :: in | ios :: binary);
    
    outputFile = "vowels_" + inputFile;
    
    if (!everything.good()){
        cout << "file is not working properly, error bit says no good";
        return 1;
    }
    
    while (everything.get(ch)){
        if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u' ||
        ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U'){
            outputwriter = ch;
            marker = everything.tellg();
            everything.close();
            
            fstream putOut;
            putOut.open(outputFile, ios :: in | ios :: out | ios :: app);
            putOut << outputwriter;
            putOut.close();
            everything.open(inputFile, ios :: in | ios :: binary);
            everything.seekg(marker);
        }
        else if(ch == '\n'){
            outputwriter = ch;
            marker = everything.tellg();
            everything.close();
            
            fstream putOut;
            putOut.open(outputFile, ios :: in | ios :: out | ios :: app);
            putOut << outputwriter;
            putOut.close();
            everything.open(inputFile, ios :: in | ios :: binary);
            everything.seekg(marker);
        }
    }
    
    return 0;
}