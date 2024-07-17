#include <iostream>
#include <string>
#include "bruh.hpp"
using namespace std;

class book{
    public:
    string title = "hi there";
    string genre;
    int numPages;
    bool doILikeIt = true;
    int h[1] = {1};

    void ah(int a, int b){
        cout << a + b + h[0] << endl;
        
    }
}book1, book2;


int main(int argc, char const *argv[]){
    // int numBooks = 3;
    book b;
    // book c;
    // book d;
    // b.title = "Harry Potter";
    // cout << b.title << endl;
    // cout << "give a title";
    // cin >> c.title;
    // cout << c.title << endl;
    book ahhh[2] = {book1, book2};
    
    b.ah(3, 4);
    book1.ah(3, 5);
    ahhh[0].ah(3, 6);
    return 0;
}
