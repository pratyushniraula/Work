// Pratyush Niraula
// pxn210033

#include <iostream>
#include <fstream>
#include <string>

using namespace std;
/*things to keep in mind

/*************************************************************
**************************************************************
only MovieTicket3.cpp fully works 
**************************************************************
**************************************************************/*/

//display auditorium function 
void displayAuditorium(string auditorium[10][26], int row)
{
    int k = 0;
    cout << "    ";
    //prints the column letters
    while(auditorium[0][k] != ""){
        cout << (char)(k+65);
        k++;
    }
    cout << endl;

    //prints the auditorium
    for (int i = 0; i < row; i++)
    {
        cout << i + 1 << "   ";
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
            else if (auditorium[i][j] == "."){
                cout << auditorium[i][j];
            }
        }
        cout << endl;
    }
}

char bestAvailable(string auditorium[10][26], int row, int colIndex, int adult, int senior, int child){
    //best available seats function
    int seat;
    int total = adult + senior + child;
    int bestSeat = 0;
    int bestV = colIndex+1;
    int v = colIndex+1;
    int middle;
    for (int i = 0; i < (colIndex - total); i++){
        bool available = true;
        for(int j = 0; j < total; j++){
            if (auditorium[row][i + j] == "A" || auditorium[row][i + j] == "S" || auditorium[row][i + j] == "C"){
                available = false;
                break;
            }
        }
        if(available){
            middle = (i + (i + total))/2;//middle seat
            v = abs(middle - (colIndex/2));//how close you are to the middle of the row
            if (v < bestV){
                v = bestV;
                bestSeat = i;
            }
        }
        
    
    }
    return (bestSeat+65);
}

bool checkSeats(string auditorium[10][26], int row, int seat, int adult, int senior, int child){
    //check if seats are available
    int totalSeats = adult + senior + child;

    for (int i = 0; i < totalSeats; i++){
        if (auditorium[row][seat + i] == "A" || auditorium[row][seat + i] == "S" || auditorium[row][seat + i] == "C"){
            return false;
        }
    }
    return true;
}

//reserve seats function
void reserveSeats(string auditorium[10][26], int row, int colIndex, int seat, int adult, int senior, int child){
    char yesNo;
    if (checkSeats(auditorium, row, seat, adult, senior, child)){
        //reserve seats
        cout << "your seats have been reserved" << endl;
        while (adult > 0){
            auditorium[row][seat] = "A";
            seat++;
            adult--;
        }
        
        while (child > 0){
            auditorium[row][seat] = "C";
            seat++;
            child--;
        }
        
        while (senior > 0){
            auditorium[row][seat] = "S";
            seat++;
            senior--;
        }
        
    }
    else{
        //display best available seats
        cout << "Although we do not have these seats in particular, these are the closest seats we have to the ones"; 
        cout << " that you have picked: " << row+1 << bestAvailable(auditorium, row, colIndex, adult, senior, child);
        if ((adult + child + senior) > 1){
            char lastSeat = bestAvailable(auditorium, row, colIndex, adult, senior, child) + (adult + child + senior) - 1;
            cout << " - " << row+1 << lastSeat;
        }
        cout << "\nwould you still like to confirm these seats? (y/n)" << endl;
        //if yes, reserve seats
        cin >> yesNo;
        if (yesNo == 'y'){
            int NewSeat = bestAvailable(auditorium, row, colIndex, adult, senior, child) - 65;
            //reserve seats
            cout << "your seats have been reserved" << endl;
            while (adult > 0){
                auditorium[row][NewSeat] = "A";
                NewSeat++;
                adult--;
            }
            
            while (child > 0){
                auditorium[row][NewSeat] = "C";
                NewSeat++;
                child--;
            }
            
            while (senior > 0){
                auditorium[row][NewSeat] = "S";
                NewSeat++;
                senior--;
            }
            
        }
        else{
            cout << "why did you pick seats that were already taken?" << endl;//this is a joke
        }
    }
}

void displayReport(string auditorium[10][26], int colIndex, int rowIndex){
    int adult = 0;
    int child = 0;
    int senior = 0;
    int totalTickets = 0;
    double totalSales = 0.0;

    //report calculations
    for (int i = 0; i < rowIndex; i++){
        for (int j = 0; j < colIndex; j++){
            if (auditorium[i][j] == "A"){
                adult++;
                totalTickets++;
                totalSales += 10.0;
            }
            else if (auditorium[i][j] == "C"){
                child++;
                totalTickets++;
                totalSales += 5.0;
            }
            else if (auditorium[i][j] == "S"){
                senior++;
                totalTickets++;
                totalSales += 7.50;
            }
        }
    }
    //display auditorium seats in file
    ofstream filey;
    filey.open("A1.txt");
    for (int i = 0; i < rowIndex; i++)
    {
        for (int j = 0; j < colIndex; j++)
        {
            filey << auditorium[i][j];
        }
        filey << endl;
    }
    //display total seats
    cout << "total seats: " << rowIndex*colIndex << endl;
    //display total tickets
    cout << "total tickets: " << totalTickets << endl;
    //display total adult child senior tickets
    cout << "adult tickets: " << adult << endl;
    cout << "child tickets: " << child << endl;
    cout << "senior tickets: " << senior << endl;
    //display total sales
    cout << "total sales: " << totalSales << endl;
    
}

//good main function?
int main(){
 ///////////////////////////////////////////////
    string auditorium[10][26];
    string filetxt;
    int rowIndex = 0;
    int columnIndex = 0;
    string choice;
    cout << "file name required" << endl;
    cin >> filetxt;
    fstream file;
    file.open(filetxt, ios::in);
            
    if (file.is_open())
    {
        string line;
        while(getline(file, line)){
            for (int j = 0; j < 26; j++)
            {
                if (line[j] == '.' || line[j] == 'A' || line[j] == 'S' || line[j] == 'C'){
                    auditorium[rowIndex][j] = line[j];
                    columnIndex++;
                }
                else{
                    break;
                    }
            }
            rowIndex++;
        }
        file.close();
        columnIndex = columnIndex/rowIndex;
        //file is successfully copied into auditorium array
    }

    /////////////////////////////////////////////////////////////
    do{
        cout << "1. Reserve Seats" << endl;
        cout << "2. exit" << endl;
        cin >> choice;

        if (choice == "1" || choice == "reserve seats"){
       //add all calculations and functions here
            int row = -1;
            char col = -1;
            int adult = -1;
            int child = -1;
            int senior = -1;
            
            // Call the display function to print the auditorium for the consumer only on the console
            displayAuditorium(auditorium, rowIndex);
           
            //prompt for row
            // while (row > rowIndex || row < 0){
            //     cout << "Enter row: ";
            //     cin >> row;
            //     if (row > rowIndex || row < 0){
            //         cout << "Invalid row" << endl;
            //     }
            // }
            bool invalidRow = true;
            bool invalidCol = true;
            bool invalidAdult = true;
            bool invalidChild = true;
            bool invalidSenior = true;

            do{
                cout << "Enter row: ";
                cin >> row;
                if (row >= rowIndex || row < 0){
                    cout << "Invalid row" << endl;
                }
                else{
                    invalidRow = false;
                }
                
            }while (invalidRow);


            //prompt for starting seat/column
            while ((col-65) >= columnIndex || (col-65) < 0){
                cout << "Enter starting seat: ";
                cin >> col;
                if ((col-65) >= columnIndex || (col-65) < 0){
                    cout << "Invalid starting seat" << endl;
                }
            }

            // do{
            //     cout << "Enter starting seat: ";
            //     cin >> col;
            //     if ((col-65) >= columnIndex || (col-65) <= 0){
            //         cout << "Invalid starting seat" << endl;
            //     }
            //     else{
            //         invalidCol = false;
            //     }
                
            // }while (invalidCol);

            //number of adult tickets
            while (adult < 0){
                cout << "Enter number of adult tickets: " << endl;
                cin >> adult;
                if (adult < 0){
                    cout << "Invalid number of adult tickets" << endl;
                }
            }
            //number of child tickets
            while (child < 0){
                cout << "Enter number of child tickets: " << endl;
                cin >> child;
                if (child < 0){
                    cout << "Invalid number of child tickets" << endl;
                }
            }
            //number of senior tickets
            while (senior < 0){
                cout << "Enter number of senior tickets: " << endl;
                cin >> senior;
                if (senior < 0){
                    cout << "Invalid number of senior tickets" << endl;
                }
            }
            //check if seats are available
            reserveSeats(auditorium, (row-1), columnIndex, (col-65), adult, senior, child);
            //if seats are available, reserve seats
            //if seats are not available, display best available seats
            
        
        }
    }while(choice == "1" || choice == "reserve seats" /*|| choice != "2" || choice != "exit"*/);
    
    displayReport(auditorium, columnIndex, rowIndex);

    return 0;
}
