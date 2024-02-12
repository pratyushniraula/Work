//failed  hpp file for generic hashmap
#ifndef SBD2_H
#define SBD2_H
#include <string>
#include <vector>

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

    //search for teacher or student objects
    T search(std::string id){
        std::hash<std::string> hasher;
        //string is given a hashcode
        int s = hasher(id);
        int index = s % this->size;

        //seperate chaining
        Node<T>* ptr = this->array[index];

        while(ptr != nullptr){
            if(ptr->data.getID() == id){
                return ptr->data;
            }
            else{
                ptr = ptr->next;
            }
        }
        return nullptr;
    }
};

#endif