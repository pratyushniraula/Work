//Pratyush Niraula
//Pxn210033

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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

    //print auditorium
    public void printAuditorium(int col){
        
        //print letters
        System.out.print("    ");
        for(int i = 0; i < col; i++){
            System.out.print((char)(65 + i));
        }
        System.out.println();
        int row = 0;


        Node node = head;
        while(node != null){
            if(node.getNext() != null)
            {
                row++;
                System.out.print(row + "   ");
            }
            Node node2 = node;
            while(node2 != null && node2.getSeat().getTicketType() != 0){
                if(node2.getSeat().getTicketType() == 'A' || node2.getSeat().getTicketType() == 'S' || node2.getSeat().getTicketType() == 'C'){
                //System.out.print(node2.getSeat().getTicketType());
                System.out.print('#');
                node2 = node2.getNext();
                }
                else if(node2.getSeat().getTicketType() == '.'){
                    System.out.print('.');
                    node2 = node2.getNext();
                }

            }
            System.out.println();
            node = node.getDown();
        }
    }

    public boolean seatValidity(int row, char column, int totalTickets, Auditorium auditorium){
        //check if seat is valid
        Node node = auditorium.getHead();
        boolean valid = true;
        //get valid row
        while(row != node.getSeat().getRow()){
            node = node.getDown();
        }

        //get valid seat
        while (column != node.getSeat().getColumn()){
            node = node.getNext();
        }
        

        //check if seat is available
        for (int i = 0; i < totalTickets; i++){
            if(node.getSeat().getTicketType() == 'A' || node.getSeat().getTicketType() == 'S' || node.getSeat().getTicketType() == 'C'){
                valid = false;
            }
            node = node.getNext();
        }
        return valid; 
    }
    

    public void reserveSeats(int row, char col, int adultTickets, int childTickets, int seniorTickets, Auditorium auditorium){
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
            System.out.println("seats not available");
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