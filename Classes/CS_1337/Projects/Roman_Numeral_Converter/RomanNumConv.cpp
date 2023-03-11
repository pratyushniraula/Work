#include <iostream>
#include<fstream>
#include <string>
using namespace std;

const string database = "numbers.txt";  
//your filestream for the database will connect to this variable

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

   //add your code for main here
}
