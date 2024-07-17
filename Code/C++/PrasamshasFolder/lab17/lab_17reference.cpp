#include <iostream>
using namespace std;

int main(){
    int rainFall_month[12];
    for(int month=0 ;month < 12; month++){
        do{
        cout<<"Enter the rainfall for month " << month+1 << ": ";
        cin >> rainFall_month[month];
        }while(rainFall_month[month] < 0);
    }
    int total = 0;
    int min = rainFall_month[0];
    int max = 0;
    for(int i = 0; i < 12; i++){
        total += rainFall_month[i];
        if(rainFall_month[i] < min){
            min = rainFall_month[i];
        }
        if(rainFall_month[i] > max){
            max = rainFall_month[i];
        }
    }

    cout << "total rainfall is " << total << endl;
    cout << "min rainfall is " << min << endl;
    cout << "max rainfall is " <<  max << endl;
    return 0;

}
