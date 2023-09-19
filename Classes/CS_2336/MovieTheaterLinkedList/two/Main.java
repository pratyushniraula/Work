//Pratyush Niraula
//Pxn210033
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.*;
import java.lang.String;

public class Main{
    //make a new constructor for auditorium and call it 
    //Auditorium auditorium = new Auditorium();
    
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
                    //if head is empty, set head to the first value in the file
                    /* 
                    if(RowHeadStore.getSeat().getTicketType() == 0){
                        auditorium.setHead(line.charAt(0));
                    }

                    else{
                        if (line.charAt(i) == 'A' || line.charAt(i) == 'S' || line.charAt(i) == 'C' || line.charAt(i) == '.'){
                            
                            Node tempNode = RowHeadStore;
                            while(tempNode.getNext() != null){
                                //initialize a new node and in that node have an overloaded seat constructor called in order to make a new variable    
                                tempNode = tempNode.getNext();
                                
                            }
                            Node newNode = new Node(tempRow, tempCol, line.charAt(i));
                            tempNode.setNext(newNode);

                        }
                    }
                    */
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
            System.out.println("1. Reserve Seats");
            System.out.println("2. Exit");
            choice = s.nextInt();
            System.out.println();

            
            if (choice == 1){
                //print auditorium
                auditorium.printAuditorium(tempCol-65);
                System.out.println();
                //get user input
                //validate user input for every input
                while (userRow < 1 || userRow > tempRow){
                    System.out.println("Enter the row of the seat you want to reserve: ");
                    userRow = s.nextInt();
                    if (userRow < 1 || userRow > tempRow){
                        System.out.println("Invalid row");
                    }
                }
                
                while (userCol < 65 || userCol > 65 + tempCol){
                    System.out.println("Enter the seat you want to reserve: ");
                    userCol = s.next().charAt(0);
                    if (userCol < 65 || userCol > tempCol + 65){
                        System.out.println("Invalid seat");
                    }
                }
                
                while (adult < 0){
                    System.out.println("Enter the number of adult tickets: ");
                    adult = s.nextInt();
                    if (adult < 0){
                        System.out.println("Invalid number of tickets");
                    }
                }

                while (child < 0){
                    System.out.println("Enter the number of child tickets: ");
                    child = s.nextInt();
                    if (child < 0){
                        System.out.println("Invalid number of tickets");
                    }
                }

                while (senior < 0){
                    System.out.println("Enter the number of senior tickets: ");
                    senior = s.nextInt();
                    if (senior < 0){
                        System.out.println("Invalid number of tickets");
                    }
                }



                //reserve the seat(s)
                auditorium.reserveSeats(userRow-1, userCol, adult, child, senior, auditorium);
                
                
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