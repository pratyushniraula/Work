//sample input 4*2
//output for sample 8
//sample input 5-3
//output for this sample 2
//to run on terminal g++ calculator.cpp -o calc && ./calc
#include <iostream>
using namespace std;

int main(){
    int operation;
    double x,y;
cout<< "Samshi's calculator: ";
cout<<"1. Multiply"<<endl;
cout<<"2. Divide"<<endl;
cout<<"3. Add"<<endl;
cout<<"4. Subtract"<<endl;
cout<<"Enter a choice from the menu";
cin>> operation;
cout<<"Enter the first number : "<<endl;
cin>>x;
cout<< "Enter the second number: "<<endl;
cin>>y;
if (operation == 1){
    cout<<x*y;

} else if(operation==2){
    cout<<x/y;


} else if(operation==3){
    cout<<x+y;

} else if (operation==4){
    cout<<x-y;
}
    return 0;
}
