#include <fstream>
#include <iostream>
using namespace std;

int main()
{
    ifstream in("stuff.txt", ios::binary);  //open file in binary mode - necessary for seek and tell
    in.seekg(9);    //move file pointer forward 9 bytes, no second argument - default is from beginning of file
    char letter;
    int start = in.tellg();     //tell tells us where the file pointer is (in terms of bytes)
    //what value do you think tellg will return

    //what characters do you think will be printed out
    for (int i = 1; i <=4; i++)
    {
        in.get(letter);
        cout << letter;
    }

    //after reading the characters, where is the file pointer?
    int end = in.tellg();

    //where does the file pointer move?  cur tells it to move from its current position
    in.seekg(start - end, ios::cur);

    //did it move forward or backwards?  why?
    //what letter will it print now?
    in.get(letter);
    cout << endl<< letter << endl;

    return 0;
}
