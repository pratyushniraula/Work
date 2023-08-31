#include <iostream>
using namespace std;

#include "ShoppingCart.h"

string ShoppingCart::GetCustomerName() {
    
    //get the customer name
    return this->customerName;
   
}

string ShoppingCart::GetDate() {
   
    //get the date
    return this->currentDate;
}

void ShoppingCart::AddItem(ItemToPurchase item) {
   
    //add item to cart
    this->cartItems.push_back(item);
}

void ShoppingCart::RemoveItem(ItemToPurchase item) {
    
    //remove item from cart
    for(int i = 0; i < cartItems.size(); i++){
        if(cartItems.at(i).GetName() == item.GetName()){
            cartItems.erase(cartItems.begin() + i);
        }
    }
}

void ShoppingCart::ModifyItem(ItemToPurchase item) {
    
    //modify item in cart
    for(int i = 0; i < cartItems.size(); i++){
        if(cartItems.at(i).GetName() == item.GetName()){
            cartItems.at(i).SetQuantity(item.GetQuantity());
        }
    }
}

int ShoppingCart::GetNumItemsInCart() {
   
    //get the number of items in cart
    int total = 0;
    for(int i = 0; i < cartItems.size(); i++){
        total += cartItems.at(i).GetQuantity();
    }
    return total;
}

int ShoppingCart::GetCostOfCart() {
    
    //get the cost of the cart
    int total = 0;
    for(int i = 0; i < cartItems.size(); i++){
        total += (cartItems.at(i).GetPrice() * cartItems.at(i).GetQuantity());
    }
    return total;
}

void ShoppingCart::PrintTotal() {
    
    //print the total cost of the cart
    cout << this->customerName << "'s Shopping Cart - " << this->currentDate << endl;
    cout << "Number of Items: " << GetNumItemsInCart() << endl << endl;
    if(cartItems.size() == 0){
        cout << "SHOPPING CART IS EMPTY";
    }
    else{
        for(int i = 0; i < cartItems.size(); i++){
            cartItems.at(i).PrintItemCost();
        }
    }
    cout << endl << "Total: $" << GetCostOfCart() << endl;
}

void ShoppingCart::PrintDescription() {
    
    //print the description of the cart
    cout << this->customerName << "'s Shopping Cart - " << this->currentDate << endl << endl;
    cout << "Item Descriptions" << endl;
    for(int i = 0; i < cartItems.size(); i++){
        cartItems.at(i).PrintItemDescription();
    }
}

