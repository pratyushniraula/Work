#ifndef ITEM_TO_PURCHASE_H
#define ITEM_TO_PURCHASE_H

#include <string>
using namespace std;

class ItemToPurchase{
   private:
      string ItemName;
      int ItemPrice;
      int ItemQuantity;
      
      string ItemDescription;
   
   public: 
      ItemToPurchase();
      ItemToPurchase(string name, string description, int price, int quantity);
      void SetName(string name);
      string GetName();
      void SetPrice(int price);
      int GetPrice();
      void SetQuantity(int quantity);
      int GetQuantity();
      
      void SetDescription(string description);
      string GetDescription();
      void PrintItemCost();
      
      void PrintItemDescription();
      
};

#endif