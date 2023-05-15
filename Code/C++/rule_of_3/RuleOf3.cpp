#include <iostream>
using namespace std;
class Dummy
{
    public:
        int* a;
        int* b;
        Dummy()
        {
            a = new int;
            *a = 5;
            b = new int;
            *b = 2;
        }

        ~Dummy()
        {
            delete a;
            delete b;
        }

        Dummy(Dummy& source) // copy constructor
        {

        }

        Dummy operator= (Dummy& source) // assigment constructor
        {

        }
        
};



int main(int argc, char const *argv[])
{

    return 0;
}
