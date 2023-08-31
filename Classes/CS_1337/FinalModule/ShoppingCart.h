/* Type your code here */
#ifndef SHOPPING_CART_H
#define SHOPPING_CART_H

#include <string> 
#include <vector>

#include "ItemToPurchase.h"

using namespace std;

class ShoppingCart : public ItemToPurchase{
private: 
   string customerName;
   string currentDate;
   vector <ItemToPurchase> cartItems;
   
public:
   
   ShoppingCart() {
        customerName = "none";
        currentDate = "January 1, 2016";
    }
    
    ShoppingCart(string name, string date) {
        customerName = name;
        currentDate = date;
    }
    
    string GetCustomerName();
    string GetDate();
    void AddItem(ItemToPurchase item);
    void RemoveItem(ItemToPurchase item);
    void  ModifyItem(ItemToPurchase item);
    int GetNumItemsInCart();
    int GetCostOfCart();
    void PrintTotal();
    void PrintDescription();
    
   
};

#endif