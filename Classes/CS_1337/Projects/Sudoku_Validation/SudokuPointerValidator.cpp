#include <iostream>
#include <fstream>
#include <cstdlib>
#include <unordered_set>

using namespace std;
//2.2
const int SIZE = 9;

string get_box_location(int row, int col) {
    if (row == 0) {
        if (col == 0) return "upper left";
        else if (col == 1) return "upper middle";
        else return "upper right";
    } else if (row == 1) {
        if (col == 0) return "left";
        else if (col == 1) return "middle";
        else return "right";
    } else {
        if (col == 0) return "lower left";
        else if (col == 1) return "lower middle";
        else return "lower right";
    }
}

bool validate_puzzle(int* puzzle, int puzzleNumber) {
    bool valid = true;
    for (int i = 0; i < SIZE; i++) {
        unordered_set<int> row_set;
        unordered_set<int> col_set;
        unordered_set<int> box_set;

        for (int j = 0; j < SIZE; j++) {
            int row_index = i * SIZE + j;
            if (puzzle[row_index] != 0) {
                if (row_set.find(puzzle[row_index]) != row_set.end()) {
                    cout << puzzleNumber << "   invalid    row " << i+1 << " has multiple " << puzzle[row_index] << "s" << endl;
                    valid = false;
                } else {
                    row_set.insert(puzzle[row_index]);
                }
            }

            int col_index = i + j * SIZE;
            if (puzzle[col_index] != 0) {
                if (col_set.find(puzzle[col_index]) != col_set.end()) {
                    cout << puzzleNumber << "   invalid    column " << i+1 <<  " has multiple " << puzzle[col_index] << "s" << endl;
                    valid = false;
                } else {
                    col_set.insert(puzzle[col_index]);
                }
            }

            int box_row = 3 * (i / 3) + j / 3;
            int box_col = 3 * (i % 3) + j % 3;
            int box_index = box_row * SIZE + box_col;

            if (puzzle[box_index] != 0) {
                if (box_set.find(puzzle[box_index]) != box_set.end()) {
                    string box_location = get_box_location(box_row / 3, box_col / 3);
                    cout << puzzleNumber << "   invalid    " << box_location <<  " has multiple " << puzzle[box_index] << "s" << endl;
                    valid = false;
                } else {
                    box_set.insert(puzzle[box_index]);
                }
            }
        }
    }
    return valid;
}

int main() {
   string file;
    cout << "enter file name: ";
   cin >> file;
    // Open the file for reading
    ifstream input_file(file);
    if (!input_file.is_open()) {
        cout << "Error: Unable to open file" << endl;
        return 1;
    }

    int puzzle_count = 0;
    int puzzle_number;

    // Read the puzzles from the file
    while (input_file >> puzzle_number) {
        puzzle_count++;

        int *puzzle = new int[SIZE * SIZE];

        for (int i = 0; i < SIZE * SIZE; i++) {
            char c;
            if (!input_file.get(c)) {
                delete[] puzzle;
                return 1;
            }
            if (c == '\n') { // if newline, skip
                i--;
                continue;
            } else if (c == ' ') { // if space, replace with 0
                *(puzzle+i) = 0;
            } else {
                *(puzzle+i) = atoi(&c);
            }
        }

        // Validate the puzzle
        if (validate_puzzle(puzzle, puzzle_number)) {
            cout << puzzle_number << "    valid" << endl;
        }

        // Deallocate the dynamically allocated memory
        delete[] puzzle;
    }

    // Close the file
    input_file.close();

    return 0;
}
