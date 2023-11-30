//this will be the main main class
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;

public class Main{
    //need to add the following methods
    //read from username and password file
    //store order file
    //remove seats method
    //admit settings
    //edit order file, although it could just call the remove order and then the add order method
    //view reciept method 
    //main will have to keep track of what auditorium is to be displayed



    private static class Customer{
        public String username;
        private String password;
        private ArrayList <order> orders = new ArrayList<order>();
        //public orders class
        public static class order{
            private Auditorium auditorium;
            private int row;
            private char startingColumn;
            private int adultSeats;
            private int childSeats;
            private int seniorSeats;

            public order(){
                this.auditorium = null;
                this.row = 0;
                this.startingColumn = 'A';
                this.adultSeats = 0;
                this.childSeats = 0;
                this.seniorSeats = 0;
            }
            public order(Auditorium a, int r, int col, int adult, int child, int senior){
                this.auditorium = a;
                this.row = r;
                this.startingColumn = (char)(65 + col);
                this.adultSeats = adult;
                this.childSeats = child;
                this.seniorSeats = senior;
            }

            //getters
            public Auditorium getAuditorium(){
                return this.auditorium;
            }
            public int getRow(){
                return this.row;
            }
            public char getStartingColumn(){
                return this.startingColumn;
            }
            public int getAdultSeats(){
                return this.adultSeats;
            }
            public int getChildSeats(){
                return this.childSeats;
            }
            public int getSeniorSeats(){
                return this.seniorSeats;
            }
            //setters
            public void setAuditorium(Auditorium a){
                this.auditorium = a;
            }
            public void setRow(int r){
                this.row = r;
            }
            public void setStartingColumn(char col){
                this.startingColumn = col;
            }
            public void setAdultSeats(int adult){
                this.adultSeats = adult;
            }
            public void setChildSeats(int child){
                this.childSeats = child;
            }
            public void setSeniorSeats(int senior){
                this.seniorSeats = senior;
            }
        }
        public Customer(){}

        public Customer(String username, String password){
            this.username = username;
            this.password = password;
        }
        //getters
        public String getUsername(){
            return this.username;
        }
        public String getPassword(){
            return this.password;
        }
        //setters
        public void setUsername(String username){
            this.username = username;
        }
        public void setPassword(String password){
            this.password = password;
        }
        public void addOrder(Auditorium a, int r, int col, int adult, int child, int senior){
            order o = new order(a, r, col, adult, child, senior);
            this.orders.add(o);
        }

    }

    public static HashMap<String, Customer> readFromFile(String fileName){
        HashMap<String, Customer> table = new HashMap<>();
        try{
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);
            while(fileScanner.hasNext()){
                String line = fileScanner.nextLine();
                String[] split = line.split(" ");
                Customer c = new Customer(split[0], split[1]);
                table.put(split[0], c);
            }
            fileScanner.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        return table;
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        //open auditorium files
        Auditorium a1 = null;
        Auditorium a2 = null;
        Auditorium a3 = null;
        boolean adminExit = false;
        // Customer m = new Customer();
        // Customer name = new Customer();
        // Customer pass = new Customer();
        // Customer.order o = new Customer.order();
        HashMap<String, Customer> custList;
        System.out.println("Enter the name of the file: ");
        String filename = s.nextLine();
        custList = readFromFile(filename);
        String username = "";
        
        ////////////////////////////////////////////////////////////////////////
        try{
            System.out.println("Enter the name of the file for auditorium 1: ");
            filename = s.nextLine();
            File file1 = new File(filename);
            System.out.println("Enter the name of the file for auditorium 2: ");
            filename = s.nextLine();
            File file2 = new File(filename);
            System.out.println("Enter the name of the file for auditorium 3: ");
            filename = s.nextLine();
            File file3 = new File(filename);
            Scanner fileScanner1 = new Scanner(file1);
            Scanner fileScanner2 = new Scanner(file2);
            Scanner fileScanner3 = new Scanner(file3);
            a1 = new Auditorium();
            a2 = new Auditorium();
            a3 = new Auditorium();
            //get the auditorium
            //Node node = t.auditorium.head;
            int tempRow = 0;
            Node RowHeadStore = a1.getHead();
            while (fileScanner1.hasNext()){
                String line = fileScanner1.nextLine();
                char tempCol = 65;
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
            fileScanner1.close();
            //print auditorium
            tempRow = 0;
            RowHeadStore = a2.getHead();
            while (fileScanner2.hasNext()){
                String line = fileScanner2.nextLine();
                char tempCol = 65;
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
            fileScanner2.close();
            //print auditorium
            tempRow = 0;
            RowHeadStore = a3.getHead();
            while (fileScanner3.hasNext()){
                String line = fileScanner3.nextLine();
                char tempCol = 65;
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
            fileScanner3.close();
            //print auditorium
        }
        catch(FileNotFoundException noFile){
            //if file cannot be found, print this
            System.out.println("File not found");
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////

        do{
            boolean login = false;
            Customer c = new Customer();
            pr:
            while (true) {
                
                System.out.println("Enter your username: ");
                username = s.nextLine();
                if (custList.containsKey(username)) {
                    c = custList.get(username);
                    String password = "";
                    int i = 0;

                    while (i < 3) {
                        System.out.println("Enter your password: ");
                        password = s.nextLine();
                        if (password.equals(c.password)) {
                            i = 0;
                            System.out.println("Welcome " + username);
                            login = true;
                            break;
                        }
                        else {
                            System.out.println("Incorrect password");
                            i++;
                        }
                        if (i == 3) {
                            System.out.println("Too many incorrect attempts");
                            c = null;
                            break pr;
                        }
                            
                    }
                }
                else {
                    System.out.println("Username not found");
                }
                if(login){
                    break;
                }
            }
            
            if(c != null){
                int choice = 0;
                do{
                    System.out.println("1. Reserve Seats");
                    System.out.println("2. View Orders");
                    System.out.println("3. Update Orders");
                    System.out.println("4. Display Receipt");
                    System.out.println("5. Log Out");
                    try{
                        choice = s.nextInt();
                    }
                    catch(InputMismatchException e){
                        s.nextLine();
                        System.out.println("Invalid input");
                    }
                }while(choice != 5);
                
                // System.out.println(a1.toString());
                // System.out.println();
                // System.out.println(a2.toString());
                // System.out.println();
                // System.out.println(a3.toString());
            }


        }while(!adminExit);

        
    }
    
    


}