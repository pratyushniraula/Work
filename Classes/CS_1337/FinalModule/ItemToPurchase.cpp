#include <iostream>
using namespace std;

#include "ItemToPurchase.h"

/* Type your code here */

ItemToPurchase::ItemToPurchase() {
    this->ItemName = "none";
    this->ItemPrice = 0;
    this->ItemQuantity = 0;
}

ItemToPurchase::ItemToPurchase(string name, string description, int price, int quantity) {
    this->ItemName = name;
    this->ItemDescription = description;
    this->ItemPrice = price;
    this->ItemQuantity = quantity;
}

void ItemToPurchase::SetName(string name){
    this->ItemName = name;
}

string ItemToPurchase::GetName(){
    return this->ItemName;
}

void ItemToPurchase::SetPrice(int price){
    this->ItemPrice = price;
}

int ItemToPurchase::GetPrice(){
    return this->ItemPrice;
}

void ItemToPurchase::SetQuantity(int quantity){
    this->ItemQuantity = quantity;
}

int ItemToPurchase::GetQuantity(){
    return this->ItemQuantity;
}

void ItemToPurchase::SetDescription(string description){
    this->ItemDescription = description;
}

string ItemToPurchase::GetDescription(){
    return this->ItemDescription;
}

void ItemToPurchase::PrintItemCost(){
    cout << this->ItemName << " " << this->ItemQuantity << " @ $" << this->ItemPrice << " = $" << this->ItemPrice * this->ItemQuantity << endl;
}

void ItemToPurchase::PrintItemDescription(){
    cout << this->ItemName << ": " << this->ItemDescription << endl;
}
