#include "stdlib.h"
#include "string.h"
#include "stdio.h"
#include "stdbool.h"

int main(int argc, char const *argv[])
{
    /*switched to nvim so indents look weird
     *this is a practice project to keep my C skills intact */
	FILE *encryption;
	encryption = fopen("EncryptedMessage.txt", "r");
	bool exitFlag = false;//this will say whether to exit program or not

	char letters[26] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}; //to look for elements subtract the character by 97 to find element in array
	char encryptedLetters[26];

	if(encryption == NULL){
		puts("Error in opening encryption key, please try again or check if file is in directory.\n");
		return EXIT_FAILURE;
	}

	else{
		puts("Encryption key opened successfully!\n reading file\n");
	}
	while(exitFlag != true){
	

		int choice = 0; //choice will determine if user wants do encrypt or decrypt in this situation
		//this part is reading in the encryption
/////////////////////////////////////////////////////////////////////
		char i[5];//first char is decrypted letter, second char is space, third char is encrypted letter, fourth char is \n and last char is null terminator
		int counter = 0;
		while(fgets(i, sizeof(i), encryption) != NULL){
			encryptedLetters[counter] = i[2];
			counter++;
		}

		fclose(encryption);
/////////////////////////////////////////////////////////////////////
	
		puts("Encrypt (1)\nDecrypt (2)\nChange Encryption (3)\nExit (4)");
		while(choice > 4 || choice < 1){
			scanf("%d",&choice);

			if(choice > 4 || choice < 1){
				puts("wait that's illegal, pick a number between one and two: \n");
			}
		}

		if(choice == 1){ //choice to encrypt something
			choice = 0;
			puts("encrypt a file's message or encrypt message on console? (1 or 2): ");
	
			while(choice > 2 || choice < 1){
				scanf("%d",&choice);

				if(choice > 2 || choice < 1){
					puts("wait that's illegal, pick a number between one and two: \n");
				}
			}

			if(choice == 1){}
		
		}
		else if(choice == 2){ //choice to decrypt something
	
			choice = 0;
			puts("decrypt a file's message or decrypt message on console? (1 or 2): ");

	
			while(choice > 2 || choice < 1){
				scanf("%d",&choice);

				if(choice > 2 || choice < 1){
					puts("wait that's illegal, pick a number between one and two: \n");
				}
			}

			if(choice == 1){//if trying to decrypt a file

			}
			else{//if trying to decrypt on console
				puts("enter the message you want to decrypt:\n");

			}
	
		}

		else if(choice == 3){
		//change encryption
		}

		else {
		//terminate program
			puts("program terminated!\n");
			return 0;
		}

	}
}
