#include <iostream>


using namespace std;

double f(double x)
{
    return x*x;
}

double derivative(double (*f)(double), double x)
{
    double h = 0.0000001;
    return (f(x+h)-f(x))/h;

}

double integrate(double (*f)(double), double a, double b)
{
    double h = (b - a) * 0.0000001;
    double total = 0;
    for(double i = a; i<=b;i+=h){
        total += (f(i)*h);
    }
    return total;
}

int main(int argc, char const *argv[])
{
    cout << derivative(f,10) << " " << integrate(f,0,2) << endl;
    return 0;
}
