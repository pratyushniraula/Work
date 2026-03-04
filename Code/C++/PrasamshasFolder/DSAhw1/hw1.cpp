//
// ========================================
// Assignment 1: C++ Programming 
// ========================================
// Adding comments for your coding, which would
// count 10% of the grading
// In this assignment, we will implement 
// a Student class and a University class. 
// 
// The Student class has two private member  
// variables and five member functions (not 
// counting the constructor). Their designs 
// are explained in the template below.
// 
// The University class has one private member 
// variable and four member functions. Their 
// designs are explained in the template below.
// 
// In the template, you will see two types of 
// sections: "Task" and "Test". A "Task" section 
// is where you need to implement something, 
// and a "Test" section is testing your implementation 
// (you don't need to do anything in this section). 
// 
// For all print functions, only print the value 
// and do not add any additional character 
// such as space or change line. For example, 
// if you are asked to print the value of x, 
// write "cout << x;" instead of "cout << x << endl;"
//
//



// ======= Task 1 =======
//only need to include iostream and using the standard namespace to use cout and cin
#include <iostream>
using namespace std;
// === End of Task 1 ====

// ======= Task 2 =======
// Design and implement all member functions of the 
// following two classes: Student and University.
// All member functions must be implemented outside 
// the class definitions. 

class Student {           
public:
	void set_SID(int x);  // It assigns x to SID. 
	void prt_SID();       // It prints SID. 
	void set_GPA(float y);// It assigns y to GPA. 
	void prt_GPA();       // It prints GPA. 
	void reset();         // It resets SID and GPA to -1. 
	float get_GPA();        // It returns GPA. 
	Student();            // Constructor. It initializes 
	                      // both SID and GPA to -1. 
private:
	int SID;    // It holds a student's ID. 
	float GPA;  // It holds a student's GPA.
};

class University {
public:
	void set_Stu(Student x[]);// It assigns x to Sooner. 
	float GPA_Mean();         // It prints the mean GPA of the five students held by Sooner[5]. 
	float GPA_Max();          // It prints the max GPA of these five students.
	float GPA_Min();          // It prints the min GPA of these five students. 
	University();             // Constructor. Initialize all student SID and GPA to -1.
	// Note: The above three functions must be implemented by yourself 
	// and not called from existing mean, max or min functions.  
private:
	Student Sooner[5];  // It holds the records of five students.
};

//function definitions:
//student methods:
void Student::set_SID(int x){
	//when you define a function outside of a class you need to have the right namespace so that the compiler can know where to look
	this->SID =x;
}
void Student::prt_SID(){
    //prints the student ID, needing to use this-> to access the private member variable SID
	cout<< this->SID;
}
void Student::set_GPA(float y){
    //sets the GPA of the student object to the value passed in
	this->GPA = y;
}
void Student::prt_GPA(){
    //prints the GPA of the student object
	cout<<this->GPA;
}
void Student::reset(){
    //sets the GPA and SID of the student object to -1
	this->GPA = -1;
	this->SID = -1;
}
float Student::get_GPA(){
    //returns the GPA of the student object
	return this->GPA;
}
Student::Student(){
    //constructor that initializes the GPA and SID to -1
	this->GPA = -1;
	this->SID = -1;
}

//University Methods:
void University::set_Stu(Student x[]){
    //assigns the array of student objects passed in to the private member variable Sooner
    //it assigns it by going through every element of the array, x, and assigning it to the corresponding element in Sooner
	for(int i =0; i<5; i++){
		this->Sooner[i] = x[i];
	}
}
float University::GPA_Mean(){
    //average : the sum of all the values / the # of values 
    // need to loop through student gpas, take the average + divide
	float average = 0.0;
    //adding all the GPAs together
	for(int i = 0; i < 5; i++){
		average += Sooner[i].get_GPA();
	}
    //dividing the total GPA by the number of students to get the average
	average = average/5;

    //looking at test 3, it seems as though the function expects the average to be printed, so I will print it here
    cout << average;

    //returning the average GPA
	return average;
}         
float University::GPA_Max(){
    //use a for loop to go through all the GPAs and find the max
	float max = 0.0;
	for(int i = 0; i< 5; i++){
		if (max < Sooner[i].get_GPA()){
            //if the current max is less than the GPA of the current student, set max to that GPA
			max = Sooner[i].get_GPA();
		}
	}
    //looking at test 3, it seems as though the function expects the max gpa to be printed, so I will print it here
    cout << max;
    //return whatever is found to be the max GPA
	return max;
}         
float University::GPA_Min(){
    //use a for loop to go through all the GPAs and find the min
    //made the gpa min 100 because the max gpa is 4.0 so it will always be less than 100
	float min = 100;
	for(int i =0; i<5; i++){
        //if the current min is greater than the GPA of the current student set min to that GPA
		if (min>Sooner[i].get_GPA()){
			min = Sooner[i].get_GPA();
		}
	}
    //looking at test 3, it seems as though the function expects the min gpa to be printed, so I will print it here
    cout << min;
    //return whatever is found to be the min GPA
	return min;
}

University::University(){
	// Ensure all five Student records start with SID = -1 and GPA = -1
    for (int i = 0; i < 5; ++i) {
        Sooner[i].reset();      // uses Student's reset() (already sets both to -1)
    }
}

// === End of Task 2 ====
int main()
{
	Student x[5];
	University OU;
	// The following two variables can be used 
	// to store inputs, but you don't have to use 
	int sid; 
	float gpa; 


	// ======= Test 1 =======
	// This tests your constructor function, 
	// print function and header files. 
	for (int i = 0; i < 5; i++) {
		x[i].prt_SID(); cout << ' ';
		x[i].prt_GPA(); cout << endl;
	}
	// === End of Test 1 ====

	// ======= Task 3 =======
	// Write a program that takes the SID 
	// and GPA of five students as input 
	// and assigne them to x[5]. 
	// 
	// Formt of the inputs should be (with space)
	// SID1 GPA1 SID2 GPA2 SID3 GPA3 SID4 GPA4 SID5 GPA5

    //using a for loop to take in the student ID and GPA for 5 students
    for(int i = 0; i<5; i++){
        int sid;
        float gpa;
        //taking in the student ID for each student
        cin>> sid;
        //taking in the GPA for each student
        cin >> gpa;
        //setting the student ID and GPA for each student object in the array
        x[i].set_SID(sid);
        x[i].set_GPA(gpa);
    }

	// === End of Task 3 ====
	

	// ======= Test 2 =======
	// This tests your set function
	for (int i = 0; i < 5; i++) {
		x[i].prt_SID(); cout << ' ';
		x[i].prt_GPA(); cout << endl;
	}
	// === End of Test 2 ====


	// ======= Test 3 =======
	// This tests your University class. 
	OU.set_Stu(x);
	OU.GPA_Mean(); cout << ' ';
	OU.GPA_Max();  cout << ' ';
	OU.GPA_Min();  cout << endl;
	// === End of Test 3 ====


	// ======= Test 4 =======
	// This tests your reset function.
	for (int i = 0; i < 5; i++) {
		x[i].reset();
		x[i].prt_SID();	cout << ' ';
		x[i].prt_GPA();	cout << endl;
	}
	// === End of Test 4 ====
	
	return 0;
}

//usage: g++ -o hw1 hw1.cpp
//./hw1
//g++ -o hw1 hw1.cpp && ./hw1