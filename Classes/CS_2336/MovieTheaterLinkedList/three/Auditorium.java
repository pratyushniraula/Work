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
        
        //get the amount of columns
        while(colFinder.getNext() != null){
            col++;
            colFinder = colFinder.getNext();

        }
        col++;

        //print letters
        String s = "    ";
        for(int i = 0; i < col; i++){
            s = s + "" + (char)(65+i);
        }
       //get a new line
        s = s + "\n";
        int row = 0;


        Node node = head;
        while(node != null){
            if(node.getNext() != null)
            {
                row++;
                //start printing out rows with spaces next to them (3 to be exact)
                s = s + row + "   ";
            }
            Node node2 = node;
            while(node2 != null && node2.getSeat().getTicketType() != 0){
                if(node2.getSeat().getTicketType() == 'A' || node2.getSeat().getTicketType() == 'S' || node2.getSeat().getTicketType() == 'C'){
                //if tickettype is A, S, or C, print out # because user does not need to know what type of ticket it is
                s = s + "#";
                node2 = node2.getNext();
                }
                //if it is a dot, leave it as a dot
                else if(node2.getSeat().getTicketType() == '.'){
                    // System.out.print('.');
                    s = s + ".";
                    node2 = node2.getNext();
                }

            }
            // get a new line and move to the next row
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
        //find the amount of columns
        int col = 0;
        Node colFinder = auditorium.getHead();
        while (colFinder.getNext() != null) {
            col++;
            colFinder = colFinder.getNext();
        }

        //find the best seat
        for (int i = 0; i < row; i++) {
            for (int j = 0; j <= col - totalTickets + 1; j++) {
                //from the nested for loop, ever possible iteration is checked if it passes seatValidity
                if (seatValidity(i, (char)(j+65), totalTickets, auditorium)) {
                    // if the seats are valid calculate the distance for this seat using pythagorean theorem
                    //colMiddle was originally not a variable but IDE was having issues so I'm going to keep it and not risk breaking anything
                    double colMiddle = (j + (totalTickets / 2.0));
                    //distance formula/ pythagorean theorem
                    double distance = Math.sqrt(Math.pow(row/2 - i, 2) + Math.pow(col/2 - colMiddle, 2));
                    // the lower the distance from the middle the better, and so if a better one is found replace it
                    if (distance < bestDistance) {
                        bestDistance = distance;
                        bestRow = i + 1;
                        bestCol = (char)(j+65);
                    }
                }
            }
        }
    
        if (bestRow != -1) {
            //if best row is not -1, then there are seats available
            if(totalTickets > 1){
                //if there are more than one ticket, print out the range of seats
                System.out.println("These are the best available seats, do you want to continue with your purchase?");
                char lastCol = (char)(bestCol + totalTickets - 1);
                System.out.println("" + bestRow + bestCol + " - " + bestRow + lastCol);
            }
            else{
                //if there is only one ticket, print out the seat
                System.out.println("These are the best available seats, do you want to continue with your purchase?");
                System.out.println("" + bestRow + bestCol);
            }
            //ask user if they want to continue with their purchase
            char yesNo = 'n';
            do {
                System.out.println("y/n?");
                //do try catch to make sure user inputs a valid input
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
                //if user wants to continue with their purchase, reserve seats
                reserveSeats(bestRow-1, bestCol, adult, child, senior, auditorium, s);
            }
        } 
        else {
            //if best row is -1, then there are no seats available
            System.out.println("Not enough seats available");
        }
    }


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
            
            //reserve adults first
            for (int i = 0; i < adultTickets; i++){
                node.getSeat().setTicketType('A');
                node = node.getNext();
            }

            //reserve children second
            for (int i = 0; i < childTickets; i++){
                node.getSeat().setTicketType('C');
                node = node.getNext();
            }
            
            //reserve seniors third
            for (int i = 0; i < seniorTickets; i++){
                node.getSeat().setTicketType('S');
                node = node.getNext();
            }
            System.out.println("seats reserved");
        }
        else{
            //do bestavailable
            //everything related to best available and console outputs are in the method best available, unlike my previous programs
            bestAvailable(adultTickets, childTickets, seniorTickets, auditorium, s);

        }
    }


    public void displayReport(Auditorium auditorium){
        //write to file
        try{
            //initialization of filewriter and printwriter
            FileOutputStream fileWriter = new FileOutputStream("A1.txt");
            PrintWriter printWriter = new PrintWriter("A1.txt");
            //go through list to get values for everything
            Node node = auditorium.getHead();
            //print to file
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
        //set everything to default values
        Node node = auditorium.getHead();
        int totalSeats = 0;
        int totalTickets = 0;
        int adultTickets = 0;
        int childTickets = 0;
        int seniorTickets = 0;
        double totalSales = 0;

        //go through list to get values for everything
        while(node != null){
            Node node2 = node;
            while(node2 != null && node2.getSeat().getTicketType() != 0){
                totalSeats++;

                //check if seat contains adult, child or senior
                if(node2.getSeat().getTicketType() == 'A' || node2.getSeat().getTicketType() == 'S' || node2.getSeat().getTicketType() == 'C'){
                    //if it is adult
                    if(node2.getSeat().getTicketType() == 'A'){
                        adultTickets++;
                        totalTickets++;
                        totalSales += 10;
                    }
                    //if it is child
                    else if(node2.getSeat().getTicketType() == 'C'){
                        childTickets++;
                        totalTickets++;
                        totalSales += 5;
                    }
                    //if it is senior
                    else if(node2.getSeat().getTicketType() == 'S'){
                        seniorTickets++;
                        totalTickets++;
                        totalSales += 7.5;
                    }

                }
                //iterates to next node
                node2 = node2.getNext();
            }
            //iterates to next node
            node = node.getDown();
        }



        //displays report to console
        //formated everything to match previous project and core implementation
        System.out.printf("Total Seats:\t%d\n", totalSeats);
        System.out.printf("Total Tickets:\t%d\n", totalTickets);
        System.out.printf("Adult Tickets:\t%d\n", adultTickets);
        System.out.printf("Child Tickets:\t%d\n", childTickets);
        System.out.printf("Senior Tickets:\t%d\n", seniorTickets);
        System.out.printf("Total Sales:\t$%.2f\n", totalSales);
    }
}