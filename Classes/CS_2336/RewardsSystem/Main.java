// package RewardsSystem;
//pratyush niraula
//pxn210022
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main{

    //process order will process the individual order read from a line in the order file
    public static Customer[] processOrder(String line, Customer []customerArray){
        //variable initialization for cup dimensions, and volume
        double diameter = 0;
        double height = 0;
        int oz = 0;
        double perOz = 0;
        
        //check if the line has the correct number of arguments
        String[] spaces = line.split(" ");
        if(spaces.length < 4 || spaces.length > 5){
            return customerArray;
        }

        //check if the id is valid
        int idFinder = line.indexOf(" ");
        if(idFinder == -1 || idFinder == 0 || idFinder == line.length() - 1){
            return customerArray;
        }
        String id = line.substring(0, idFinder);
        //invalid id finder will be below when it searches for it

        //check if the size is valid
        String rest = line.substring(idFinder + 1);
        int sizeFinder = rest.indexOf(" ");
        if(sizeFinder == -1 || sizeFinder == 0 || sizeFinder == line.length() - 1){
            return customerArray;
        }
        String size = rest.substring(0, sizeFinder);
        if(!size.equals("S") && !size.equals("M") && !size.equals("L")){
            return customerArray;
        }
        
        //check if the drink type is valid
        rest = rest.substring(sizeFinder + 1);
        int drinkTypeFinder = rest.indexOf(" ");
        if(drinkTypeFinder == -1 || drinkTypeFinder == 0 || drinkTypeFinder == line.length() - 1){
            return customerArray;
        }
        String drinkType = rest.substring(0, drinkTypeFinder);
        //check if the drink type is valid
        if(!drinkType.equals("punch") && !drinkType.equals("tea") && !drinkType.equals("soda")){
            //invalid drink type
            System.out.println("invalid drink type");
            return customerArray;
        }
        
        //check if the square inch is a number if not then return
        rest = rest.substring(drinkTypeFinder + 1);
        int sIfinder = rest.indexOf(" ");
        if(sIfinder == -1 || sIfinder == 0 || sIfinder == line.length() - 1){
            return customerArray;
        }
        String squareInch = rest.substring(0, sIfinder);
        //check if the square inch is a number if not then return
        try{
            double squareInchDouble = Double.parseDouble(squareInch);
            if(squareInchDouble < 0){
                //invalid square inch
                System.out.println("invalid square inch");
                return customerArray;
            }
        }catch(NumberFormatException e){
            System.out.println("invalid square inch");
            return customerArray;
        }
        
        //check if the quantity is a number if not then return
        rest = rest.substring(sIfinder + 1);
        String quantity = rest.substring(0);
        if(quantity.length() == 0){
            return customerArray;
        }
        int quantityInt = Integer.parseInt(quantity);
        if(quantityInt <= 0){
            return customerArray;
        }


        //find out dimensions of the cup
        if (size.equals("S")){
            diameter = 4;
            height = 4.5;
            oz = 12;
        }
        else if (size.equals("M")){
            diameter = 4.5;
            height = 5.75;
            oz = 20;
        }
        else if (size.equals("L")){
            diameter = 5.5;
            height = 7;
            oz = 32;
        }

        //find out the price per ounce
        if (drinkType.equals("punch")){
            perOz = 0.15;
        }
        else if (drinkType.equals("tea")){
            perOz = 0.12;
        }
        else if (drinkType.equals("soda")){
            perOz = 0.20;
        }

        //calculate price of the cup if there is square inch price for custom design
        double price = 0;
        if (!(squareInch.equals("0"))){
            price = (Math.PI * diameter * height) * Double.parseDouble(squareInch) * Double.parseDouble(quantity) + oz * perOz * Double.parseDouble(quantity);
        }
        else{
           //if no design just do normal calculation
            price = oz * perOz * Double.parseDouble(quantity);
        }

        //find the customer in the array and add the order to their total
        for (int i = 0; i < customerArray.length; i++) {
            if(customerArray[i].getGuestID().equals(id)){
                if(customerArray[i] instanceof Gold){
                    //apply discount
                    price = price - price*checkDiscount(id, customerArray);
                    customerArray[i].setMoneySpent(customerArray[i].getMoneySpent() + price);
                }
                else if(customerArray[i] instanceof Platinum){
                    //apply discount
                    Platinum platinum = (Platinum)customerArray[i];
                    double usedBonusBucks = checkDiscount(id, customerArray);
                    price = price - usedBonusBucks;
                    //subtract used bonusbucks from total bonusbucks
                    platinum.setBonusBucks((int)(platinum.getDiscount() - (int)usedBonusBucks));

                    //add new bonus bucks
                    int newBonusBucks = (int)(platinum.getDiscount() + (price + ((platinum.getMoneySpent())%5))/5);
                    platinum.setBonusBucks(newBonusBucks);
                    //add money spent
                    customerArray[i].setMoneySpent(customerArray[i].getMoneySpent() + price);
                    //add platinum to the array
                    customerArray[i] = platinum;
                    
                }
                else{
                    //apply discount
                    //price = price
                    customerArray[i].setMoneySpent(customerArray[i].getMoneySpent() + price);
                    
                }
            }
        }
        //return new array
        return customerArray;
    }
    
    public static boolean checkUpgradeEligibility(String id, Customer []customerArray, Customer []prefCustomerArray){
        //check if customer is eligible for upgrade
        for(int i = 0; i < customerArray.length; i++){
            if(customerArray[i].getGuestID().equals(id)){
                if(customerArray[i].getMoneySpent() >= 50){
                    //chance to upgrade to preferred customer
                    return true;
                }
            }
        }
        //do not upgrade to preferred customer
        return false;
    }

    public static int prefUpgradeEligibility(Customer []prefCustomerArray, String id){
        if(prefCustomerArray == null){
           //if null just return
            return 0;
        }
        //if integer is 0 then then customer is not a preferred customer eligible to upgrade
        //if integer returns 1 then customer is eligible to move to next tier of gold
        //if integer returns 2 then customer is eligible to move to platinum
        //if integer returns 3 then customer is already platinum
        for (int i = 0; i < prefCustomerArray.length; i++) {
            //get id of customer
            if(prefCustomerArray[i].getGuestID().equals(id)){
                
                //see if it is a gold or platinum customer
                if(prefCustomerArray[i] instanceof Gold){
                    //check to see the magnitude of their discount with how much they have spent
                    //for simplicity and logic following reasons variable prefUpdate is used instead of the actual object
                    Gold prefUpdate = (Gold)prefCustomerArray[i];
                    if (prefUpdate.getDiscount() == 15 && prefUpdate.getMoneySpent() >= 200){
                        //upgrade to platinum if possible
                        return 2;
                    }
                    //check to see if they can have a discount increase
                    else if (prefUpdate.getDiscount() == 10 && prefUpdate.getMoneySpent() >= 150){
                        return 1;
                    }
                    //check to see if they can have a discount increase
                    else if (prefUpdate.getDiscount() == 5 && prefUpdate.getMoneySpent() >= 100){
                        return 1;
                    }
                    
                }
                //already platinum
                else if(prefCustomerArray[i] instanceof Platinum){
                    return 3;
                }

            }
        }
        //they are not eligible to upgrade
        return 0;
    }

    public static Customer[] custUpgrade(String id, Customer[] custArray, Customer[] prefCustArray){
        //find customer in array
        for (int i = 0; i < custArray.length; i++) {
            if(custArray[i].getGuestID().equals(id)){
                //upgrade to preferred customer
                //check if they are gold or platinum worthy
                String discount = "0%";
                //add discount if it pertains to them
                if (custArray[i].getMoneySpent() >= 50){
                    discount = "5%";
                    if (custArray[i].getMoneySpent() >= 100){
                        discount = "10%";
                    }
                    if (custArray[i].getMoneySpent() >= 150){
                        discount = "15%";
                    }
                    //create new preferred customer object and call expand array preferred method
                    Customer temp = custArray[i];
                    String line = temp.getGuestID() + " " + temp.getFirstName() + " " + temp.getLastName() + " " + temp.getMoneySpent() + " " + discount;
                    prefCustArray = expandArrayPreffered(line, prefCustArray);//might have fixed the problem here
                    
                }
            }
        }
        //return new updated array
        return prefCustArray;
    }
    
    public static Customer[] prefUpgrade(String id, Customer[] prefCustArray){
        //check if customer is gold or platinum
        for (int i = 0; i < prefCustArray.length; i++) {
            if(prefCustArray[i].getGuestID().equals(id)){
                
                //see if it is a gold or platinum customer
                if(prefCustArray[i] instanceof Gold){
                    //check to see the magnitude of their discount with how much they have spent
                    //for simplicity and logic following reasons variable prefUpdate is used instead of the actual object
                    Gold prefUpdate = (Gold)prefCustArray[i];
                    if (prefUpdate.getMoneySpent() >= 200){
                        //upgrade to platinum
                        //get bonus bucks 
                        int bonusBucks = (int)((prefUpdate.getMoneySpent()-200)/5);
                        Platinum platinum = new Platinum(prefUpdate.getFirstName(), prefUpdate.getLastName(), prefUpdate.getGuestID(), prefUpdate.getMoneySpent(), bonusBucks);
                        prefCustArray[i] = platinum;
                    }
                    //check to see the magnitude of their discount with how much they have spent
                    else if (prefUpdate.getDiscount() == 10 && prefUpdate.getMoneySpent() >= 150){
                        prefUpdate.setDiscountPercent(prefUpdate.getDiscount() + 5);
                    }
                    else if (prefUpdate.getDiscount() == 5 && prefUpdate.getMoneySpent() >= 100){
                        prefUpdate.setDiscountPercent(prefUpdate.getDiscount() + 5);
                    }
                }
                else if(prefCustArray[i] instanceof Platinum){
                    //do nothing
                }

            }
        }
         //return updated array
        return prefCustArray;
    }

    public static double checkDiscount(String id, Customer []prefCustomerArray){
        //check if customer is gold or platinum and return discount
        for(int i = 0; i < prefCustomerArray.length; i++){
            if(prefCustomerArray[i].getGuestID().equals(id)){
                if(prefCustomerArray[i] instanceof Gold){
                    //apply discount
                    return (Double)(prefCustomerArray[i].getDiscount()) * 0.01;
                }
                else if(prefCustomerArray[i] instanceof Platinum){
                    //apply discount
                    return (Double)(prefCustomerArray[i].getDiscount());
                }
                
            }
        }
         //there is no discount
        return 0;
    }

    //regular customer array creation
        public static Customer[] expandArray(String line, Customer []customerArray){
        //get the id
        int idFinder = line.indexOf(" ");
        String id = line.substring(0, idFinder);

         
        //get first name
        String rest = line.substring(idFinder + 1);
        int firstFinder = rest.indexOf(" ");
        String first = rest.substring(0, firstFinder);

        //get last name
        rest = rest.substring(firstFinder + 1);
        int lastFinder = rest.indexOf(" ");
        String last = rest.substring(0, lastFinder);

         //get money spent
        rest = rest.substring(lastFinder + 1);
        String spent = rest.substring(0);

        //create new object
        Customer customer = new Customer(first, last, id, Double.parseDouble(spent));

        //reinitialize array with new object
        if (customerArray.length == 0 || customerArray == null){
            Customer[] customerArray2 = new Customer[1];
            customerArray2[0] = customer;
            customerArray = customerArray2;
        }
        else{
            Customer []customerArray2 = new Customer[customerArray.length + 1];
            for (int i = 0; i < customerArray.length; i++) {
                customerArray2[i] = customerArray[i];
            }
            customerArray = customerArray2;

            customerArray[customerArray.length - 1] = customer;
        }
        return customerArray;
    }

    //preferred customer array creation
    public static Customer[] expandArrayPreffered(String line, Customer []prefCustomerArray){
        //read in all of the values starting from id
        int idFinder = line.indexOf(" ");
        String id = line.substring(0, idFinder);
        //read in first name
        String rest = line.substring(idFinder + 1);
        int firstFinder = rest.indexOf(" ");
        String first = rest.substring(0, firstFinder);
        //read in last name
        rest = rest.substring(firstFinder + 1);
        int lastFinder = rest.indexOf(" ");
        String last = rest.substring(0, lastFinder);
        //read in money spent
        rest = rest.substring(lastFinder + 1);
        int spentFinder = rest.indexOf(" ");
        String spent = rest.substring(0, spentFinder);

        //read in discount or bonus bucks
        rest = rest.substring(spentFinder + 1);
        String bothDiscount = rest.substring(0);

        //convert money spent to double
        double moneySpent = Double.parseDouble(spent);
        int bonusBucks = -1;
        int discountPercent = 0;


        //check if it is a number or a percentage
        if(bothDiscount.contains("%")){
            bothDiscount = bothDiscount.substring(0, bothDiscount.indexOf("%"));
            discountPercent = Integer.parseInt(bothDiscount);
        }
        else{
            //else it is bonus bucks
            bothDiscount = bothDiscount.substring(0);
            bonusBucks = Integer.parseInt(bothDiscount);
        }
        //System.out.println("bothDiscount: " + bothDiscount);

        //create the preferred customer object
        Customer prefCustomer;
        if(bonusBucks == -1){
            prefCustomer = new Gold(first, last, id, moneySpent, discountPercent);
        }
        else{
            prefCustomer = new Platinum(first, last, id, moneySpent, bonusBucks);
        }

        if(prefCustomerArray.length == 0){
           //increase array size
            Customer []prefCustomerArray2 = new Customer[1];
            prefCustomerArray2[0] = prefCustomer;
            prefCustomerArray = prefCustomerArray2;
        }
        else{
            Customer []prefCustomerArray2 = new Customer[prefCustomerArray.length + 1];
            for(int i = 0; i < prefCustomerArray.length; i++){
                prefCustomerArray2[i] = prefCustomerArray[i];
            }
            //update array
            prefCustomerArray = prefCustomerArray2;
            prefCustomerArray[prefCustomerArray.length - 1] = prefCustomer;
        }
        //return to replace old array
        return prefCustomerArray;
    }

    public static Customer[] arrayDemolition(String id, Customer []customerArray){
        //find the index of the customer
        for(int i = 0; i < customerArray.length; i++){
            if(customerArray[i].getGuestID().equals(id)){
                //destroy the object
                customerArray[i] = null;
            }
        }
        //create a new array and copy everything except the null object
        Customer []temp = new Customer[customerArray.length - 1];
        int k = 0;
        for (int j = 0; j < customerArray.length; j++) {
            if (customerArray[j] != null){
                temp[k] = customerArray[j];
                if(k < temp.length - 1){k++;}    //error prone watch out for this
            }
        }
        //return the original array
        customerArray = temp;
        return customerArray;
    }

    public static void main(String[] args) throws FileNotFoundException{
        Scanner s = new Scanner(System.in);
        //customer array
        System.out.println("Enter the name of the regular customer file: ");
        String fileName = s.nextLine();
        File file = new File(fileName);
        //initialize scanners
        Scanner fileInput = new Scanner(file);
        Customer []customerArray = new Customer[0];

        //for final implementation put this inside a try catch when opening corresponding file as it can not have a file
        //Customer []prefCustomerArray = new Customer[0];
        Customer []prefCustomerArray = null;
        //read file and create regular customer array
        while(fileInput.hasNextLine()){
            String line = fileInput.nextLine();
            customerArray = expandArray(line, customerArray);
        }
        fileInput.close();

        //file null so that if there is no input for preferred customer file then it will not go back to previous value at all
        file = null;

        //read in customer file
        System.out.println("Enter the name of the preferred customer file: ");
        fileName = s.nextLine();
        file = new File(fileName);

        try {
            //trycatch to see if file exists and handle accordingly
            if (file.exists()) {
                fileInput = new Scanner(file);
                prefCustomerArray = new Customer[0];

                // read file and create preferred customer array
                while (fileInput.hasNextLine()) {
                    String line = fileInput.nextLine();
                    prefCustomerArray = expandArrayPreffered(line, prefCustomerArray);
                }

                fileInput.close();
            } else {
                System.out.println("File does not exist or cannot be opened.");
                prefCustomerArray = null;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error opening file: " + e.getMessage());
            prefCustomerArray = null;
        }
        fileInput.close();

        //start the order processing
        System.out.println("Enter the name of the order file: ");
        fileName = s.nextLine();
        file = new File(fileName);
        fileInput = new Scanner(file);

        while(fileInput.hasNextLine()){
            String line = fileInput.nextLine();
            //find the id of the customer and check if they are a preferred customer or regular customer
            String identification = line.substring(0, line.indexOf(" "));
            boolean alreadyPreferred = false;

            //processOrder will process the order and add the money spent to the customer's total
            for(int i = 0; i < customerArray.length; i++){
                //avoid null pointer exception
                if(customerArray[i].getGuestID().equals(identification)){
                    customerArray = processOrder(line, customerArray);
                }
            }
            //process order if preferred customer is already in the array
            if(prefCustomerArray != null){//this may or may not work
                for(int i = 0; i < prefCustomerArray.length; i++){
                    if(prefCustomerArray[i].getGuestID().equals(identification)){
                        prefCustomerArray = processOrder(line, prefCustomerArray);
                        alreadyPreferred = true;
                    }
                }
            }

            //find the id of the customer and check if they are eligible for upgrade
            //keep in mind 
            //if integer is 0 then then customer is not a preferred customer eligible to upgrade
            //if integer returns 1 then customer is eligible to move to next tier of gold
            //if integer returns 2 then customer is eligible to move to platinum
            //if integer returns 3 then customer is already platinum
            if(checkUpgradeEligibility(identification, customerArray, prefCustomerArray) && alreadyPreferred == false){
                //upgrade(identification, prefCustomerArray, customerArray);
                if(prefCustomerArray == null){
                    prefCustomerArray = new Customer[0];
                }

                prefCustomerArray = custUpgrade(identification, customerArray, prefCustomerArray);
                //downsize customer array
                customerArray = arrayDemolition(identification, customerArray);
            }
            //see if they are upgradeable
            else if(prefUpgradeEligibility(prefCustomerArray, identification) == 1 && alreadyPreferred == true){
                prefCustomerArray = prefUpgrade(identification, prefCustomerArray);
            }
            else if(prefUpgradeEligibility(prefCustomerArray, identification) == 2 && alreadyPreferred == true){
                prefCustomerArray = prefUpgrade(identification, prefCustomerArray);
                
            }
        }
        fileInput.close();

        //write to output file        
        PrintWriter fileOutput = new PrintWriter("customer.dat");
        //print to file
        for(int i = 0; i < customerArray.length; i++){
            Customer cust = customerArray[i];
            fileOutput.print(cust.getGuestID() + " " + cust.getFirstName() + " " + cust.getLastName() + " ");
            //print to file the money spent up to second decimal point
            fileOutput.printf("%.2f", cust.getMoneySpent());
            fileOutput.print("\n");
        }


        PrintWriter fileOutput2 = new PrintWriter("preferred.dat");
        //print to file
        for(int i = 0; i < prefCustomerArray.length; i++){
            Customer cust = prefCustomerArray[i];
            fileOutput2.print(cust.getGuestID() + " " + cust.getFirstName() + " " + cust.getLastName() + " ");
            //print to file the money spent up to second decimal point
            fileOutput2.printf("%.2f", cust.getMoneySpent());
            //check if customer is gold or platinum and handle discount types
            if(cust instanceof Gold){
                Gold gold = (Gold)cust;
                int discount = (int)gold.getDiscount();
                fileOutput2.print(" " + discount + "%\n");
            }
            else if(cust instanceof Platinum){
                Platinum platinum = (Platinum)cust;
                int discount = (int)platinum.getDiscount();
                fileOutput2.print(" " + discount + "\n");
            }
        }
        //close everything
        fileOutput.close();
        fileOutput2.close();
        s.close();
    }
}