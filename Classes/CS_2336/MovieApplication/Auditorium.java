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
    //setter
    public void setHead(char val) {
        this.head.getSeat().setTicketType(val);
        this.head.getSeat().setColumn((char)65);
        this.head.getSeat().setRow(0);
    }
    //getter
    public Node getHead() {
        return head;
    }
        //tostring method
        @Override
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

        //print out the rows
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



    //method to remove ticket
    public char removeTicket(int row, char col, Auditorium auditorium){
        //remove ticket
        //get row and column from Seat
        //get the head
        Node node = auditorium.getHead();
        //get valid row
        row--;    //just in case
        while(row != node.getSeat().getRow()){
            node = node.getDown();
        }

        //get valid seat
        while (col != node.getSeat().getColumn()){
            node = node.getNext();
        }

        //remove ticket
        if(node.getSeat().getTicketType() == 'A' || node.getSeat().getTicketType() == 'S' || node.getSeat().getTicketType() == 'C'){
            if (node.getSeat().getTicketType() == 'A'){
                node.getSeat().setTicketType('.');
                //return A for adult so that the total tickets can be updated
                return 'A';
            }
            else if (node.getSeat().getTicketType() == 'C'){
                node.getSeat().setTicketType('.');
                //return C for child so that the total tickets can be updated
                return 'C';
            }
            else if (node.getSeat().getTicketType() == 'S'){
                node.getSeat().setTicketType('.');
                //return S for senior so that the total tickets can be updated
                return 'S';
            }
        }
        //if there is no ticket, return .
            return '.';
    }

    //method to verify seats are taken by particular user
    public boolean verifySeats(int row, char col, int adult, int senior, int child, Auditorium auditorium){
        //get row and column from seat
        //get the head
        Node node = auditorium.getHead();
        //get valid row
        row--;    //just in case
        while(row != node.getSeat().getRow()){
            node = node.getDown();
        }

        //get valid seat
        while (col != node.getSeat().getColumn()){
            node = node.getNext();
        }

        //verify seats with seats in seats class
        boolean valid = true;
        for (int i = 0; i < adult; i++){
            //if the seat is not an adult seat, then it is not valid
            if(node.getSeat().getTicketType() != 'A'){
                valid = false;
            }
            //iterate to next node
            node = node.getNext();
        }
        //same thing for child
        for (int i = 0; i < child; i++){
            if(node.getSeat().getTicketType() != 'C'){
                valid = false;
            }
            node = node.getNext();
        }
        //same thing for senior
        for (int i = 0; i < senior; i++){
            if(node.getSeat().getTicketType() != 'S'){
                valid = false;
            }
            node = node.getNext();
        }

        //return valid
        return valid;
    }

    //best available function

    public String bestAvailable(int adult, int child, int senior, Auditorium auditorium, Scanner s) {
        //variable initialization
        int totalTickets = adult + child + senior;
        int bestRow = 100000000;  // Initialize with a value that can't be a valid row
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
        col++;

        //find the best seat
        for (int i = 0; i < row; i++) {
            for (int j = 0; j <= col - totalTickets + 1; j++) {
                //from the nested for loop, ever possible iteration is checked if it passes seatValidity
                if (seatValidity(i, (char)(j+65), totalTickets, auditorium)) {
                    // if the seats are valid calculate the distance for this seat using pythagorean theorem
                    //colMiddle was originally not a variable but IDE was having issues so I'm going to keep it and not risk breaking anything
                    double colMiddle = (j + (totalTickets / 2.0));

                    //distance formula/ pythagorean theorem
                    double distance = Math.sqrt(Math.pow((row)/2 - i, 2) + Math.pow((col)/2 - colMiddle, 2));
                    //System.out.print(distance + ":  ");
                    //System.out.println(i+1 + "" + (char)(j+65));
                    // the lower the distance from the middle the better, and so if a better one is found replace it ******** something wrong here **********
                    if (distance <= bestDistance) {
                        if(distance < bestDistance || distance <= bestDistance && Math.abs(i - (row/2)) < Math.abs(bestRow - (row/2)) && row < bestRow){
                            // System.out.println("best distance: " + distance);
                            bestDistance = distance;
                            bestRow = i+1;
                            bestCol = (char)(j+65);
                        }
                    }
                }
            }
        }
        if (bestRow != 100000000) {
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
                return reserveSeats(bestRow-1, bestCol, adult, child, senior, auditorium, s, 1);
            }
        } 
        else {
            //if best row is -1, then there are no seats available
            System.out.println("Not enough seats available");
        }

        return "";
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
                //if the seat is not available, then it is not valid
                if(node.getSeat().getTicketType() == 'A' || node.getSeat().getTicketType() == 'S' || node.getSeat().getTicketType() == 'C'){
                    valid = false;
                }
                //iterate to next node
                if(node.getNext() != null){
                    node = node.getNext();
                }
            }
        }
        else{
            //if the seat is not available, then it is not valid
            valid = false;
        }
        
        return valid;
    }


    public String reserveSeats(int row, char col, int adultTickets, int childTickets, int seniorTickets, Auditorium auditorium, Scanner s, int yesOrNoBestAvailable){
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
            
            String returnStatement = "" + row + "" + col;
            return returnStatement;
        }
        else if (!seatValidity(row, col, adultTickets + childTickets + seniorTickets, auditorium) && yesOrNoBestAvailable == 1){
            //do bestavailable
            //everything related to best available and console outputs are in the method best available, unlike my previous programs
            return bestAvailable(adultTickets, childTickets, seniorTickets, auditorium, s);
        }
        else{
            //if seats are not valid, return -1
            System.out.println("Seats not valid");
            return "-1";
        }
    }


    public void fileMaker(Auditorium auditorium, int num){
        //try catch to make sure file is found
        try{
           num = num + 5;
            //initialization of filewriter and printwriter
            FileOutputStream fileWriter = new FileOutputStream("A" + num +".txt");
            PrintWriter printWriter = new PrintWriter("A"+ num + ".txt");
            //go through list to get values for everything
            Node node = auditorium.getHead();
            //print to file
            while(node != null){
                Node node2 = node;
                //while loop to print out the row and column
                while(node2 != null && node2.getSeat().getTicketType() != 0){
                    //print out the row and column
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

    }

    public void auditoriumReport(Auditorium a1, Auditorium a2, Auditorium a3){
        //displays report to console
        //formated everything to match previous project and core implementation
        Node node = a1.getHead();
        int totalSeats = 0;
        int totalTickets = 0;
        int adultTickets = 0;
        int childTickets = 0;
        int seniorTickets = 0;
        double totalSales = 0;

        int totalAllSeatsOpen = 0;
        int totalAllTickets = 0;
        int totalAllAdultTickets = 0;
        int totalAllChildTickets = 0;
        int totalAllSeniorTickets = 0;
        double totalAllSales = 0;
        
        System.out.print("Auditorium 1\t");
        ///////////////
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
        //seats open
        totalAllSeatsOpen += totalSeats-totalTickets;
        System.out.printf("%d\t", totalSeats-totalTickets);
        //seats reserved
        totalAllTickets += totalTickets;
        System.out.printf("%d\t", totalTickets);
        //adult
        totalAllAdultTickets += adultTickets;
        System.out.printf("%d\t", adultTickets);
        //child
        totalAllChildTickets += childTickets;
        System.out.printf("%d\t", childTickets);
        //senior
        totalAllSeniorTickets += seniorTickets;
        System.out.printf("%d\t", seniorTickets);
        //total sales
        totalAllSales += totalSales;
        System.out.printf("$%.2f\n", totalSales);
        ///////////////

        node = a2.getHead();
        totalSeats = 0;
        totalTickets = 0;
        adultTickets = 0;
        childTickets = 0;
        seniorTickets = 0;
        totalSales = 0;
        System.out.print("Auditorium 2\t");
        /////////////////
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
        //seats open
        totalAllSeatsOpen += totalSeats-totalTickets;
        System.out.printf("%d\t", totalSeats-totalTickets);
        //seats reserved
        totalAllTickets += totalTickets;
        System.out.printf("%d\t", totalTickets);
        //adult
        totalAllAdultTickets += adultTickets;
        System.out.printf("%d\t", adultTickets);
        //child
        totalAllChildTickets += childTickets;
        System.out.printf("%d\t", childTickets);
        //senior
        totalAllSeniorTickets += seniorTickets;
        System.out.printf("%d\t", seniorTickets);
        //total sales
        totalAllSales += totalSales;
        System.out.printf("$%.2f\n", totalSales);
        ////////////////
        node = a3.getHead();
        totalSeats = 0;
        totalTickets = 0;
        adultTickets = 0;
        childTickets = 0;
        seniorTickets = 0;
        totalSales = 0;

        System.out.print("Auditorium 3\t");
        
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
        //seats open
        totalAllSeatsOpen += totalSeats-totalTickets;
        System.out.printf("%d\t", totalSeats-totalTickets);
        //seats reserved
        totalAllTickets += totalTickets;
        System.out.printf("%d\t", totalTickets);
        //adult
        totalAllAdultTickets += adultTickets;
        System.out.printf("%d\t", adultTickets);
        //child
        totalAllChildTickets += childTickets;
        System.out.printf("%d\t", childTickets);
        //senior
        totalAllSeniorTickets += seniorTickets;
        System.out.printf("%d\t", seniorTickets);
        //total sales
        totalAllSales += totalSales;
        System.out.printf("$%.2f\n", totalSales);
        
        System.out.print("Total\t");
        System.out.printf("%d\t", totalAllSeatsOpen);
        System.out.printf("%d\t", totalAllTickets);
        System.out.printf("%d\t", totalAllAdultTickets);
        System.out.printf("%d\t", totalAllChildTickets);
        System.out.printf("%d\t", totalAllSeniorTickets);
        System.out.printf("$%.2f\n", totalAllSales);
        
        System.out.println();
    }
}