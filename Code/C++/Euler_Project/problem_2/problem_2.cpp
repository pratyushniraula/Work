#include <iostream>

using namespace std;

int main() {
    //variables
    auto n1 = 1ul;
    auto n2 = 1ul;
    auto t = 0ul;
    auto s = 0ul;


    while (n2 < 4000000ul) {
        t = n1 + n2;
        n1 = n2;
        n2 = t;
        if (n2 % 2 == 0) {
            s += n2;
        }
    }

    cout << s << endl;
}