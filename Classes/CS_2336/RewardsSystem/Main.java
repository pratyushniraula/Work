package RewardsSystem;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main{

    public void processing(String line){
        

    }


    public static void main(String[] args) throws FileNotFoundException{
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the name of the file: ");
        String fileName = input.nextLine();
        File file = new File(fileName);
        Scanner fileInput = new Scanner(file);
        PrintWriter fileOutput = new PrintWriter(fileName);
        String line = "";

        // String[] lineArray = new String[5];
        // int i = 0;


        // while(fileInput.hasNextLine()){
        //     line = fileInput.nextLine();
        //     lineArray = line.split(",");
        //     if(lineArray[0].equals("Customer")){
        //         Customer customer = new Customer(lineArray[1], lineArray[2], lineArray[3], Double.parseDouble(lineArray[4]));
        //         fileOutput.println(customer.toString());
        //     }
        //     else if(lineArray[0].equals("Gold")){
        //         Gold gold = new Gold(lineArray[1], lineArray[2], lineArray[3], Double.parseDouble(lineArray[4]), Double.parseDouble(lineArray[5]));
        //         fileOutput.println(gold.toString());
        //     }
        //     else if(lineArray[0].equals("Platinum")){
        //         Platinum platinum = new Platinum(lineArray[1], lineArray[2], lineArray[3], Double.parseDouble(lineArray[4]), Integer.parseInt(lineArray[5]));
        //         fileOutput.println(platinum.toString());
        //     }
        //     else{
        //         System.out.println("Invalid input");
        //     }
        // }
        fileInput.close();
        fileOutput.close();
    }
}