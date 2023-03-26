//pxn210033
//Pratyush Niraula
//1337.008
//02/12/23
#include <string>
#include <iostream>
#include <map>
#include <vector>
#include <utility>
#include <fstream>
using namespace std;

const string database = "numbers.txt";  
//your filestream for the database will connect to this variable

//*************roman to arabic*************
int romanToInt(string romannum) {
    // hashmap to correlate letters and numbers
    map<char, int> lettonum{{'I', 1}, {'V', 5}, {'X', 10}, {'L', 50}, {'C', 100}, {'D', 500}, {'M', 1000}};
    /*arabnum is the arabic number total, where the algorithm in the for loop will give the number
    converted from roman numeral*/
    int arabnum = 0;
    for (auto i = 0; i < romannum.length(); i++) {
        // value will assess if the current character it is on has a < or > value then the next character
        int value = lettonum[romannum[i]];
        if (i + 1 < romannum.length() && value < lettonum[romannum[i + 1]]) {
            /*if statement accounts for IV's IX's XL's and others
            done by seeing if first there is something next and if that next is bigger
            which then it will subtract value from the number*/
            arabnum -= value;
        } else {
            // if next is smaller you just add whatever 'value' is to the number
            arabnum += value;
        }
    }
    return arabnum;
}

//*************arabic to roman*************
string intToRoman(int num) {
    string result;
    // vector pairs for the different numerals and correlating number
    vector<pair<int, string>> numToRoman = {{1000, "M"}, {900, "CM"}, {500, "D"}, {400, "CD"}, {100, "C"}, {90, "XC"}, {50, "L"}, {40, "XL"}, {10, "X"}, {9, "IX"}, {5, "V"}, {4, "IV"}, {1, "I"}};

    /*auto would automatically put the pair together, with arabicnumeral representing the number, which is first, and
     romannumeral representing the roman numeral, which is second*/
    for (auto &pair : numToRoman) {
        int arabicnumeral = pair.first;
        string romannumeral = pair.second;
        /*while the number you are trying to convert is greater than or equal to an arabic numeral associated to
         a certain roman numeral, subtract the number by that number associated to the roman numeral, and on the
         string needed for return, add the letters associated to the number you subtracted the integrt 'num' by
         */
        while (num >= arabicnumeral) {
            result += romannumeral;
            num -= arabicnumeral;
        }
    }
    return result;
}


int main(){
/* ################  DO NOT MODIFY CODE IN THIS BLOCK ###################### */
   string temp;  //variable for database filename
   string batch; //variable for batch filename

   cout<<"Enter File Name: ";
   cin>>temp;

   ifstream infile(temp, ios::binary);
   ofstream outfile(database, ios::binary);
   string line;
   if (infile)
        while (infile.good())
        {
            getline(infile, line);
            if (line != "")
                outfile << line << "\n";
        }

   infile.close();
   outfile.close();
/* ################  DO NOT MODIFY CODE IN THIS BLOCK ###################### */
   // opening and checking if file is running properly
    string fileName;
    // getting/opening the file
    //cout << "Enter the name of the file: ";
    //cin >> fileName;
    fileName = "numbers.txt";
    fstream file;

    // opens the file
    file.open(fileName, ios::in | ios::out | ios::binary);

    // checks if the file is open successfully
    if (!file.is_open()) {
        cout << "Error opening the file. Check if the file name is correct." << endl;
        return 1;
    }

    string buffer;
    while (getline(file, buffer)) {
        if (buffer[0] == ' ') {
            // converts the Arabic number to Roman numeral
            string r = intToRoman(stoi(buffer.substr(17, 4)));

            // seeks the file position to where the Arabic number is located
            file.seekp(-22l, ios::cur);

            // writes the Roman numeral to the file
            file << r;

            // seeks the file position to where the next data is located
            file.seekp(22l - (int long)r.length(), ios::cur);
        } else {
            // converts the Roman numeral to Arabic number
            string r = to_string(romanToInt(buffer));

            // seeks the file position to where the Roman numeral is located
            file.seekp(-5l, ios::cur);

            // writes the Arabic number to the file
            file << r;

            // seeks the file position to where the next data is located
            file.seekp(5l - (int long)(4l - r.length()), ios::cur);
        }
    }

    // closes the file
    file.close();

    return 0;
}
