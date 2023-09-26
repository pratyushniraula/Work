//Pratyush Niraula
//Pxn210033
import java.io.File;
import java.io.FileNotFoundException;
// import java.util.*;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.*;
import java.lang.String;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        //variable initialization
        System.out.println("Enter the name of the file: ");
        Scanner s = new Scanner(System.in);
        String fileName = s.nextLine();
        File file = new File(fileName);
        Auditorium auditorium = null;
        char tempCol = 65;
        int tempRow = 0;
        int choice = 2;

        //open file
        try{
            //inside of the try catch fix the way the linked list is initialized
            //read file
            Scanner fileScanner = new Scanner(file);
            auditorium = new Auditorium();
            //get the auditorium
            //Node node = t.auditorium.head;
            tempRow = 0;
            Node RowHeadStore = auditorium.getHead();
            // create the auditorium and its inside body
            while (fileScanner.hasNext()){
                String line = fileScanner.nextLine();
                tempCol = 65;
                //storing variables inside the seat node from the Node ndoe
                for(int i = 0; i < line.length(); i++){
                    tempCol = (char)(65 + i);
                    if (line.charAt(i) == 'A' || line.charAt(i) == 'S' || line.charAt(i) == 'C' || line.charAt(i) == '.') {
                        
                        //uses overloaded constructor
                        if (RowHeadStore.getSeat().getTicketType() == 0) {
                            RowHeadStore.setSeat(new Seat(tempRow, tempCol, line.charAt(i)));
                        } 
                        
                        else {
                            //gets the next value
                            Node tempNode = RowHeadStore;
                            while (tempNode.getNext() != null) {
                                tempNode = tempNode.getNext();
                            }
                            //creates a new node
                            Node newNode = new Node(tempRow, tempCol, line.charAt(i));
                            tempNode.setNext(newNode);
                        }
                    }
                }
                //start the next row
                Node oneRowDown = new Node();
                RowHeadStore.setDown(oneRowDown);
                RowHeadStore = oneRowDown;
                tempRow++;
            }
            //close scanner
            fileScanner.close();
        }
        catch(FileNotFoundException noFile){
            //if file cannot be found, print this
            System.out.println("File not found");
        }
        
        do{
            //variable initialization
            //set to bogus values as a placeholder
            int adult = -1;
            int child = -1;
            int senior = -1;
            char userCol = 0;
            int userRow = -1;
            
            //new line
            System.out.println();
            
            //start of a long list of input validation and making sure variables exist
            
            //menu validation
            do{
                System.out.println("1. Reserve Seats");
                System.out.println("2. Exit");
                try{
                    choice = s.nextInt();
                    
                }
                catch(InputMismatchException e){
                    s.nextLine();
                    System.out.println("Invalid input");
                }

            }while (choice != 1 && choice != 2);
            
            //new line
            System.out.println();

            //if they want to reserve seats
            if (choice == 1){
                //print auditorium
                System.out.println(auditorium.toString());
                System.out.println();
                //get user input
                //validate user input for every input

                //row validation
                //try catch the row they want to reserve
                do{
                    System.out.println("Enter the row of the seat you want to reserve: ");
                    try{
                        userRow = s.nextInt();
                    }
                    catch(InputMismatchException e){
                        s.nextLine();
                        System.out.println("Row must be a number");
                    }
                    if (userRow < 1 || userRow > tempRow){
                        System.out.println("Invalid row");
                    }
                }while (userRow < 1 || userRow > tempRow);

                //column validation
                //try catch the column they want to reserve
                do{
                    try{
                        System.out.println("Enter the seat you want to reserve: ");
                        userCol = s.next().charAt(0);
                    }
                    catch(InputMismatchException e){
                        s.nextLine();
                        System.out.println("Column must be a letter");
                    }

                    if (userCol < 65 || userCol > tempCol + 65){
                        System.out.println("Invalid column");
                    }

                }while (userCol < 65 || userCol > 65 + tempCol);
                

                //adult validation
                //try catch the number of adult tickets they want to reserve
                do{
                    try{
                        System.out.println("Enter the number of adult tickets: ");
                        adult = s.nextInt();
                    }
                    catch(InputMismatchException e){
                        s.nextLine();
                        System.out.println("Invalid number");
                    }

                    if (adult < 0){
                        System.out.println("Tickets need to be greater than or equal to zero");
                    }
                }while (adult < 0);

                //child validation
                //try catch the number of child tickets they want to reserve
                do{
                    try{
                        System.out.println("Enter the number of child tickets: ");
                        child = s.nextInt();
                    }
                    catch(InputMismatchException e){
                        s.nextLine();
                        System.out.println("Invalid number");
                    }

                    if (child < 0){
                        System.out.println("Tickets need to be greater than or equal to zero");
                    }
                }while (child < 0);

                //senior validation
                //try catch the number of senior tickets they want to reserve
                do{
                    try{
                        System.out.println("Enter the number of senior tickets: ");
                        senior = s.nextInt();
                    }
                    catch(InputMismatchException e){
                        s.nextLine();
                        System.out.println("Invalid number");
                    }

                    if (senior < 0){
                        System.out.println("Tickets need to be greater than or equal to zero");
                    }
                }while (senior < 0);



                //reserve the seat(s)
                //-1 because the user input is 1 greater than the actual row
                auditorium.reserveSeats(userRow-1, userCol, adult, child, senior, auditorium, s);
                
                
            }

            else if (choice == 2){
                //display report because the end of the program is near
                auditorium.displayReport(auditorium);
            }

        }while(choice != 2);

        //close scanner
        s.close();
        
        //end the program
        
        System.exit(0);
    }

}