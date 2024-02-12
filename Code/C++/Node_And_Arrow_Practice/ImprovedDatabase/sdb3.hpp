//original implementation of the hashmap usage
//too many complications occured and so an unordered map will be used in the main file for studentDatabase2.cpp
//the personal hashmap class will be implemented in this file in the future for the studentDatabase3.cpp file when it is created

#ifndef SBD3_H
#define SBD3_H
#include <string>
#include <vector>
#include <iostream>

class Student{
    private: 
        std::string name;
        std::string studentID;
        unsigned int grade;
        double GPA;
        unsigned int age;
        hashmap<StudentGradeInClass> curClasses;

    public:

    Student(){
        this->name = "No Record";
        this->studentID = "No Record";
        this->grade = -1;
        this->GPA = -1.00;
        this->age = -1;
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
    void addToMap(StudentGradeInClass &S){
        this->curClasses.insert(S);
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
    std::string getGradeInClass(){
       //get all grades in classes

       for(int i = 0; i < this->curClasses.getSize(); i++){
           //get the grade in the class
           while(this->curClasses.getArray()[i] != nullptr){
               std::cout << this->curClasses.getArray()[i]->getData().getGrade() << std::endl;
               this->curClasses.getArray()[i] = this->curClasses.getArray()[i]->getNext();
           }
           //get the class name
           //get the teacher name
           //get the class id
       }

    }

};

class StudentGradeInClass{
    private:
    int grade;
    Classes* lecture; // Use a pointer to Classes instead of object directly
    std::string idNum;

    public:
    //constructors
    StudentGradeInClass(){
        lecture = nullptr; // Initialize the pointer to nullptr
    }
    StudentGradeInClass(int gr, Classes* l){ // Use a pointer to Classes instead of object directly
        this->lecture = l;
        this->grade = gr;
        this->idNum = l->getID(); // Convert string to int using std::stoi
    }
    ~StudentGradeInClass(){
        delete this->lecture;
    }

    //mutators and accessors
    //mutators
    void setGrade(int gr){
        this->grade = gr;
    }
    void setLecture(Classes* l){ // Use a pointer to Classes instead of object directly
        this->lecture = l;
    }
    void setID(std::string id){
        this->idNum = id;
    }

    //accessors
    int getGrade(){
        return this->grade;
    }
    Classes* getLecture(){ // Use a pointer to Classes instead of object directly
        return this->lecture;
    }
    std::string getID(){
        return this->idNum;
    }
};

class Classes{
    private:
    std::string subject;
    teacher Teacher;
    std::string classID;
    hashmap<Student> students;

    public:
    //mutators and accessors
    //mutators
    void setSubject(std::string sub){
        this->subject = sub;
    }
    void setTeacher(teacher teach){
        this->Teacher = teach;
    }
    void setID(std::string id){
        this->classID = id;
    }
    //add hashmap editors


    //accessors
    std::string getSubject(){
        return this->subject;
    }
    teacher getTeacher(){
        return this->Teacher;
    }
    std::string getID(){
        return this->classID;
    }



};

class teacher{
    private: 
        std::string name;
        std::string teacherID;
        //vector<Classes> classes;

    public:
        //mutators and accessor
        //mutators
        void setName(std::string n){
            this->name = n;
        }
        void setID(std::string id){
            this->teacherID = id;
        }


        //accessors
        std::string getName(){
            return this->name;
        }
        std::string getID(){
            return this->teacherID;
        }
};

template<typename T>
class Node{
    private:
    T data;
    Node* next;
    
    public:
    //mutators and accessors
    //accessors
    T getData(){
        return this->data;
    }
    
    Node* getNext(){
        return this->next;
    }

    //mutators
    void setData(T d){
        this->data = d;
    }

    void setNext(Node* n){
        this->next = n;
    }
};

template<typename T>
class hashmap{
    //this will contain the hashmap until rehash is required
    private:
    int size;
    int currentSize;
    Node<T>** array;
    const int lambda = 2; //load factor for a seperate chaining hashmap


    public: 
    //constructor and destructor
    hashmap(){
        this->size = 11;
        this->array = new std::string[size];

        for(int i = 0; i > size; i++){
            this->array[i] = nullptr;
        }
      }
    ~hashmap(){
        for(int i = 0; i < size; i++){
            delete this->array[i];
        }
        delete[] this->array;
    }

    //accessors and mutators
    //accessors
    int getSize(){
        return this->size;
    }
    int getCurrentSize(){
        return this->currentSize;
    }
    Node<T>** getArray(){
        return this->array;
    }

    //mutators
    void setSize(int s){
        this->size = s;
    }
    void setCurrentSize(int cs){
        this->currentSize = cs;
    }
    void setArray(Node<T>** arr){
        this->array = arr;
    }


    //insert for teacher and student objects
    void insert(T& data){
        //find loadfactor
        double loadfactor = this->currentSize / this->size;
        if(loadfactor > lambda){
            this->array = rehash(this->array);
        }

        std::hash<std::string> hasher;
        //string is given a hashcode
        int s = hasher(data.getID());
        int index = s % this->size;
        
        //seperate chaining
        Node<T>* newNode = new Node<T>;
        newNode->data = data;
        newNode->next = nullptr;

        if(this->array[index] == nullptr){
            this->array[index] = newNode;
        }
        else{
            //point to the head of the array index
            Node<T>* ptr = this->array[index];
            //append to the head of the array
            newNode->next = ptr;
            this->array[index] = newNode;
        }

    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    void rehash(T& arr){
        int newsize = this->size * 2 + 1;
        T* newArray = new T[newsize];

        //make all elements of the array null
        for(int i = 0; i < newsize; i++){
            newArray[i] = nullptr;
        }

        T* arr = this->array;
        this->array = newArray;
        for(int i = 0; i < this->size; i++){
            //rehashing
            std::hash<std::string> hasher;

           //go to the end of the list of the old array and add that to the new array 
            while(arr[i] != nullptr){
                int s = hasher(arr[i]->getID());
                int index = s % newsize;

                //seperate chaining
                Node<T>* newNode = new Node<T>;
                newNode->data = arr[i];
                newNode->next = nullptr;

                if(this->array[index] == nullptr){
                    this->array[index] = newNode;
                }
                else{
                    //point to the head of the array index
                    Node<T>* ptr = this->array[index];
                    //append to the head of the array
                    newNode->next = ptr;
                    this->array[index] = newNode;
                }
                arr[i] = arr[i]->next;
            }
        }

        this->size = newsize;
        //delete the old array
        delete[] arr;

    }

    void removeNode(T& data){
        std::hash<std::string> hasher;
        //string is given a hashcode
        int s = hasher(data.getID());
        int index = s % this->size;

        //seperate chaining
        Node<T>* ptr = this->array[index];
        Node<T>* ptr1 = this->array[index];

        while(ptr != nullptr){
            if(ptr->data.getID() == data.getID()){
                ptr1->next = ptr->next;
                delete ptr;
            }
            else{
                ptr1 = ptr;
                ptr = ptr->next;
            }
        }
    }

};


#endif