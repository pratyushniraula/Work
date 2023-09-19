//Pratyush Niraula
// pxn210033

/*************************************************************
**************************************************************
only MovieTicket3.cpp fully works 
**************************************************************
**************************************************************/



#include <iostream>
#include <fstream>
#include <string>

using namespace std;

//best available seat function

//display auditorium function
void displayAuditorium(string auditorium[10][26])
{
    //prints the auditorium
    for (int i = 0; i < 10; i++)
    {
        for (int j = 0; j < 26; j++)
        {
            /*
            if (auditorium[i][j] == "."){
            cout << auditorium[i][j];
            }
            */
            if (auditorium[i][j] == "A" || auditorium[i][j] == "S" || auditorium[i][j] == "C"){
                cout << "#";
            }
            else{
                cout << auditorium[i][j];
            }
        }
        cout << endl;
    }
}
//reserve seats function
//void reserveSeats(string auditorium[10][26], int row, int seat, int numSeats, char type)
// {
//     //checks if the seats are available
//     if (type == 'A')
//     {
//         for (int i = 0; i < numSeats; i++)
//         {
//             if (auditorium[row][seat + i] == "A")
//             {
//                 auditorium[row][seat + i] = "S";
//             }
//             else
//             {
//                 cout << "seats not available" << endl;
//             }
//         }
//     }
//     else if (type == 'C')
//     {
//         for (int i = 0; i < numSeats; i++)
//         {
//             if (auditorium[row][seat + i] == "C")
//             {
//                 auditorium[row][seat + i] = "S";
//             }
//             else
//             {
//                 cout << "seats not available" << endl;
//             }
//         }
//     }
//     else if (type == 'S')
//     {
//         for (int i = 0; i < numSeats; i++)
//         {
//             if (auditorium[row][seat + i] == "S")
//             {
//                 auditorium[row][seat + i] = "S";
//             }
//             else
//             {
//                 cout << "seats not available" << endl;
//             }
//         }
//     }
//     else
//     {
//         cout << "invalid type" << endl;
//     }
// }
void reserveSeating(string auditorium[10][65], int row, int seat, int AdultQuantity, int ChildQuantity, int SeniorQuantity)
{
    int totalSeats = AdultQuantity + ChildQuantity + SeniorQuantity;

}

//check availability function

//display report function

int main(int argc, char const *argv[])
{
    cout << "1. Reserve Seats" << endl;
    cout << "2. exit" << endl;
    string choice;

    do{
    cin >> choice;
    if (choice == "1" || choice == "reserve seats")
    {
        //reserve seats
        string auditorium[10][26];
        string filetxt;
        cout << "file name required" << endl;
        cin >> filetxt;
        fstream file;
        file.open(filetxt, ios::in | ios::out | ios::binary);
        if (file.is_open())
        {
            //checks if file is empty
            while(!file.eof()){
                string line;
                getline(file, line);
                if (line == ""){
                    break;
                }
            }
        }
        else
        {
            cout << "file not opened" << endl;
        }
        
        //prints the file
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 26; j++)
            {
                cout << auditorium[i][j];
            }
            cout << endl;
        }

        //reserve seats
        int row;
        char seat;
        int numSeats;
        char type;
        cout << "enter row" << endl;
        cin >> row;
        cout << "enter seat" << endl;
        cin >> seat;
        cout << "enter number of seats" << endl;
        cin >> numSeats;
        cout << "enter type" << endl;
        cin >> type;
        //reserveSeats(auditorium, row, seat-0, numSeats, type);
        //displayAuditorium(auditorium);
        
        
        //file.close();
        //open the other file and write to it
        
    }
    else if (choice == "2" || choice == "exit")
    {
        //exit
        break;
    }
    else
    {
        //loops until valid choice
        cout << "invalid choice" << endl;
    }
    }while(choice == "1" || choice == "reserve seats" || choice != "2" || choice != "exit");
    
    return 0;
}
