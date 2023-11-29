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

public class Main2{
    public static void main(String[] args){
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

            while (fileScanner.hasNext()){
                String line = fileScanner.nextLine();
                tempCol = 65;
                
                for(int i = 0; i < line.length(); i++){
                    tempCol = (char)(65 + i);
                    if (line.charAt(i) == 'A' || line.charAt(i) == 'S' || line.charAt(i) == 'C' || line.charAt(i) == '.') {
                        
                        if (RowHeadStore.getSeat().getTicketType() == 0) {
                            RowHeadStore.setSeat(new Seat(tempRow, tempCol, line.charAt(i)));
                        } 
                        
                        else {
                            Node tempNode = RowHeadStore;
                            while (tempNode.getNext() != null) {
                                tempNode = tempNode.getNext();
                            }
                            Node newNode = new Node(tempRow, tempCol, line.charAt(i));
                            tempNode.setNext(newNode);
                        }
                    }
                }
                Node oneRowDown = new Node();
                RowHeadStore.setDown(oneRowDown);
                RowHeadStore = oneRowDown;
                tempRow++;
            }
            //close scanner
            fileScanner.close();
            //print auditorium
        }
        catch(FileNotFoundException noFile){
            //if file cannot be found, print this
            System.out.println("File not found");
        }
        
        do{
            int adult = -1;
            int child = -1;
            int senior = -1;
            char userCol = 0;
            int userRow = -1;
            

            System.out.println();

            //display menu
            // System.out.println("1. Reserve Seats");
            // System.out.println("2. Exit");
            
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
            
            // choice = s.nextInt();
            System.out.println();

            
            if (choice == 1){
                //print auditorium
                // auditorium.printAuditorium(tempCol-65);
                System.out.println(auditorium.toString());
                System.out.println();
                //get user input
                //validate user input for every input

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
                auditorium.reserveSeats(userRow-1, userCol, adult, child, senior, auditorium, s);
                
                
            }

            else if (choice == 2){
                auditorium.displayReport(auditorium);
            }

        }while(choice != 2);

        //close scanner
        s.close();
        
        //end the program
        
        System.exit(0);
    }

}