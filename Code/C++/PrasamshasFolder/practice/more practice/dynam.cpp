#include <iostream>
#include <fstream>
#include <cstdlib>
using namespace std;

int main(int argc, char const *argv[])
{
    int n;
    cout << "Enter the size of the array: ";
    cin >> n;

    int *x = new int[n];
    for (int i = 0; i < n; i++)
    {
        x[i] = i;
    }

    int *g = x;
    for (int i = 0; i < n; i++)
    {
        cout << g << endl;
        g++;
    }
    cout << "x is storing " << x << endl;
    cout << "g is storing " << g << endl;
    cout << "" << endl;
    g = x;
        for (int i = 0; i < n; i++)
    {
        cout << *g << endl;
        g++;
    }
    delete[] x;
    return 0;
}
