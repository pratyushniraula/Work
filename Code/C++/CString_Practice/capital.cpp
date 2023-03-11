#include <iostream>
#include <cstring>
#include <cctype>

using namespace std;
//if given a set of letters, find and print out which letters are uppercase and lowercase
void uppercase(char array[], int a, char upp[]) {
    int iterationup = 0;
    for (int i = 0; i < a; i++) {
        if (isupper(array[i])) {
            upp[iterationup] = array[i];
            iterationup++;
        }
    }
    upp[iterationup] = '\0';
}

void lowercase(char array[], int a, char low[]) {
    int iterationlow = 0;
    for (int i = 0; i < a; i++) {
        if (islower(array[i])) {
            low[iterationlow] = array[i];
            iterationlow++;
        }
    }
    low[iterationlow] = '\0';
}

int main() {
    char lala[100];
    cin >> lala;
    int size = strlen(lala);
    char upp[size + 1];
    char low[size + 1];
    uppercase(lala, size, upp);
    lowercase(lala, size, low);
    cout << "the upper case letters are: " << upp << "\n\n";
    cout << "the lower case letters are: " << low << "\n\n";
    cout << "I beat rohit\n";
    return 0;
}
