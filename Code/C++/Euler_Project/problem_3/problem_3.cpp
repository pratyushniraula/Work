#include <iostream>
#include <cmath>
using namespace std;

bool isPrime(unsigned long n){
    for (auto i = 2ul; i < sqrt(n);i++){
        if (n%i == 0){
            return false;
        }
    }
    return true;

}

int main(int argc, char const *argv[])
{
    int num;
    while (num != 0){
        
    }
    
    return 0;
}
