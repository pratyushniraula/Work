#ifndef Enc_Dec_H
#define Enc_Dec_H
#include <stdio.h>

char *arrResize(int arrSize, char* arr){
    if(arr == NULL || arr[0] == '\0' || arr[0] == ' '){ 
        arr = (char*)realloc(arr, arrSize * 2);
    
    }
    return arr;
}


char *encrypt(char *message, char *encryptedLetters, char letters[], char encrypted[]){
    int i = 0;
    while(message[i] != '\0'){
        int j = 0;
        while(letters[j] != '\0'){
            if(message[i] == letters[j]){
                message[i] = encrypted[j];
                break;
            }
            j++;
        }
        i++;
    }
    return encryptedLetters;

}
char *decrypt(char *message, char *encryptedLetters, char letters[], char encrypted[]){
    int i = 0;
    while(message[i] != '\0'){
        int j = 0;
        while(encrypted[j] != '\0'){
            if(message[i] == encrypted[j]){
                message[i] = letters[j];
                break;
            }
            j++;
        }
        i++;
    }
    return message;

}
void changeEncryption(char *encryptedLetters, FILE *encryption){

}

#endif
