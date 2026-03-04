#include <iostream>
#include <stdexcept>
#include "BasicShapeClass.h"
#include "CircleShapeSubClass.h"
#include "RectangleShapeSubClass.h"
#include "TriangleShapeSubClass.h"
#include "LineInfo.h"
/* 
Program Name: Shapes Assignment
Date: 2024-11-06
Author: Prasamsha Dahal
Module Purpose: This program lets you create different shapes— Circle, Rectangle, and Triangle. It prompts you to pick a shape and enter dimensions, 
checks for valid input, and handles any errors if dimensions are negative. After you've created three shapes, it displays each shape with its calculated area.
*/
using namespace std;

int main() {
    // Create an array of pointers to BasicShapeClass objects
    const int numShapes = 3;
    BasicShapeClass* shapeArray[numShapes];
    int shapeCount = 0;

    while (shapeCount < numShapes) {
        try {
            // Prompt user to choose a shape
            int choice;
            cout << "\nChoose your shape:\n1. Circle\n2. Rectangle\n3. Triangle\nEnter your choice: ";
            cin >> choice;

            // Create the chosen shape
            if (choice == 1) {
                // Create a circle
                double radius;
                cout << "Enter the circle's radius: ";
                // Validate input
                try{
                    // Get the radius
                    cin >> radius;
                    // Check if input is valid
                    if(cin.fail() || radius < 0){
                        // Clear the error flag
                        cin.clear();
                        cin.ignore(100, '\n');
                        // Throw an exception
                        throw domain_error(LineInfo("Radius less than zero", __FILE__, __LINE__));
                        }
                    // if input is valid, create a circle and add it to the array
                    shapeArray[shapeCount++] = new CircleShapeSubClass(radius);
                }catch(exception& e){ // Catch the exception and print the error message and exit the program
                    cout << e.what() << endl;
                    cout << endl << "Press the enter key once or twice to leave..." << endl;
                    cin.ignore(); cin.get();
                    exit(EXIT_FAILURE); 
                }
            } 
            else if (choice == 2) {
                // Create a rectangle
                double width, length;
                cout << "Enter the rectangle's width and length: ";
                // Validate input
                try{
                    // Get the width and length
                    cin >> width >> length;
                    // Check if input is valid
                    if(cin.fail() || width < 0 || length < 0){
                        // Clear the error flag
                        cin.clear();
                        cin.ignore(100, '\n');
                        // Throw an exception
                        throw domain_error(LineInfo("Width or Length less than zero", __FILE__, __LINE__));
                        }
                    // if input is valid, create a rectangle and add it to the array
                    shapeArray[shapeCount++] = new RectangleShapeSubClass(width, length);
                }catch(exception& e){ // Catch the exception and print the error message and exit the program
                    cout << e.what() << endl;
                    cout << endl << "Press the enter key once or twice to leave..." << endl;
                    cin.ignore(); cin.get();
                    exit(EXIT_FAILURE);
                }
                
            } 
            // Create a triangle
            else if (choice == 3) {
                // Create a triangle
                double height, base;
                cout << "Enter the triangle's height and base: ";
                // Validate input
                try{
                    // Get the height and base
                    cin >> height >> base;
                    // Check if input is valid
                    if(cin.fail() || height < 0 || base < 0){
                        // Clear the error flag
                        cin.clear();
                        cin.ignore(100, '\n');
                        // Throw an exception
                        throw domain_error(LineInfo("Height or Base less than zero", __FILE__, __LINE__));
                        }
                    // if input is valid, create a triangle and add it to the array
                    shapeArray[shapeCount++] = new TriangleShapeSubClass(height, base);
                }
                catch(exception& e){ // Catch the exception and print the error message and exit the program
                    cout << e.what() << endl;
                    cout << endl << "Press the enter key once or twice to leave..." << endl;
                    cin.ignore(); cin.get();
                    exit(EXIT_FAILURE);
                }
            } 
            // Invalid choice
            else {
                // Print an error message, clear the error flag, ignore input buffer, and throw an exception
                if(cin.fail()){
                    cout << "Invalid choice. Please try again.\n";
                    cin.clear();
                    cin.ignore(100, '\n');
                    throw domain_error(LineInfo("Invalid choice", __FILE__, __LINE__));
                }
                // Print an error message and re-prompt the user
                else if(choice < 1 || choice > 3){
                    cout << "Invalid choice. Please try again.\n";
                    cin.clear();
                    cin.ignore(100, '\n');
                }
            }
        } 
        catch (exception& e) { // Catch the exception and print the error message and exit the program
            cout << e.what() << endl;
            cout << endl << "Press the enter key once or twice to leave..." << endl;
            cin.ignore(); cin.get();
            exit(EXIT_FAILURE);
        }
        // Print the number of shapes created so far
        cout << shapeCount << " shape(s) created so far.\n" << endl;
    }
    // Display the shapes
    cout << "\nShapes Summary:\n";
    for (int i = 0; i < shapeCount; ++i) {
        shapeArray[i]->display();
        delete shapeArray[i]; // Free allocated memory
    }

    return 0;
}
