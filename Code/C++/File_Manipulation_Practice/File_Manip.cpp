#include <iostream>
#include <cmath>
#include <fstream>
using namespace std;

int main(int argc, char const *argv[])
{
    string filestream = argv[1];
    fstream file;
    string line;
    file.open(filestream, ios :: in | ios :: out | ios :: binary);
    
    if (!file.is_open()){
        cout << "File not found" << endl;
        return 1;
    }

    file.seekp(0, ios :: beg);
    if(file.eof()){
        file.clear();
        file << "\n";
    }
    int length = file.tellg();
    file << "rahhh";
    file >> line;
    cout << line << endl;
    cout << length << endl;
    cout << "rahhh" << endl;
    file.close();
    return 0;
}
