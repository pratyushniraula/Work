#include <iostream>
#include <cmath>
#include <iomanip>
#include "sdb.hpp"
#include <fstream>

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


    return 0;
}
