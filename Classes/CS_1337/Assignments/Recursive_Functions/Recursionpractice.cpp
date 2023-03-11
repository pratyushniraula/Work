//pxn210033 Pratyush Niraula
//lxn210023 Leda Ning
//ask about where to put netid*************************
//what we did:

/******************************************************************************
Leda Ning lxn210023
Pratyush Niraula pxn210033

this function test if a number is in the array and output it in binary if it is in
*******************************************************************************/
#include <iostream>

using namespace std;

bool number_search(int array[],int number, int size=15,int pos=0);

void binary_output(int number);

int main()
{
    int number;
    int array[15]={1,2,3,4,5,6,7,8,9,10,11,12,13,14,100};
   
    //prompt and input
   
    cout<<"please input a number to search"<<endl;
    cin>>number;
   
    // test if it is in
   
    if(number_search(array, number))
    {
        // if returns true
       
        binary_output(number);
    }
   
    // if did not find anything
   
    else
    {
        cout<<"Do not find the number"<<endl;
    }
   
    return 0;
}

bool number_search(int array[], int number, int size, int pos)
{
    bool find;
    if(pos == size-1)
    {
        if(array[pos] == number)
        {
            find = true;
        }
        else
        {
            find = false;
        }
        return find;
    }
   
    else
    {
        if(array[pos] == number)
        {
            find = true;
            return find;
        }
        else
        {
            return number_search(array, number, size, pos+1);
        }
    }
    return find;
}
void binary_output(int number)
{
    if(number ==0)
    {
        return;
    }
    if( number == 1)
    {
        cout << number;
        return;
    }
    else
    {
        //algorithm: call function dividing number/2 and outputting binary
        //causing function to call first and output last, making it correct order
        binary_output(number/2);
        cout<<(number % 2);
        return;
    }
}
