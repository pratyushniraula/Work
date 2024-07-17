//newest implementation but this one will be used
#ifndef SBD_H
#define SBD_H
#include <unordered_map>
#include <string>
#include <iostream>
#include <fstream>


//crazy idea but use fork after every action done so that processor can continue to run the program but in the background it will update the file storing the information of everything


class Student{
    private: 
        std::string name;
        std::string studentID;
        std::string password;
        unsigned int grade;
        double GPA;
        unsigned int age;
        std::unordered_map<std::string, int> classes;
        int accessLevel = 1;

    public:

    Student(){
        this->name = "No Record";
        this->studentID = "No Record";
        this->grade = -1;
        this->GPA = -1.00;
        this->age = -1;
    }

    Student(std::string n, std::string id, std::string pass, unsigned int gr, double gpa, unsigned int a){
        this->name = n;
        this->studentID = id;
        this->password = pass;
        this->grade = gr;
        this->GPA = gpa;
        this->age = a;
    }

    //destructor
    //when deleting everything make sure to delete off of the lectures class too or else student will still show up in the class
    ~Student(){
        //delete everything
        delete &name;
        delete &studentID;
        delete &grade;
        delete &GPA;
        delete &age;

        //delete the unordered map
        for (auto it = classes.begin(); it != classes.end(); it++){
            delete &it->first;
            delete &it->second;
        }
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
    void setGPA(double gpa){
        this->GPA = gpa;
    }
    void setAge(int Age){
        this->age = Age;
    }
    void setPassword(std::string pass){
        this->password = pass;
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
    std::string getPassword(){
        return this->password;
    }

};

class Teacher{
    private: 
        std::string name;
        std::string teacherID;
        std::string subject;
        std::unordered_map<std::string, Lecture> classes;
        int accessLevel = 2;
    public:
    Teacher(){
        this->name = "No Record";
        this->teacherID = "No Record";
        this->subject = "No Record";
    }
    Teacher(std::string n, std::string id, std::string sub){
        this->name = n;
        this->teacherID = id;
        this->subject = sub;
    }
    //mutators and accessors
    //mutators
    void setName(std::string n){
        this->name = n;
    }
    void setID(std::string id){
        this->teacherID = id;
    }
    void setSubject(std::string sub){
        this->subject = sub;
    }
    //accessors
    std::string getName(){
        return this->name;
    }
    std::string getID(){
        return this->teacherID;
    }
    std::string getSubject(){
        return this->subject;
    }

};

class Lecture{
    private:
        Teacher teacher;
        std::string name;
        std::string lectureID;
        std::string subject;
        std::unordered_map<std::string, Student> students;

    public:
    Lecture(){
        this->name = "No Record";
        this->lectureID = "No Record";
        this->subject = "No Record";
    }
    Lecture(std::string n, std::string id, std::string sub){
        this->name = n;
        this->lectureID = id;
        this->subject = sub;
    }
    //mutators and accessors
    //mutators
    void setName(std::string n){
        this->name = n;
    }
    void setID(std::string id){
        this->lectureID = id;
    }
    void setSubject(std::string sub){
        this->subject = sub;
    }
    //accessors
    std::string getName(){
        return this->name;
    }
    std::string getID(){
        return this->lectureID;
    }
    std::string getSubject(){
        return this->subject;
    }

    //add student to lecture
    void addStudent(Student s){
        students[s.getID()] = s;
    }

    //remove student from lecture
    void removeStudent(Student s){
        students.erase(s.getID());
    }

    //add teacher to lecture
    void addTeacher(Teacher t){
        teacher = t;
    }

    //remove teacher from lecture
    

};

class admin {
    private:
        std::string password = "admin";
        std::string adminID = "admin";
        int accessLevel = 3;

    public:
        admin(){
            this->password = "admin";
            this->adminID = "admin";
        }

        //mutators and accessors
        //mutators

        void setPassword(std::string p){
            this->password = p;
        }
        void setID(std::string id){
            this->adminID = id;
        }
        //accessors
        std::string getPassword(){
            return this->password;
        }
        std::string getID(){
            return this->adminID;
        }


};

    //********* no working here *********

    // void readStudentFile(std::unordered_map<std::string, Student> &students){
    //     std::ifstream file;
    //     file.open("studentList.txt");
    //     std::string names;
    //     std::string id;
    //     //unsigned int grade;
    //     std::string grade;
    //     //double gpa;
    //     std::string gpa;
    //     //unsigned int age;
    //     std::string age;
    //     std::string newLine;
    //     //read in student file with each line containing either name, id, grade, gpa, age, or newline
    //     while(std::getline(file, names) &&
    //         std::getline(file, id) &&
    //         std::getline(file, grade) &&
    //         std::getline(file, gpa) &&
    //         std::getline(file, age) &&
    //         std::getline(file, newLine)) {
    //         Student s(names, id, grade, std::stod(gpa), std::stoi(age));
    //         students[id] = s;
    //     }
    //     file.close();
    // }

    void readStudentFile(std::unordered_map<std::string, Student> &students){
        std::ifstream file;
        file.open("studentList.txt");
        std::string name;
        std::string id;
        std::string pass;
        //unsigned int grade;
        std::string grade;
        //double gpa;
        std::string gpa;
        //unsigned int age;
        std::string age;
        std::string newLine;
        // read in student file with each line containing either name, id, grade, gpa, age, or newline
        try{
        while (std::getline(file, name) &&
               std::getline(file, id) &&
               std::getline(file, pass) &&
               std::getline(file, grade) &&
               std::getline(file, gpa) &&
               std::getline(file, age) &&
               std::getline(file, newLine)) {
            
            Student s;
            s.setName(name);
            s.setID(id);
            s.setPassword(pass);
            s.setGrade(std::stoi(grade));
            s.setGPA(std::stod(gpa));
            s.setAge(std::stoi(age));
            students.insert({id, s});
            std::cout << "..." << std::endl;
        }

        file.close();
        } catch (std::exception e){
            std::cout << "studentList.txt Error: " << e.what() << std::endl;
        }
    }

    void readTeacherFile(std::unordered_map<std::string, Teacher> &teachers){
        std::ifstream file;
        file.open("teacherClassDB.txt");
        std::string name;
        std::string id;
        std::string subject;
        std::string newLine;
        // read in teacher file with each line containing either name, id, subject, or newline
        while (std::getline(file, name) &&
               std::getline(file, id) &&
               std::getline(file, subject) &&
               std::getline(file, newLine)) {
            Teacher t;
            t.setName(name);
            t.setID(id);
            t.setSubject(subject);
            teachers.insert({id, t});
        }

        file.close();
    }

    void readLectureFile(std::unordered_map<std::string, Lecture> &lectures){
        std::ifstream file;
        file.open("lectureDB.txt");
        std::string name;
        std::string id;
        std::string subject;
        std::string newLine;
        // read in lecture file with each line containing either name, id, subject, or newline
        while (std::getline(file, name) &&
               std::getline(file, id) &&
               std::getline(file, subject) &&
               std::getline(file, newLine)) {
            Lecture l;
            l.setName(name);
            l.setID(id);
            l.setSubject(subject);
            lectures[id] = l;
        }

        file.close();
    }

#endif
