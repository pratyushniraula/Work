// package RewardsSystem;
//pratyush niraula
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
//this will hold original interpretation of Main.java

public class Main2{

    //so far main can create arrays to add individual customers to the preferred or the nonpreferred arrays
    //process orders, apply discounts, reading every line from input file and write output into file is still necessary
    //to check if there is no preferred customer file add a boolean value to do expandArrayPreffered only if there is a file, if flag is false then create an empty array
    //if upgraded to platinum then do initial check for bonusbucks but after that make sure to account for if they have already attained other bonus bucks
    //bonus bucks will be a hassle to fix right now
    //make sure to not print out anything from the input files


    public static void processOrder(String line, Customer []customerArray, Customer []prefCustomerArray){
        //variable initialization for cup dimensions, and volume
        double diameter = 0;
        double height = 0;
        int oz = 0;
        double perOz = 0;
        
        //check the +1 in the substring method for id
        int idFinder = line.indexOf(" ");
        String id = line.substring(0, idFinder+1);
        
        String rest = line.substring(idFinder + 1);
        int sizeFinder = rest.indexOf(" ");
        String size = rest.substring(0, sizeFinder);

        rest = rest.substring(sizeFinder + 1);
        int drinkTypeFinder = rest.indexOf(" ");
        String drinkType = rest.substring(0, drinkTypeFinder);

        rest = rest.substring(drinkTypeFinder + 1);
        int sIfinder = rest.indexOf(" ");
        String squareInch = rest.substring(0, sIfinder);
     
        rest = rest.substring(sIfinder + 1);
        //this too might break for the same reason the other one might
        // int quantityFinder = rest.indexOf("\n");
        // String quantity = rest.substring(0, quantityFinder);
        String quantity = rest.substring(0);

        //find out if it is existing customer or new customer
        // boolean existingCustomer = false;
        // boolean existingPrefCustomer = false;
        // for(int i = 0; i < customerArray.length; i++){
        //     if(customerArray[i].getGuestID().equals(id)){
        //         existingCustomer = true;
        //     }
        // }
        // for(int i = 0; i < prefCustomerArray.length; i++){
        //     if(prefCustomerArray[i].getGuestID().equals(id)){
        //         existingPrefCustomer = true;
        //     }
        // }

        boolean existingCustomer = false;
        boolean existingPrefCustomer = false;
    
        for (Customer customer : customerArray) {
            if (customer.getGuestID().equals(id)) {
                existingCustomer = true;
                break;
            }
        }
    
        for (Customer prefCustomer : prefCustomerArray) {
            if (prefCustomer.getGuestID().equals(id)) {
                existingPrefCustomer = true;
                break;
            }
        }
        //try to use the above for loop to check if the customer is in the array
        //if not throw away the entire line and read the next one
        if (existingCustomer == false && existingPrefCustomer == false){
            return;
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
            price = oz * perOz * Double.parseDouble(quantity);
        }


        if (existingCustomer == true){
            //find the customer in the array and add the order to their total
            for(int i = 0; i < customerArray.length; i++){
                if(customerArray[i].getGuestID().equals(id)){
                    customerArray[i].setMoneySpent(price);
                }
            }
        }
        else if(existingPrefCustomer == true){
            //find the customer in the array and add the order to their total
            //check to see if there are any applyable discounts to their price
            if(checkDiscount(id, prefCustomerArray) > 0){
                price = price - (price * checkDiscount(id, prefCustomerArray));
            }

            for(int i = 0; i < prefCustomerArray.length; i++){
                if(prefCustomerArray[i].getGuestID().equals(id)){
                    prefCustomerArray[i].setMoneySpent(price);
                    if(prefCustomerArray[i] instanceof Platinum){
                        Platinum platinum = (Platinum)prefCustomerArray[i];
                        if (platinum.getMoneySpent() >= 200){
                            //apply discount
                            int bonus = (int)(0.2*(platinum.getMoneySpent()-200));
                            platinum.setBonusBucks(bonus);
                        }
                    }
                }
            }
        }
        
    }

    public static boolean checkUpgradeEligibility(String id, Customer []customerArray, Customer []prefCustomerArray){
        //add here
        //this only works for regular to gold customers right now!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! fix this
        for(int i = 0; i < customerArray.length; i++){
            if(customerArray[i].getGuestID().equals(id)){
                if(customerArray[i].getMoneySpent() >= 50){
                    //upgrade(id, prefCustomerArray, customerArray);
                    return true;
                }
                // if(customerArray[i].getMoneySpent() >= 100){
                //     //upgrade to preferred customer
                //     expandArrayPreffered(customerArray[i].getFirstName() + " " + customerArray[i].getLastName() + " " + customerArray[i].getGuestID() + " " + customerArray[i].getMoneySpent() + " 0", prefCustomerArray);
                // }
                    
            }
        }
        return false;
    }

    public static int prefUpgradeEligibility(Customer []prefCustomerArray, String id){
        //if integer is 0 then then customer is not a preferred customer eligible to upgrade
        //if integer returns 1 then customer is eligible to move to next tier of gold
        //if integer returns 2 then customer is eligible to move to platinum
        //if integer returns 3 then customer is already platinum
        for (int i = 0; i < prefCustomerArray.length; i++) {
            if(prefCustomerArray[i].getGuestID().equals(id)){
                
                //see if it is a gold or platinum customer
                if(prefCustomerArray[i] instanceof Gold){
                    //check to see the magnitude of their discount with how much they have spent
                    //for simplicity and logic following reasons variable prefUpdate is used instead of the actual object
                    Gold prefUpdate = (Gold)prefCustomerArray[i];
                    if (prefUpdate.getDiscount() == 15 && prefUpdate.getMoneySpent() >= 200){
                        //upgrade to platinum
                        return 2;
                    }
                    else if (prefUpdate.getDiscount() == 10 && prefUpdate.getMoneySpent() >= 150){
                        return 1;
                    }
                    else if (prefUpdate.getDiscount() == 5 && prefUpdate.getMoneySpent() >= 100){
                        return 1;
                    }
                    
                }
                else if(prefCustomerArray[i] instanceof Platinum){
                    return 3;
                }

            }
        }
        return 0;
    }

    public static void upgrade(String id, Customer[] prefCustomerArray, Customer[] customerArray){
        
        //check if customer is in the regular customer array
        for (int i = 0; i < customerArray.length; i++) {
            if(customerArray[i].getGuestID().equals(id)){
                //upgrade to preferred customer
                //check if they are gold or platinum worthy

                if (customerArray[i].getMoneySpent() >= 50){
                    String line = customerArray[i].getGuestID() + "" + customerArray[i].getFirstName() + " " + customerArray[i].getLastName() + " " + customerArray[i].getMoneySpent() + " 0";
                    //expandArrayPreffered(customerArray[i].getGuestID() + "" + customerArray[i].getFirstName() + " " + customerArray[i].getLastName() +  " " + customerArray[i].getMoneySpent() + "5%\n", prefCustomerArray);
                    expandArrayPreffered(line, prefCustomerArray);
                    //resize array
                    Customer []temp = new Customer[customerArray.length - 1];
                    // for (int j = 0; j < temp.length; j++) {
                    //     if (customerArray[j] != null){
                    //         temp[j] = customerArray[j];
                    //     }
                    // }
                    int k = 0;
                    for (int j = 0; j < customerArray.length; j++) {
                        if (customerArray[j] != null){
                            temp[k] = customerArray[j];
                            if(k < temp.length - 1){k++;}    //error prone watch out for this
                        }
                    }
                }
            }
        }

        //check if customer is already in the preferred customer array method is not tested and should be debugged further
        for (int i = 0; i < prefCustomerArray.length; i++) {
            if(prefCustomerArray[i].getGuestID().equals(id)){
                
                //see if it is a gold or platinum customer
                if(prefCustomerArray[i] instanceof Gold){
                    //check to see the magnitude of their discount with how much they have spent
                    //for simplicity and logic following reasons variable prefUpdate is used instead of the actual object
                    Gold prefUpdate = (Gold)prefCustomerArray[i];
                    if (prefUpdate.getDiscount() == 15 && prefUpdate.getMoneySpent() >= 200){
                        //upgrade to platinum
                        Platinum platinum = new Platinum(prefUpdate.getFirstName(), prefUpdate.getLastName(), prefUpdate.getGuestID(), prefUpdate.getMoneySpent(), 0);
                        prefCustomerArray[i] = platinum;
                    }
                    else if (prefUpdate.getDiscount() == 10 && prefUpdate.getMoneySpent() >= 150){
                        prefUpdate.setDiscountPercent(prefUpdate.getDiscount() + 5);
                    }
                    else if (prefUpdate.getDiscount() == 5 && prefUpdate.getMoneySpent() >= 100){
                        prefUpdate.setDiscountPercent(prefUpdate.getDiscount() + 5);
                    }
                }

            }
        }

        
    }

    public static double checkDiscount(String id, Customer []prefCustomerArray){
        //add here
        //this only works for gold customers right now!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! fix this
        for(int i = 0; i < prefCustomerArray.length; i++){
            if(prefCustomerArray[i].getGuestID().equals(id)){
                if(prefCustomerArray[i] instanceof Gold){
                    //apply discount
                    return (Double)(prefCustomerArray[i].getDiscount()) * 0.01;
                }
                
            }
        }

        return 0;
    }

    //regular customer array creation
        public static Customer[] expandArray(String line, Customer []customerArray){
        //something wrong with .indexOf or .substring
        int idFinder = line.indexOf(" ");
       //String id = line.substring(0, idFinder);
        String id = line.substring(0, idFinder+1);
        System.out.println("" + id);

        String rest = line.substring(idFinder + 1);
        int firstFinder = rest.indexOf(" ");
        String first = rest.substring(0, firstFinder);
        //tester
        System.out.println(first);
        
        rest = rest.substring(firstFinder + 1);
        int lastFinder = rest.indexOf(" ");
        String last = rest.substring(0, lastFinder);
        //tester
        System.out.println(last);

        rest = rest.substring(lastFinder + 1);
        //might possibly break here as it could be a space or it could just be a newline
        //int spentFinder = rest.indexOf("\n");
        //String spent = rest.substring(0, spentFinder);
        String spent = rest.substring(0);
        //tester
        System.out.println(spent);

        //create new object
        Customer customer = new Customer(first, last, id, Double.parseDouble(spent));

        //reinitialize array with new object
        if (customerArray.length == 0){
            //System.out.println("checkpoint10");
            Customer[] customerArray2 = new Customer[1];
            customerArray2[0] = customer;
            customerArray = customerArray2;
            //System.out.println("checkpoint20");
        }
        else{
            Customer []customerArray2 = new Customer[customerArray.length + 1];
            for (int i = 0; i < customerArray.length; i++) {
                //System.out.println("checkpoint 70");
                customerArray2[i] = customerArray[i];
            }
            customerArray = customerArray2;

            customerArray[customerArray.length - 1] = customer;
            //System.out.println("checkpoint 80");
        }
        //System.out.println("checkpoint4");
        return customerArray;
    }

    //preferred customer array creation
    public static Customer[] expandArrayPreffered(String line, Customer []prefCustomerArray){

        int idFinder = line.indexOf(" ");
        String id = line.substring(0, idFinder+1);

        String rest = line.substring(idFinder + 1);
        int firstFinder = rest.indexOf(" ");
        String first = rest.substring(0, firstFinder);
        
        rest = rest.substring(firstFinder + 1);
        int lastFinder = rest.indexOf(" ");
        String last = rest.substring(0, lastFinder);
        
        rest = rest.substring(lastFinder + 1);
        int spentFinder = rest.indexOf(" ");
        String spent = rest.substring(0, spentFinder);

        
        rest = rest.substring(spentFinder + 1);
        //int discountFinder = rest.indexOf("\n");
        //String bothDiscount = rest.substring(0, discountFinder);
        String bothDiscount = rest.substring(0);

        
        double moneySpent = Double.parseDouble(spent);
        int bonusBucks = -1;
        int discountPercent = 0;

        //checkpoint in printing everything
        System.out.println("id: " + id);
        System.out.println("first: " + first);
        System.out.println("last: " + last);
        System.out.println("spent: " + spent);


        //check if it is a number or a percentage
        if(bothDiscount.contains("%")){
            bothDiscount = bothDiscount.substring(0, bothDiscount.indexOf("%"));
            discountPercent = Integer.parseInt(bothDiscount);
        }
        else{
            //bothDiscount = bothDiscount.substring(0, bothDiscount.indexOf("\n"));
            bothDiscount = bothDiscount.substring(0);
            bonusBucks = Integer.parseInt(bothDiscount);
        }
        System.out.println("bothDiscount: " + bothDiscount);

        //create the preferred customer object
        Customer prefCustomer;
        if(bonusBucks == -1){
            prefCustomer = new Gold(first, last, id, moneySpent, discountPercent);
        }
        else{
            prefCustomer = new Platinum(first, last, id, moneySpent, bonusBucks);
        }

        //reinitialize array with new object
        prefCustomerArray = new Customer[prefCustomerArray.length + 1];
        
        //subtract one for index and another for the space being made available for the new object
        for(int i = 0; i < prefCustomerArray.length - 2; i++){
            prefCustomerArray[i] = prefCustomerArray[i];
        }
        prefCustomerArray[prefCustomerArray.length - 1] = prefCustomer;

        return prefCustomerArray;
    }



    public static void main(String[] args) throws FileNotFoundException{
        Scanner s = new Scanner(System.in);
        //customer array
        System.out.println("Enter the name of the regular customer file: ");
        String fileName = s.nextLine();
        File file = new File(fileName);
        
        Scanner fileInput = new Scanner(file);
        Customer []customerArray = new Customer[0];
        //Customer []customerArray = null;

        //for final implementation put this inside a try catch when opening corresponding file as it can not have a file
        Customer []prefCustomerArray = new Customer[0];

        //read file and create regular customer array
        //there is a problem with expandArray method////////////////////////////////////////////////////
        while(fileInput.hasNextLine()){
            String line = fileInput.nextLine();
            customerArray = expandArray(line, customerArray);
        }
        fileInput.close();

        System.out.println("Enter the name of the preferred customer file: ");
        fileName = s.nextLine();
        file = new File(fileName);
        fileInput = new Scanner(file);
        
        //read file and create preferred customer array
        while(fileInput.hasNextLine()){
            String line = fileInput.nextLine();
            prefCustomerArray = expandArrayPreffered(line, prefCustomerArray);
        }
        fileInput.close();


        //test to see if the arrays are created properly
        try{
        System.out.println("Regular customer array: ");
        for(int i = 0; i < customerArray.length; i++){
            System.out.println(customerArray[i].getFirstName() + " " + customerArray[i].getLastName() + " " + customerArray[i].getGuestID() + " " + customerArray[i].getMoneySpent());
        }
        }catch(NullPointerException e){
            System.out.println("Regular customer array is empty");
        }
        System.out.println("Preferred customer array: ");
        for(int i = 0; i < prefCustomerArray.length; i++){
            System.out.println(prefCustomerArray[i].getFirstName() + " " + prefCustomerArray[i].getLastName() + " " + prefCustomerArray[i].getGuestID() + " " + prefCustomerArray[i].getMoneySpent());
        }


        //implement scenarios in which there is faulty line in the input file
        //implement scenarios in which there is no preferred customer file
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //task starts here
        //start the order processing
        System.out.println("Enter the name of the order file: ");
        fileName = s.nextLine();
        file = new File(fileName);
        fileInput = new Scanner(file);

        //possible ideas going about reading it promoting to preferred customer array and then checking if they are eligible for upgrade
        //current methods that happen because of reading order file: processOrder, checkUpgradeEligibility, upgrade, checkDiscount, and prefUpgradeEligibility
        //process order, checkupgradeeligibility, and prefupgradeeligibility are properly implemented so that they can be used in the main method
        //checkUpgradeEligibility has been refitted into a boolean method and prefUpgradeEligibility has been refitted into an multiple scenario method
        //upgrade and checkDiscount are not implemented yet into new change
        //further upgrades are required on upgrade, and checkDiscount to use if statements that condition if arrays need to be refitted
        //possible to implement a resizing method that can be used for both arrays so that there can be objects destroyed
        //possible to implement a method that can be used to check if the customer is in the array
        //process order needs to be refitted to return array that can be accounted for in main method or reorganize two seperate methods for regular and preferred customers

        while(fileInput.hasNextLine()){
            String line = fileInput.nextLine();
            processOrder(line, customerArray, prefCustomerArray);
            //find the id of the customer and check if they are eligible for upgrade
            String identification = line.substring(0, line.indexOf(" "));
            if(checkUpgradeEligibility(identification, customerArray, prefCustomerArray)){
                
            }
        }
        fileInput.close();
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //write to output file
        
        
        PrintWriter fileOutput = new PrintWriter("customer.dat");
        for(int i = 0; i < customerArray.length; i++){
            Customer cust = customerArray[i];
            fileOutput.print(cust.getGuestID() + " " + cust.getFirstName() + " " + cust.getLastName() + " ");
            //print to file the money spent up to second decimal point
            fileOutput.printf("%.2f", cust.getMoneySpent());
        }
        fileOutput.close();

        fileOutput = new PrintWriter("preferred.dat");
        for(int i = 0; i < prefCustomerArray.length; i++){
            Customer cust = prefCustomerArray[i];
            fileOutput.print(cust.getGuestID() + " " + cust.getFirstName() + " " + cust.getLastName() + " ");
            //print to file the money spent up to second decimal point
            fileOutput.printf("%.2f", cust.getMoneySpent());
            if(cust instanceof Gold){
                Gold gold = (Gold)cust;
                fileOutput.print(" " + gold.getDiscount() + "%\n");
            }
            else if(cust instanceof Platinum){
                Platinum platinum = (Platinum)cust;
                fileOutput.print(" " + platinum.getDiscount() + "\n");
            }
        }


        
        s.close();
    }
}