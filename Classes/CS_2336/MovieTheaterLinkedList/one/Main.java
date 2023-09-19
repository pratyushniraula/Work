package one;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.*;
import java.lang.String;

public class Main{
    Auditorium auditorium = new Auditorium();
    
    public static void main(String[] args){
        System.out.println("Enter the name of the file: ");
        Scanner s = new Scanner(System.in);
        String fileName = s.nextLine();
        File file = new File(fileName);
        Main t = new Main();
        //open file
        //read file****************************************************
        //t.auditorium.printAuditorium();
        //get the file and store file in linked list
        //see if file can open or not, if it can continue with the program, if not, say it cannot be found
        try{
            //read file
            Scanner fileScanner = new Scanner(file);
            //get the auditorium
            Node node = t.auditorium.head;

            while (fileScanner.hasNext()){
                Node tempHead = node;
                String line = fileScanner.nextLine();
                //split the line into characters
                for(int i = 0; i < line.length(); i++){
                    //read in the file and store it in the linked list
                    //if the character is a '.', the seat is open
                    //otherwise if the seat is an A or S or C the seat is filled
                    if (line.charAt(i) == '.' || line.charAt(i) == 'A' || line.charAt(i) == 'S' || line.charAt(i) == 'C'){
                        node.getSeat().setTicketType(line.charAt(i));
                        node = node.getNext();
                    }
                    
                }
                node = tempHead;
                node = node.getDown();
            }
            //close scanner
            fileScanner.close();

        }
        catch(FileNotFoundException e){
            //if file cannot be found, print this
            System.out.println("File not found");
        }
        t.auditorium.printAuditorium();
        s.close();
    }

}