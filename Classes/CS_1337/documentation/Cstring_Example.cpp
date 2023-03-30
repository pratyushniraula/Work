/*#include <iostream>
#include <string>
using namespace std;
*/

#include "stdio.h"
#include "string.h"

int main()
{
    int num_students;
    //string first, last, name;
    char first[10], last[10], name[10];

    //cout << "How many students are there? ";
    puts("How many students are there? ");

    //cin >> num_students;
    scanf("%d", &num_students);

    //cin >> name;
    scanf("%s", name);

    //first = last = name;
    strcpy(last, name);
    strcpy(first, last);

    for (int i = 2; i <= num_students; i++)
    {
	  scanf("%s", name);

        //if (name < first)
	  if (strcmp(name, first) < 0)
            //first = name;
		strcpy(first, name);

        //else if (name > last)
	  else if (strcmp(name, last) > 0)
            //last = name;
		strcpy(last, name);

    }
    
    //cout << "The first student is " << first << endl;
    //cout << "The last student is " << last << endl;
    printf("The first student is %s\nThe last student is %s\n", first, last);

    return 0;
}