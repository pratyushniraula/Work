/******************************************************************************
stores a char instead of an array value first part 2 fixing shit good now
*******************************************************************************/

#include <iostream>
#include <fstream>
#include <iomanip>
#include <string>
#include <cctype>
#include <cstring>
using namespace std;

int main() {
    string puzzleNumber;
    char individualnum[9][9];
    string line;
    int puzzleStore[9][9];
    int solve = 0;//see if puzzle is solved (if zero then solved)
    bool valid = true;
    
    string fileName;
    cout << "Enter the name of the file: ";
    cin >> fileName;
    fstream file;
    file.open(fileName, ios::in | ios::out | ios::binary);
    
    if (!file.good()){
        cout << "file is not working properly, error bit says no good" << endl;
        return 1;
    }
    
    
    //while loop for 1 puzzle iteration
    while (getline(file, puzzleNumber)) {
        string puzzleLine;
        for (int i = 0; i < 9; i++) {
            getline(file, puzzleLine);
            for (int j = 0; j < 9; j++) {
                if (puzzleLine[j] == ' ') { // check if input is a space
                    puzzleStore[i][j] = 0; // store a 0 if input is a space
                    solve++; //will make solve a nonzero which will help with validity test
                }
                else {
                    puzzleStore[i][j] = puzzleLine[j] - '0'; // convert char to int and store in array
                }
                individualnum[i][j] = puzzleLine[j];
            }
        }
    }
    if(solve == 0){
        cout << puzzleNumber << "\tsolved" <<endl;
    }
    else{
    // Check repeated numbers in each row
    for (int i = 0; i < 9; i++) {
        int rowFreq[10] = {0};
        for (int j = 0; j < 9; j++) {
            int num = puzzleStore[i][j];
            if (num != 0 && rowFreq[num] == 1) {
                cout << puzzleNumber << " invalid\t" << "row " << i+1
                << " has multiple " << num  << "s" << endl;
            }
            rowFreq[num] = 1;
        }
    }
    //columns
    for (int col = 0; col < 9; col++) {
        int colFreq[10] = {0};
        for (int row = 0; row < 9; row++) {
            int num = puzzleStore[row][col];
            if (num != 0) {
                colFreq[num]++;
                if (colFreq[num] > 1) {
                    // duplicate found in this column
                    cout << puzzleNumber << " invalid\t" << "column " << col+1
                    << " has multiple "  << num << "s"<< endl;
                }
            }
        }
    }

    // Check for duplicates in boxes
    for (int boxRow = 0; boxRow < 9; boxRow += 3) {
        for (int boxCol = 0; boxCol < 9; boxCol += 3) {
            int boxFreq[10] = {0};
            for (int row = boxRow; row < boxRow + 3; row++) {
                for (int col = boxCol; col < boxCol + 3; col++) {
                    int num = puzzleStore[row][col];
                    if (num != 0) {
                        if (++boxFreq[num] > 1) {
                            cout << puzzleNumber << " invalid\t";
                            if (boxRow == 0 && boxCol == 0) {
                                cout << "upper left";
                            } else if (boxRow == 0 && boxCol == 3) {
                                cout << "upper middle";
                            } else if (boxRow == 0 && boxCol == 6) {
                                cout << "upper right";
                            } else if (boxRow == 3 && boxCol == 0) {
                                cout << "middle left";
                            } else if (boxRow == 3 && boxCol == 3) {
                                cout << "middle";
                            } else if (boxRow == 3 && boxCol == 6) {
                                cout << "middle right";
                            } else if (boxRow == 6 && boxCol == 0) {
                                cout << "lower left";
                            } else if (boxRow == 6 && boxCol == 3) {
                                cout << "lower middle";
                            } else if (boxRow == 6 && boxCol == 6) {
                                cout << "lower right";
                            }
                            cout << " has multiple " << num << "s" << endl;
                            valid = false;
                        }
                    }
                }
            }
        }
    }
}
    /*
    //prints array
    cout << puzzleNumber << endl;
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            cout << puzzleStore[i][j] << " ";
        }
        cout << endl;
    }
    */
    file.close();
    return 0;
}
