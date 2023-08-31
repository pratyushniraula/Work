#include <iostream>
#include <iomanip>
using namespace std;

#include "ShoppingCart.h"

void PrintMenu() {
    /* Type your code here */
    //print the menu
    std::cout << "\nMENU" << endl;
    std::cout << "a - Add item to cart" << endl;
    std::cout << "d - Remove item from cart" << endl;
    std::cout << "c - Change item quantity" << endl;
    std::cout << "i - Output items' descriptions" << endl;
    std::cout << "o - Output shopping cart" << endl;
    std::cout << "q - Quit" << endl << endl;
    std::cout << "\nChoose an option:" << endl;
}

void ExecuteMenu(char option, ShoppingCart& theCart) {
   /* Type your code here */
   //declare variables
   string name;
   string description;
   int price;
   int quantity;
   ItemToPurchase item;
   bool flag = false;
   //switch statement
   switch(option){
       case 'a':
           //get the name
           std::cout << "ADD ITEM TO CART" << endl;
           std::cout << "Enter the item name:" << endl;
           std::cin.ignore();
           std::getline(std::cin, name);

           //get the description
           std::cout << "Enter the item description:" << endl;
           std::getline(std::cin, description);

           //get the price
           std::cout << "Enter the item price:" << endl;
           std::cin >> price;

           //get the quantity
           std::cout << "Enter the item quantity:" << endl;
           std::cin >> quantity;

           //create the item
           item.SetName(name);
           item.SetDescription(description);
           item.SetPrice(price);
           item.SetQuantity(quantity);

           //add the item to the cart
           theCart.AddItem(item);

           std::cout << endl;
           break;
       case 'd':
           //get the name
           std::cout << "REMOVE ITEM FROM CART" << endl;
           std::cout << "Enter name of item to remove:" << endl;
           std::cin.ignore();
           std::getline(std::cin, name);

           //create the item
           item.SetName(name);

           //remove the item from the cart
           theCart.RemoveItem(item);

           std::cout << endl;
           break;
       case 'c':
           //get the name
           std::cout << "CHANGE ITEM QUANTITY" << endl;
           std::cout << "Enter the item name:" << endl;
           std::cin.ignore();
           getline(std::cin, name);

           //get the quantity
           std::cout << "Enter the new quantity:" << endl;
           std::cin >> quantity;

           //create the item
           item.SetName(name);
           item.SetQuantity(quantity);

           //modify the item in the cart
           theCart.ModifyItem(item);

           cout << endl;
           break;
       case 'i':
           //print the description
           std::cout << "OUTPUT ITEMS' DESCRIPTIONS" << endl;
           theCart.PrintDescription();
           std::cout << endl;
           break;
       case 'o':
           //print the cart
           std::cout << "OUTPUT SHOPPING CART" << endl;
           theCart.PrintTotal();
          std::cout << endl;
           break;
       case 'q':
           break;
       default:
           cout << "Choose an option:";
           break;
   }

   
   
}

int main() {
   /* Type your code here */
   
   //declare variables
   string name;
   string date;
   char option;

   //get the name and date
   std::cout << "Enter customer's name:" << endl;
   std::getline(std::cin, name);
   std::cout << "Enter today's date:" << endl;
   std::getline(std::cin, date);
   std::cout << endl;

   std::cout << "Customer name: " << name << endl;
   std::cout << "Today's date: " << date << endl << endl;

   //create the cart
   ShoppingCart theCart(name, date);

   //print the menu
   PrintMenu();

   //get the option
   std::cin >> option;
   std::cout << endl;

   //while the option is not q
   while(option != 'q'){
       //execute the menu
       ExecuteMenu(option, theCart);

       //print the menu
      //cout << "\n\n";
      PrintMenu();

       //get the option
       cin >> option;
       //cout << endl;
   }

   return 0;
}