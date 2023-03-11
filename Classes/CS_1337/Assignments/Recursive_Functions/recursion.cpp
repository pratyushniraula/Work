//pxn210033
//Pratyush Niraula
#include <iostream>
#include <cstring>
#include <string>
using namespace std;
// 2 different recursion statements
//oddsum uses an array and returns the sum of odd indices in the array given size
//backwards gives the string backwards
int OddSum(int arr[], int length, int pointer=1){
    if (pointer >= length){
        return 0;
    }
    else {
        return arr[pointer] + OddSum(arr, length, pointer + 2);
    }
}

void Backwards(string s){
    int len = s.length();
    if (len == 0){
        return ;
    }
    else {
        cout << s[len - 1];
        Backwards(s.substr(0, len - 1));
    }
}



int main() {
    // **********OddSum function**********
    int size;
    size = 5;
    int numlist[] = {1, 2, 3, 4, 5};
    
    cout << OddSum(numlist,size) << " is the answer\n";
    //int size = sizeof(numlist);
    
    // **********Backwards function**********
    string s;
    cout << "Enter a string: ";
    cin >> s;
    
    Backwards(s);

    return 0;
}
