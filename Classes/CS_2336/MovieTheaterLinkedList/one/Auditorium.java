//Pratyush Niraula
//Pxn210033

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.Math;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Auditorium{
    private Node head;

    //linked list is created in auditorium

    //create auditorium
    public Auditorium(){
        head = new Node();
    }

    public void setHead(char val) {
        this.head.getSeat().setTicketType(val);
        this.head.getSeat().setColumn((char)65);
        this.head.getSeat().setRow(0);
    }
    public Node getHead() {
        return head;
    }

        public String toString(){
        //find the amount of columns in each row
        int col = 0;
        Node colFinder = this.head;

        while(colFinder.getNext() != null){
            col++;
            colFinder = colFinder.getNext();

        }
        col++;

        //print letters

        // System.out.print("    ");
        String s = "    ";
        
        for(int i = 0; i < col; i++){
            // System.out.print((char)(65 + i));
            s = s + "" + (char)(65+i);
        }
        // System.out.println();
        s = s + "\n";
        int row = 0;


        Node node = head;
        while(node != null){
            if(node.getNext() != null)
            {
                row++;
                // System.out.print(row + "   ");
                s = s + row + "   ";
            }
            Node node2 = node;
            while(node2 != null && node2.getSeat().getTicketType() != 0){
                if(node2.getSeat().getTicketType() == 'A' || node2.getSeat().getTicketType() == 'S' || node2.getSeat().getTicketType() == 'C'){
                //System.out.print(node2.getSeat().getTicketType());
                // System.out.print('#');
                s = s + "#";
                node2 = node2.getNext();
                }

                else if(node2.getSeat().getTicketType() == '.'){
                    // System.out.print('.');
                    s = s + ".";
                    node2 = node2.getNext();
                }

            }
            // System.out.println();
            s = s + "\n";
            node = node.getDown();
        }
        return s;
    }



    //best available function

    public void bestAvailable(int adult, int child, int senior, Auditorium auditorium, Scanner s) {
        int totalTickets = adult + child + senior;
        int bestRow = -1;  // Initialize with a value that can't be a valid row
        char bestCol = 0;
        double bestDistance = Double.MAX_VALUE; // Initialize with a large value

        //find the amount of rows
        int row = 0;
        Node rowFinder = auditorium.getHead();
        while (rowFinder.getDown() != null) {
            row++;
            rowFinder = rowFinder.getDown();
        }
        int col = 0;
        Node colFinder = auditorium.getHead();
        while (colFinder.getNext() != null) {
            col++;
            colFinder = colFinder.getNext();
        }

    
        for (int i = 0; i < row; i++) {
            for (int j = 0; j <= col - totalTickets + 1; j++) {
                if (seatValidity(i, (char)(j+65), totalTickets, auditorium)) {
                    // Calculate the distance for this seat
                    double colMiddle = (j + (totalTickets / 2.0));
                    double distance = Math.sqrt(Math.pow(row/2 - i, 2) + Math.pow(col/2 - colMiddle, 2));
                
                    if (distance < bestDistance) {
                        bestDistance = distance;
                        bestRow = i + 1;
                        bestCol = (char)(j+65);
                    }
                }
            }
        }
    
        if (bestRow != -1) {

            if(totalTickets > 1){
                System.out.println("These are the best available seats, do you want to continue with your purchase?");
                char lastCol = (char)(bestCol + totalTickets - 1);
                System.out.println("" + bestRow + bestCol + " - " + bestRow + lastCol);
            }
            else{
                System.out.println("These are the best available seats, do you want to continue with your purchase?");
                System.out.println("" + bestRow + bestCol);
            }
    
            char yesNo = 'n';
            do {
                System.out.println("y/n?");
                try {
                    yesNo = s.next().charAt(0);
                } catch (InputMismatchException e) {
                    s.nextLine();
                    System.out.println("Must be a y, Y, n, or N");
                }
                if (yesNo != 'y' && yesNo != 'Y' && yesNo != 'n' && yesNo != 'N') {
                    System.out.println("Invalid input");
                }
            } while (yesNo != 'y' && yesNo != 'Y' && yesNo != 'n' && yesNo != 'N');
            
            if (yesNo == 'y' || yesNo == 'Y') {
                reserveSeats(bestRow-1, bestCol, adult, child, senior, auditorium, s);
            }
        } 
        else {
            System.out.println("Not enough seats available");
        }
    }

    // public void bestAvailable(int adult, int child, int senior, Auditorium auditorium, Scanner s){
    //     //find best available using pythagorean theorem
    //     int totalTickets = adult + child + senior;
    //     int row = 0; // total amount of rows, not index
    //     int col = 0; // total amount of columns, not index
    //     double colMiddle = 0; //finds the middle of every column
    //     double rowMiddle = 1000; // finds the best row possible if it is possible
    //     double distance = 0; //calcultes distance from the middle using displacement formula
    //     double bestDistance = 100000;// smallest distance from the middle
    //     int bestRow = 0; //stores the best row
    //     char bestCol = 0; // stores the best column
    //     Node node = auditorium.getHead();

    //     //loop through to find column size and row size
    //     Node counterNode = auditorium.getHead();
    //     while (counterNode.getDown() != null){
    //         row++;
    //         counterNode = counterNode.getDown();
    //     }
    //     //AHHHHHHHHHHHHHHHHHHHHH COME BACK HEREEEEEEEEEE
    //     // row++;

    //     while(counterNode.getNext() != null){
    //         col++;
    //         counterNode = counterNode.getNext();
    //     }
    //     // col++;

    //     colMiddle = col/2.0;
    //     rowMiddle = row/2.0;
        
    //     //now that the column and row middles were found, along with rows and cols iterating through a for loop is easier
    //     //iterate through all of the columns and have the total 

    //     while(node != null){
    //         for (int i = 0; i < col - totalTickets; i++){
    //             //check if the seats are available
    //             if(seatValidity(node.getSeat().getRow(), (char)(65 + i), totalTickets, auditorium)){
    //                 //change the values of row and col
    //                 col = i;
    //                 row = node.getSeat().getRow();

    //                 //calculate distance
    //                 distance = Math.sqrt(Math.pow((rowMiddle - row), 2) + Math.pow((colMiddle - i), 2));
    //                 //check if distance is less than best distance
    //                 if(distance < bestDistance){
    //                     bestDistance = distance;
    //                     bestRow = row;
    //                     bestCol = (char)(65 + i);
    //                 }
    //             }
    //         }
    //         node = node.getDown();
    //     }

    //     //this will check if the last row is the best row if best row has not been found
    //     if(bestDistance >= 10000){
    //         while(node != null){
    //             for (int i = 0; i < col - totalTickets; i++){
    //                 //check if the seats are available
    //                 if(seatValidity(row, (char)(65 + i), totalTickets, auditorium)){
    //                     //calculate distance
    //                     col = i;
    //                     row = node.getSeat().getRow();
    //                     distance = Math.sqrt(Math.pow((rowMiddle - row), 2) + Math.pow((colMiddle - i), 2));
    //                     //check if distance is less than best distance
    //                     if(distance < bestDistance){
    //                         bestDistance = distance;
    //                         bestRow = row;
    //                         bestCol = (char)(65 + i);
    //                     }
    //                 }
    //             }
    //         }
    //     }

    //     //reserve seats
    //     if (seatValidity(bestRow, bestCol, totalTickets, auditorium)){
    //         // reserveSeats(bestRow, bestCol, totalTickets, 0, 0, auditorium);
    //         System.out.println("these are the best available seats, do you want to continue with your purchase?");
    //         System.out.println("" + bestRow + "" + bestCol);

    //         char yesNo = 'n';
    //         //get user input
    //         //validate user input for every input
    //         do{
    //             System.out.println("y/n?");
    //             try{
    //                 yesNo = s.next().charAt(0);
    //             }
    //             catch(InputMismatchException e){
    //                 s.nextLine();
    //                 System.out.println("Must be a y, Y, n, or N");
    //             }
    //             if (yesNo != 'y' && yesNo != 'Y' && yesNo != 'n' && yesNo != 'N'){
    //                 System.out.println("Invalid input");
    //             }
    //         }while (yesNo != 'y' && yesNo != 'Y' && yesNo != 'n' && yesNo != 'N');


    //         reserveSeats(bestRow, bestCol, adult, child, senior, auditorium, s);

    //     }
    //     else{
    //         System.out.println("Not enough seats available");
    //     }
    // }


    public boolean seatValidity(int row, char column, int totalTickets, Auditorium auditorium){
        //check if seat is valid
        Node node = auditorium.getHead();
        boolean valid = true;
        //get valid row
        while(row != node.getSeat().getRow() && node.getDown() != null){
            node = node.getDown();
        }

        //get valid seat
        while (column != node.getSeat().getColumn() && node.getNext() != null){
            node = node.getNext();
        }
        

        //check if seat is available
        if(row == node.getSeat().getRow() && column == node.getSeat().getColumn()){
            for (int i = 0; i < totalTickets; i++){
                if(node.getSeat().getTicketType() == 'A' || node.getSeat().getTicketType() == 'S' || node.getSeat().getTicketType() == 'C'){
                    valid = false;
                }
                if(node.getNext() != null){
                    node = node.getNext();
                }
            }
        }
        else{
            valid = false;
        }
        
        return valid;
    }

    // public boolean seatValidity(int row, char column, int totalTickets, Auditorium auditorium) {
    //     Node node = auditorium.getHead();
        
    //     // Find the node for the given row and column
    //     while (node != null && (node.getSeat().getRow() != row || node.getSeat().getColumn() != column)) {
    //         node = node.getNext();
    //     }
        
    //     if (node == null) {
    //         // Seat not found, or it's out of bounds
    //         return false;
    //     }
        
    //     // Check if the total number of tickets you want to reserve are available in sequence
    //     Node seatNode = node;
    //     for (int i = 0; i < totalTickets; i++) {
    //         if (seatNode == null || seatNode.getSeat().getTicketType() != '.') {
    //             return false;
    //         }
    //         seatNode = seatNode.getNext();
    //     }
        
    //     return true;
    // }


    public void reserveSeats(int row, char col, int adultTickets, int childTickets, int seniorTickets, Auditorium auditorium, Scanner s){
        //reserve seats
        Node node = auditorium.getHead();
        //get valid row
        while(row != node.getSeat().getRow()){
            node = node.getDown();
        }

        //get valid seat
        while (col != node.getSeat().getColumn()){
            node = node.getNext();
        }

        //reserve seats
        if (seatValidity(row, col, adultTickets + childTickets + seniorTickets, auditorium)){
            
            for (int i = 0; i < adultTickets; i++){
                node.getSeat().setTicketType('A');
                node = node.getNext();
            }

            for (int i = 0; i < childTickets; i++){
                node.getSeat().setTicketType('C');
                node = node.getNext();
            }
            
            for (int i = 0; i < seniorTickets; i++){
                node.getSeat().setTicketType('S');
                node = node.getNext();
            }
            System.out.println("seats reserved");
        }
        else{
            //do bestavailable
            
            bestAvailable(adultTickets, childTickets, seniorTickets, auditorium, s);

        }
    }


    public void displayReport(Auditorium auditorium){
        //write to file
        try{
            FileOutputStream fileWriter = new FileOutputStream("A1.txt");
            PrintWriter printWriter = new PrintWriter("A1.txt");
            Node node = auditorium.getHead();
            while(node != null){
                Node node2 = node;
                while(node2 != null && node2.getSeat().getTicketType() != 0){
                    printWriter.print(node2.getSeat().getTicketType());
                    node2 = node2.getNext();
                }
                printWriter.println();
                node = node.getDown();
            }
            printWriter.close();
            fileWriter.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
        }catch(Exception e){
            System.out.println("Error");
        }

        //go through list to get values for everything
        Node node = auditorium.getHead();
        int totalSeats = 0;
        int totalTickets = 0;
        int adultTickets = 0;
        int childTickets = 0;
        int seniorTickets = 0;
        double totalSales = 0;

        while(node != null){
            Node node2 = node;
            while(node2 != null && node2.getSeat().getTicketType() != 0){
                totalSeats++;

                if(node2.getSeat().getTicketType() == 'A' || node2.getSeat().getTicketType() == 'S' || node2.getSeat().getTicketType() == 'C'){
                    if(node2.getSeat().getTicketType() == 'A'){
                        adultTickets++;
                        totalTickets++;
                        totalSales += 10;
                    }

                    else if(node2.getSeat().getTicketType() == 'C'){
                        childTickets++;
                        totalTickets++;
                        totalSales += 5;
                    }

                    else if(node2.getSeat().getTicketType() == 'S'){
                        seniorTickets++;
                        totalTickets++;
                        totalSales += 7.5;
                    }

                }
                node2 = node2.getNext();
            }
            node = node.getDown();
        }



        //display report to console
        System.out.printf("Total Seats:\t%d\n", totalSeats);
        System.out.printf("Total Tickets:\t%d\n", totalTickets);
        System.out.printf("Adult Tickets:\t%d\n", adultTickets);
        System.out.printf("Child Tickets:\t%d\n", childTickets);
        System.out.printf("Senior Tickets:\t%d\n", seniorTickets);
        System.out.printf("Total Sales:\t$%.2f\n", totalSales);
    }
}

//for best avail use doubles for everything, remember 