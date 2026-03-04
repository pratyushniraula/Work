/******************************************************************************
Name: Sarah Zaki
Date: 10/06
Sec: 001
This program calculates the weight of an object on different celestial bodies
(Earth, Moon, Mercury, and Venus) based on its mass entered in kilograms.
*******************************************************************************/

#include <iostream>
#include <iomanip> // For stream manipulators like setw and setprecision
using namespace std;

int main() {

    // Define constants for the gravitational acceleration in m/s^2

    const double EARTH_GRAVITY = 9.807;
    const double MOON_GRAVITY = 1.62;
    const double MERCURY_GRAVITY = 3.7;
    const double VENUS_GRAVITY = 8.87;

    // Declare variable to store the object's mass

    double mass;

    // Prompt user to enter the mass of the object in kilograms

    cout << "Please enter the mass of an object in kilograms: ";
    cin >> mass;

    // Check if mass is valid (greater than zero)

    if (mass <= 0) {
        cout << "Error, invalid mass entered." << endl;
        cout << "The entered mass must be greater than 0." << endl;
    } else {

        // Calculate the weight on Earth, Moon, Mercury, and Venus

        double weightEarth = mass * EARTH_GRAVITY;
        double weightMoon = mass * MOON_GRAVITY;
        double weightMercury = mass * MERCURY_GRAVITY;
        double weightVenus = mass * VENUS_GRAVITY;

        // Set up formatting for output

        cout << fixed << setprecision(3);  // Set precision to 3 decimal places

        // Add a space after the prompt and then the table header
        cout << "\nPlanet/Satellite" << setw(18) << "Weight (N)\n" << endl;

        // Output the weights on each planet/satellite

        cout << left << setw(18) << "Earth" << right << setw(12) << weightEarth << endl;
        cout << left << setw(18) << "Moon" << right << setw(12) << weightMoon << endl;
        cout << left << setw(18) << "Mercury" << right << setw(12) << weightMercury << endl;
        cout << left << setw(18) << "Venus" << right << setw(12) << weightVenus << endl;
    }

    return 0;
}