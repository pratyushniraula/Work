package one;
//Pratyush Niraula
//Pxn210033
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main2{
    public static void main(String[] args){
        System.out.println("Enter the name of the file: ");
        Scanner s = new Scanner(System.in);
        String fileName = s.nextLine();
        File file = new File(fileName);
        //see if file can open or not, if it can continue with the program, if not, say it cannot be found
        try{
            Scanner fileScanner = new Scanner(file);
            //get the auditorium
            while (fileScanner.hasNext()){
                String line = fileScanner.next();
                //split the line into characters
                for(int i = 0; i < line.length(); i++){
                    char c = line.charAt(i);
                    //if the character is a letter, it is a seat
                    if(c == 'A' || c == 'S' || c == 'C'){
                        //create a seat
                        Seat seat = new Seat(i, line.charAt(0), 'A');
                        //create a node
                        Node node = new Node(seat);
                        //if the head is null, make the head the node
                        if(head == null){
                            head = node;
                        }
                        //if the head is not null, make the head the node and make the head down the node
                        else{
                            head = node;
                            head.down = node;
                        }
                    }
                    //if the character is a 1, it is a seat
                    else if(c == '1'){
                        //create a seat
                        Seat seat = new Seat(i, line.charAt(0), 'S');
                        //create a node
                        Node node = new Node(seat);
                        //if the head is null, make the head the node
                        if(head == null){
                            head = node;
                        }
                        //if the head is not null, make the head the node and make the head down the node
                        else{
                            head = node;
                            head.down = node;
                        }
                    }
                    //if the character is a 2, it is a seat
                    else if(c == '2'){
                        //create a seat
                        Seat seat = new Seat(i, line.charAt(0), 'C');
                        //create a node
                        Node node = new Node(seat);
                        //if the head is null, make the head the node
                        if(head == null){
                            head = node;
                        }
                        //if the head is not null, make the head the node and make the head down the node
                        else{
                            head = node;
                            head.down = node;
                        }
                    }
                    //if the character is a 3, it is a seat
                    else if(c == '3'){
                        //create a seat
                        Seat seat = new Seat(i, line.charAt(0), 'D');
                        //create a node
                        Node node = new Node(seat);
                        //if the head is null, make the head the node
                        if(head == null){
                            head = node;
                        }
                        //if the head is not null, make the head the node and make the head down the node
                        else
                }
            }
            //close scanner
            fileScanner.close();

        }
        catch(FileNotFoundException e){
            //if file cannot be found, print this
            System.out.println("File not found");
        }
    
    
    }


}