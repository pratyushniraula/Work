#include <iostream>
#include <fstream>

using namespace std;

void sort(int arr[], int* ptrarr[], int n);

int main(int argc, char const *argv[])
{
    ifstream file;
    file.open("arrayData.txt");

    while(!file.eof())
    {
        int n;
        file >> n;
        int arr[n];
        int *pointerarr[n];
        for(int i = 0; i < n; i++)
        {
            file >> arr[i];
            pointerarr[i] = &arr[i];
        }
        
        sort(arr, pointerarr, n);
    }

    file.close();

    return 0;
}

void sort(int arr[], int *ptrarr[], int n)
{
    int *temp;
    for(int i = 0; i < n; i++)
    {
        for(int j = i + 1; j < n; j++)
        {
            if(*ptrarr[i] > *ptrarr[j])
            {
                temp = ptrarr[i];
                ptrarr[i] = ptrarr[j];
                ptrarr[j] = temp;   
            }
        }
    }

    for(int i = 0; i < n; i++)
    {
        cout << *ptrarr[i] << " ";
    }
    cout << endl;
}