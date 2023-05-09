#include <iostream>
#include <fstream>
#include <stdio.h>

using namespace std;

int main(int argc, char const *argv[])
{
    int words = 0, letters = 0, sentences = 0;
    ifstream file;
    file.open("something_txt");
    char ch;
    while (file.get(ch))
    {
        if (ch == ' ')
            words++;
        else if (ch == '.')
            sentences++;
        else if (ch == '!')
            sentences++;
        else if (ch == '?')
            sentences++;
        else
            letters++;
    }
    cout << "Words: " << words << endl;
    cout << "Letters: " << letters << endl;
    cout << "Sentences: " << sentences << endl;
    return 0;
}
