/******************************************************************************

Name: Sarah Zaki

Date: 09/11

Sec: 001

This program performs arithmetic operations (sum, product, quotient, and 

remainder) on two integers. The first integer is assigned a value of 8, and 

the second integer is entered by the user. It handles both positive and negative numbers.

*******************************************************************************/



#include <iostream>

using namespace std;



int main() {

    // Declare variables

    int firstNumber = 8;     // Assign 8 to the first integer variable

    int secondNumber;        // Declare a second integer variable for user input

    double result;           // Declare a double variable to store the results



    // Prompt user to enter a whole number

    cout << "Enter a whole number: ";

    cin >> secondNumber;



    // Calculate and display the sum of the two integers, stored in 'result'

    result = firstNumber + secondNumber;

    cout << "sum = " << result << endl;



    // Calculate and display the product of the two integers, stored in 'result'

    result = firstNumber * secondNumber;

    cout << "product = " << result << endl;



    // Calculate and display the quotient, integer division, stored in 'result'

    if (secondNumber != 0) {

        result = secondNumber / firstNumber;  // Integer division

    } else {

        result = 0;  // Handle division by zero

    }

    cout << "quotient = " << result << endl;



    // Calculate and display the remainder using mod%, stored in 'result'

    if (secondNumber != 0) {

        result = secondNumber % firstNumber;  // Modulus operator for remainder

    } else {

        result = firstNumber;  // Handle division by zero, remainder is the first number

    }

    cout << "remainder = " << result << endl;



    return 0;

}