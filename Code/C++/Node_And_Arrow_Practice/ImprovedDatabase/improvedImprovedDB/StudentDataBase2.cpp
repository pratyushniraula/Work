#include <iostream>
#include <cmath>
#include <iomanip>
#include "sdb.hpp"
#include <fstream>
#include <string>
#include <unordered_map>
#include <unistd.h>

using namespace std;
//things to remember:
//make sure memory is deleted
//pointers must be allocated, not everything is already in references like in java
//classes to not have to take up a single file
//file manipulation and input validation is necessary to stop crashing
//reduce time complexity of certain algorithms in the old database
//add a forgot password section
//unauthorized access should do something to lock the user out
//output file will open the same file as input file
//file handling should also be considered
//make another file linking teachers to their classes


/*
idea flow:
read in database file for valid usernames and password
read in student file
read in class manip file
store everything in hashmap
multiple hashmap objects can be created so make the class generic
user can access grades and classes if they are a student
if they are a teacher they can only access the class that they teach
students that are in that class can only be affected, teacher must navigate to individual students
student information stored in a custom made hashmap


hashmap stores student information
hashmap stores teacher information
hashmap stores 
*/

int main(int argc, char const *argv[])
{
    //create the hashmaps for the students and teachers where string would be their username and the object would be the student or teacher
    unordered_map<string, Student> studentMap;
    //unordered_map<string, Teacher> teacherMap;
    //read in the files
    cout << "Please stand by!\nInformation is being processed" << endl;
    readStudentFile(studentMap);
    //readTeacherFile(teacherMap);



    bool adminExit = false; //exits the admin loop

    pr:
    while(adminExit == false){
        int choice = 0; //lets user interact with the console to select options
        string password = ""; //stores the password
        string id = ""; //stores the id

        cout << "Welcome to the Student Database" << endl;
        cout << "Please select an option" << endl;
        cout << "1. Student Login" << endl;
        cout << "2. Teacher/Admin Login" << endl;
        cout << "3. Exit" << endl;

        cin >> choice;

        if (choice == 1){
            cout << "if at any point you want to exit student sign in please type exit" << endl;
            cout << "Please enter your student ID: ";
            cin >> id;
            //check if id exists or not
            auto it = studentMap.find(id);
            if (it == studentMap.end()){
                cout << "ID does not exist" << endl;
                goto pr;
            }


            cout << "Please enter your password: ";
            cin >> password;
            //check if password is correct
            //but first check if exit
            if (id == "exit" || password == "exit"){
                 goto pr;
            }
            int passwordAttempts = 0;
            while(password != it->second.getPassword()){
                cout << "Password is incorrect" << endl;
                cout << "Please enter your password: ";
                cin >> password;
                passwordAttempts++;
                if (passwordAttempts == 3){
                    cout << "You have exceeded the number of attempts" << endl;
                    goto pr;
                }
            }

            cout << "Welcome " << it->second.getName() << endl;

            cout << "Please select an option" << endl;
            cout << "1. View Grades" << endl;
            cout << "2. View Classes" << endl;
            cout << "3. View GPA" << endl;
            cout << "4. View Everything" << endl;
            cout << "5. Exit" << endl;

            cin >> choice;


        }

        else if (choice == 2){
            cout << "Please enter your teacher/admin ID: ";
            cin >> id;
            //check if id exists or not
            cout << "Please enter your password: ";
            cin >> password;
            //check if password is correct

        }
            
        else if (choice == 3){
            cout << "Goodbye" << endl;
            return 0;
        }
    
        else{
            cout << "Invalid input" << endl;
            goto pr;
        }
        
    
    }

    return 0;
}

//what i encryption decryption is added onto this program as well in order to make it more secure
//can be introduced in the next version
//only problem will be efficiency
