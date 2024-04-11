/*
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <stdbool.h>

typedef struct character{
    char value;
    int occurance;
}character;




//find the most occured character in a string
int main(int argc, char const *argv[])
{

    char* line = NULL;
    size_t size = 0;
    character *arr;
    arr = (character*)malloc(100*sizeof(character));

    printf("print string. see what happens: \n");
    getline(&line, &size, stdin);
    bool flag;
    int numOfInserted = 0;

    for(int i = 0; i < size; i++){
        flag = false;
        for(int j = 0; j < numOfInserted; j++){
            if( line[i] == arr[j].value){
                    arr[j].occurance += 1;
                    flag = true;
                    break;
            }


        }
        if(!flag){
            character c;
            c.occurance = 1;
            c.value = line[i];
            arr[numOfInserted] = c;
            numOfInserted++;

        }
    }

    int maxOccur = 0;
    char let;
    for(int i = 0; i < numOfInserted; i++){
        if(arr[i].occurance > maxOccur){
            let = arr[i].value;
            maxOccur = arr[i].occurance;
        }

    }
    printf("\nthis is the most occured character %c\n", let);
    printf("\noccuring this many times %d", maxOccur);

    free(arr);
    free(line);
    return 0;
}
*/

#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <stdbool.h>

typedef struct character{
    char value;
    int occurance;
} character;

// Find the most occurred character in a string
int main(int argc, char const *argv[])
{
    char* line = NULL;
    size_t size = 0;
    ssize_t length;
    character *arr;
    arr = (character*)malloc(100 * sizeof(character));
    memset(arr, 0, 100 * sizeof(character)); // Initialize array

    printf("Enter string. See what happens: \n");
    length = getline(&line, &size, stdin); // Get line and its length
    bool flag;
    int numOfInserted = 0;

    for(int i = 0; i < strlen(line); i++){ // Use strlen(line) instead of size
        if(line[i] == '\n') continue; // Skip newline character if present
        flag = false;
        for(int j = 0; j < numOfInserted; j++){
            if(line[i] == arr[j].value){
                arr[j].occurance += 1;
                flag = true;
                break;
            }
        }
        if(!flag){
            character c;
            c.occurance = 1;
            c.value = line[i];
            arr[numOfInserted++] = c; // Increment numOfInserted here
        }
    }

    int maxOccur = 0;
    char let = 0; // Initialize let
    for(int i = 0; i < numOfInserted; i++){
        if(arr[i].occurance > maxOccur){
            let = arr[i].value;
            maxOccur = arr[i].occurance;
        }
    }
    printf("\nThe most occurred character: %c\n", let);
    printf("Occurring this many times: %d\n", maxOccur);

    free(arr);
    free(line);
    return 0;
}
