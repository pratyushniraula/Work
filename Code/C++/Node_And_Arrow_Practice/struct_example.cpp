#include <iostream>

using namespace std;

struct person{
    string name;
    int age;
    int birthmonth;
    

    void printing(string n, int a, int b){
        cout << n << " is " << a << " years old. His birth month is " << b << endl;
        return;
    }

};

int main(int argc, char const *argv[])
{
    person p;
    person *per = new person();
    per->name = "brandon";
    per->age = 32;
    per->birthmonth = 12;

    cout << (*per).name << endl;

    string n = "lala";
    int a = 45;
    int b = 10;

    p.name = "kyle";
    p.age = 24;
    p.birthmonth = 01;
    cout << p.name << " is " << p.age << " years old. His birth month is " << p.birthmonth << endl;
    per->printing(n, a, b);
    return 0;
}

