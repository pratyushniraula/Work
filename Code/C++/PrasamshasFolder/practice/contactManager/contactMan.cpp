//read from file
//store everything in memory
//read the contacts in console
//be able to edit if the user wants to
//display the contacts and who to edit
//use arrays to store data
//everything is running inside of a while loop until the user says to exit
//figure out how to end the while loop if the user wants to exit
//to run program: g++ contactMan.cpp
//                ./a.out

#include <fstream> //used to read from files
#include <iostream>
#include <string>
//add file library



using namespace std;

int main ()
{

    string line; 
    ifstream myfile("samplecontacts.txt");  //input file
    int option;

    if (myfile.is_open()){
        
            cout<<"What do you want to do with the contacts: "<<endl;
            cout<<"1.display\n2.exit\n3.edit\n";
            cin>>option;
            do{
            if(option==1){ 
                while(getline(myfile,line)){
                    cout<<line<<endl;
                }
            myfile.close();
            } 
            else if (option==2){
                cout<<"The program has stopped";
                return 1;
            }

            else if (option==3){
                cout<<"Do you want to add a contact, remove a contact, or edit a contact";
                if(myfile.is_open()){
                    string myArray[19];
                    for(int i =0;i<19;i++){
                        myfile>>myArray[i];
                    }

                }
                //if choosing edit, ask user if they want to add a contact, remove a contact or edit a contact

            
            }
            } while(1==1);
           

        int array[5] = {72,34,56,75,42};
        array[1]
    
    }

    
    
    
    
    //initialize all variables
    //read from the for loop
    //REMEMBER: when you open a file YOU MUST CLOSE THE FILE!

    //create a while loop that will have everything running again
        //inside the while loop ask the user for input on what they want to do with the contacts
        //complete the operations for if they enter "exit" "edit" "display"
            
            //hint: exit you may use command break; or create a flag for the while loop

            //display

            //hint: edit: you need to display an indicator for the names of all the people in the contacts
            //this indicator will tell you where they are in the array
            //using this indicator you want to prompt the user to enter the information to update the contact
        
    //upon exiting the while loop you want to update the contact information from the same file that you got your input from
    //this requires ofstream, this in turn will delete the information previously on the files
    //remember: CLOSE FILE AFTER USE



    return 0;
}
