

#include <string>
#include <iostream>
#include <map>
#include <fstream>
using namespace std;

int romanToInt(string romannum) {
    //map to correlate letters and numbers
    map<char, int> lettonum{ {'I', 1}, {'V', 5}, {'X', 10}, {'L', 50}, {'C', 100}, {'D', 500}, {'M', 1000}};
    /*arabnum is the arabic number total, where the algorithm in the for loop will give the number
    converted from roman numeral*/
    int arabnum = 0;
    for (int i = 0; i < romannum.length(); i++) {
        //value will assess if the current character it is on has a < or > value then the next character
        int value = lettonum[romannum[i]];
        if (i + 1 < romannum.length() && value < lettonum[romannum[i + 1]]) {
            /*if statement accounts for IV's IX's XL's and others
            done by seeing if first there is something next and if that next is bigger
            which then it will subtract value from the number*/
            arabnum -= value;
        } else {
            //if next is smaller you just add whatever 'value' is to the number
            arabnum += value;
        }
    }
    return arabnum;
}

int main() {
    string inputFile;
    string roman;
    //getting/opening the file
    cout << "Enter the name of the file: ";
    cin >> inputFile;
    fstream romnumfile;
    romnumfile.open(inputFile, ios::in | ios::out | ios::binary);
    
    //checks if the file is not good
    if (!romnumfile.good()){
        cout << "file is not working properly, error bit says no good";
        return 1;
    }
    
    //reads and runs the function line by line
    while(getline(romnumfile, roman)) {
        int num = romanToInt(roman);
        string str = to_string(num);
        //find position of the pointer
        int position = romnumfile.tellg();
        //clears the eof flag
        if (romnumfile.eof()){
            romnumfile.clear();
            romnumfile << "\n";
            continue;
        }
        //places number in the right location
        romnumfile.seekp(position-5);
        romnumfile << str;
        romnumfile.seekp(position);
    }
    
    romnumfile.close();
    return 0;
}
