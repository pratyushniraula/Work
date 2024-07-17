

/******************************************************************************
Lab 11
Prasamsha Dahal
Due date: 3/26/24
Date completeed: 3/26/24
Date submitted: 3/26/24
1436.004 Programming fundamentals
The purpose of this program is to create a bar graph that includes 10 Texas cities and the population
is represented as a astrik for every 1000 people.
*******************************************************************************/
#include <iostream>

using namespace std;

int main()
{
    int num[10]; //array of integers holding all of the populations of the city - looked ahead in the slides

    for(int i = 0; i < 10; i++){ //loops around until i reaches to 10

    cout<< "Enter the population of Texas City " << i +1 << ": "; //output shows the current city number the loop is on

    cin >> num[i]; // input to correspond to whatever city it corresponds to 

    }

    cout << "\n"; //next line

    for(int i = 0; i < 10; i++){ //loops around until i reaches to 10

        cout << "CITY " << i+1 << ": "; //output shows the current city number the loop is on

        int asteriskTracker = num[i]/1000;// population entered divided it by 1000 to determine the number asteriks

        for(int j = 0; j < asteriskTracker; j++){  //loops around to print the total amount of asteriks calculated

            cout << "*"; //asterisk output
        }
        cout << "\n"; //nextline
    }


    return 0;
}
