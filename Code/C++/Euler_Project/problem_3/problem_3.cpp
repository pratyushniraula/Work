#include <iostream>
#include <cmath>
using namespace std;

bool isPrime(unsigned long n){
    for (auto i = 2ul; i < sqrt(n); i++){
        if (n%i == 0){
            return false;
        }
    }
    return true;

}

int main(int argc, char const *argv[])
{
    int num;
    cin >> num;
    for (auto j = 1ul; j < sqrt(num); j++){
        if (num%j == 0 && isPrime(num)){
            cout << "" << endl;
        }
    }
    // not complete
    return 0;
}
