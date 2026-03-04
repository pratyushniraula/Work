/*
Program Name: Vertical Additive Symmetric Matrix

Date: September 4, 2024

Author: Prasamsha Dahal

Module Purpose : The purpose of the program is to read matrices from a file, 
verify for vertical additive symmetry , and display the results. It also shows 
the input matrix, column sums, whether the matrix is symmetric, and the matrix 
with its rows sorted in ascending order. The user can also view each matrix step 
by step.
*/

#include <string>
#include <iostream>
#include <fstream>
#include <iomanip>

using namespace std;

const unsigned MAX_MATRIX_SIZE   = 20;
const unsigned WIDTH_DISPLAY     = 12;

bool    readMatrixFromFile (int [][MAX_MATRIX_SIZE], unsigned&, unsigned&, ifstream&);
void         displayMatrix (int [][MAX_MATRIX_SIZE], unsigned,  unsigned);
void displayCalcMatrixSums (int [][MAX_MATRIX_SIZE], unsigned,  unsigned,  int[MAX_MATRIX_SIZE]);
void   symmetryCheckMatrix (int [], unsigned,  unsigned);
void        sortMatrixRows (int [][MAX_MATRIX_SIZE], unsigned,  unsigned);


int main() {
  //variable initialization
  unsigned height,
           width;
  int      matrix[MAX_MATRIX_SIZE][MAX_MATRIX_SIZE],
           colSums[MAX_MATRIX_SIZE];
  string   INPUT_FILE_NAME = "matrixes.txt";
  ifstream inputFileStreamObj(INPUT_FILE_NAME);

  //check if the file is open
  if (inputFileStreamObj.fail()) {
     cout << "File " << INPUT_FILE_NAME << "could not be opened !" << endl; 
     cout << endl << "Press the enter key once or twice to leave..." << endl; cin.ignore(); cin.get();
     exit(EXIT_FAILURE); 
   }

   do {  
     //read the matrix from the file
     //break if the file is at the end
     if (readMatrixFromFile(matrix, height, width, inputFileStreamObj) )
       break;

      //display the matrix and the calculations found from the functions
     cout << "Input:" << endl;
     displayMatrix        (matrix, height, width);
     displayCalcMatrixSums(matrix, height, width, colSums);
     symmetryCheckMatrix  (colSums, width, height);
     sortMatrixRows       (matrix,  width, height);
     cout << "Sorted:" << endl;
     displayMatrix        (matrix, height, width);
     //go to the next matrix in the file if applicable
     cout << endl << "Press the enter key once or twice to continue..." << endl; cin.ignore(); cin.get(); 
     
   } while (true);

   cout << "Program Done" << endl;

   exit(EXIT_SUCCESS);
}

bool readMatrixFromFile(int matrix[][MAX_MATRIX_SIZE], unsigned& height, unsigned& width, ifstream& inputFileStreamObj) {

   //check if file is open and not at the end of file
   if(!(inputFileStreamObj.is_open())) {
     return true;
   }

   //read the height and width of the matrix
   inputFileStreamObj >> height >> width;

   //read the matrix from the file, starting from the row 0 to height and column 0 to width
   for(int i = 0; i < height; i++) {
     for(int j = 0; j < width; j++) {
       //read the matrix from the file
       inputFileStreamObj >> matrix[i][j];
     }
   }
   //if it is the end of the file close the file and return true
   //return true since main has it set to break the loop upon true
   if(inputFileStreamObj.eof()) {
     inputFileStreamObj.close();
     return true;
   }

   //return false if all goes well
   return false;
}


void displayMatrix(int matrix[][MAX_MATRIX_SIZE], unsigned height, unsigned width) {
   //display the matrix using nested for loop since it is necessary to display the matrix
   //go from row 0 to height and column 0 to width
   for(int i = 0; i < height; i++) {
     for(int j = 0; j < width; j++) {
        //display each element of the matrix with a width of 12
       cout << setw(WIDTH_DISPLAY) << matrix[i][j];
    }
    //end the line after each row
    cout << endl;
   }
   //end the line after the matrix is displayed
   cout << endl;
}


void displayCalcMatrixSums(int matrix[][MAX_MATRIX_SIZE], unsigned height, unsigned width, int colSums[MAX_MATRIX_SIZE]) {
   cout << "Sums:" << endl;
   //calculate the sum of each column of the matrix and display it
   for(int i = 0; i < width; i++) {
     //initialize the sum of each column to 0
     colSums[i] = 0;
     for(int j = 0; j < height; j++) {
       //add each individual element of the column to the sum
       colSums[i] += matrix[j][i];
     }
     //display the sum of each column with a width of 12
     cout << setw(WIDTH_DISPLAY) << colSums[i];
   }
   cout << endl;
}
 

void symmetryCheckMatrix(int colSums[], unsigned width, unsigned height) {
   cout << "Vertical additive Symmetry: ";
   //create a flag so that if the matrix is not symmetric it will be set to false
   bool isSymmetric = true;
   //check if the sum of the columns are symmetric
   for(int i = 0; i < width/2; i++){
     //if the sums are not equal for the opposite sides then print no
     if(colSums[i] != colSums[width - i - 1]) {
       isSymmetric = false;
       cout << "No" << endl;
       break;
    }
   }
   //if the flag was not triggered then the matrix is symmetric
   if(isSymmetric){
    cout << "Yes" << endl;
   }

}

void sortMatrixRows(int matrix[][MAX_MATRIX_SIZE], unsigned width, unsigned height) {
    //for each row of the matrix sort the elements in ascending order
    for(int i = 0; i < height; i++) {
      //bubble sort each row of the matrix
      for(int j = 0; j < width; j++) {
        //traverse the row and swap the elements if they are not in order
        for(int k = 0; k < width - j - 1; k++) {
          //if the element is greater than the next element swap them
          if(matrix[i][k] > matrix[i][k + 1]) {
            //swap the elements using a temporary variable
            int temp = matrix[i][k];
            matrix[i][k] = matrix[i][k + 1];
            matrix[i][k + 1] = temp;
          }
        }
      }
    }
 }
