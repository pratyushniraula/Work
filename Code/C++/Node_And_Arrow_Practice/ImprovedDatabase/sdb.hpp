//newest implementation but this one will be used
#ifndef SBD_H
#define SBD_H
#include <unordered_map>

class Student{
    private: 
        std::string name;
        std::string studentID;
        unsigned int grade;
        double GPA;
        unsigned int age;
        unordered_map<std::string, int> classes;

    public:

    Student(){
        this->name = "No Record";
        this->studentID = "No Record";
        this->grade = -1;
        this->GPA = -1.00;
        this->age = -1;
    }

    Student(std::string n, std::string id, unsigned int gr, double gpa, unsigned int a){
        this->name = "n";
        this->studentID = id;
        this->grade = gr;
        this->GPA = gpa;
        this->age = a;
    }
    //mutators and accessors
    //mutators
    void setName(std::string n){
        this->name = n;
    }
    void setID(std::string id){
        this->studentID = id;
    }
    void setGrade(int gr){
        this->grade = gr;
    }
    void setID(double gpa){
        this->GPA = gpa;
    }
    void setAge(int Age){
        this->age = Age;
    }


    //accessors
    std::string getName(){
        return this->name;
    }
    std::string getID(){
        return this->studentID;
    }
    int getGrade(){
        return this->grade;
    }
    double getGpa(){
        return this->GPA;
    }
    int getAge(){
        return this->age;
    }

};



#endif