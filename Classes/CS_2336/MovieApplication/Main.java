//Pratyush 
//this will be the main main class
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.lang.String;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

// make reserve seats into a method that returns an array as to what seats were reserved
//or make reserve seats into a string method that returns the starting seat that was reserved
public class Main{
    //remove seats method
    //edit order file, although it could just call the remove order and then the add order method

    private static class Customer{
        public String username;
        private String password;
        private ArrayList <order> orders = new ArrayList<order>();
        //public orders class
        public static class order{
            private Auditorium auditorium;
            private int auditoriumNumber;
            private int row;
            private char startingColumn;
            private int adultSeats;
            private int childSeats;
            private int seniorSeats;
            private ArrayList <seats> seatz = new ArrayList<seats>();
                //public seats class
                public static class seats{
                    private int row;
                    private char column;
                    private char ticketType;
                    public seats(){
                        this.row = 0;
                        this.column = '/';
                        this.ticketType = 0;
                    }
                    public seats(int row, char column, char ticketType){
                        this.row = row;
                        this.column = column;
                        this.ticketType = ticketType;
                    }
                    //getters
                    public int getRow(){
                        return this.row;
                    }
                    public char getColumn(){
                        return this.column;
                    }
                    public char getTicketType(){
                        return this.ticketType;
                    }
                    //setters
                    public void setRow(int row){
                        this.row = row;
                    }
                    public void setColumn(char column){
                        this.column = column;
                    }
                    public void setTicketType(char ticket){
                        this.ticketType = ticket;
                    }
                    //toString method
                    @Override
                    public String toString(){
                        String s = "" + this.ticketType;
                        return s;
                    }
                }
            //default constructor
            public order(){
                this.auditorium = null;
                this.row = 0;
                this.startingColumn = 'A';
                this.adultSeats = 0;
                this.childSeats = 0;
                this.seniorSeats = 0;
            }
            //overloaded constructor
            public order(Auditorium a, int audnum,  int r, char col, int adult, int child, int senior){
                this.auditorium = a;
                this.row = r;
                this.auditoriumNumber = audnum;
                this.startingColumn = col;
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
            public ArrayList<seats> getSeats(){
                return this.seatz;
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
            public void setSeats(ArrayList<seats> seatzz){
                this.seatz = seatzz;
            }
        }
        //default constructor
        public Customer(){}
        //overloaded constructor
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
        public ArrayList<order> getOrders(){
            return this.orders;
        }

        //setters
        public void setUsername(String username){
            this.username = username;
        }
        public void setPassword(String password){
            this.password = password;
        }
        public void setOrders(ArrayList<order> orders){
            this.orders = orders;
        }
        
        //toString method
        public void addOrder(Auditorium a, int audnum,  int r, char col, int adult, int child, int senior){
            order o = new order(a, audnum, r, col, adult, child, senior);
            this.orders.add(o);
        }

    }
    

    public static HashMap<String, Customer> readFromFile(){
        HashMap<String, Customer> table = new HashMap<>();
        try{
            //read from file
            File file = new File("userdb.dat");
            Scanner fileScanner = new Scanner(file);
            //read from file
            while(fileScanner.hasNext()){
                String line = fileScanner.nextLine();
                String[] split = line.split(" ");
                //create customer object from file
                Customer c = new Customer(split[0], split[1]);
                //add customer object to hashmap
                table.put(split[0], c);
            }
            fileScanner.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        return table;
    }
    
    //verify if a user has a seat
    //use for cancel by doing a traversal through the arraylist of orders and checking row number of the order, and start at the column they want to start at
    public static boolean verifySeat(Customer c, int audnum, int row, char col){
        for(int i = 0; i < c.orders.size(); i++){
            //check if the auditorium number is the same
            if(c.orders.get(i).auditoriumNumber == audnum){
                //loop through the arraylist of seats
                for(int j = 0; j < c.orders.get(i).seatz.size(); j++){
                    //check if the row and column are the same
                    if(c.orders.get(i).seatz.get(j).getRow() == row+1 && c.orders.get(i).seatz.get(j).getColumn() == col){
                        //if they are, return true
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        //open auditorium files
        //variable initializations
        Auditorium a1 = null;
        Auditorium a2 = null;
        Auditorium a3 = null;
        boolean adminExit = false;
        int maxrow1 = 0;
        int maxrow2 = 0;
        int maxrow3 = 0;
        char maxcol1 = 0;
        char maxcol2 = 0;
        char maxcol3 = 0;
        // Customer m = new Customer();
        // Customer name = new Customer();
        // Customer pass = new Customer();
        // Customer.order o = new Customer.order();
        HashMap<String, Customer> custList;
        custList = readFromFile();
        String username = "";
        
        //String filename;
        ////////////////////////////////////////////////////////////////////////
        try{
            //file initializations
            File file1 = new File("A1.txt");
            File file2 = new File("A2.txt");
            File file3 = new File("A3.txt");

            //scanner initializations
            Scanner fileScanner1 = new Scanner(file1);
            Scanner fileScanner2 = new Scanner(file2);
            Scanner fileScanner3 = new Scanner(file3);

            //create auditorium objects to house the auditoriums
            a1 = new Auditorium();
            a2 = new Auditorium();
            a3 = new Auditorium();

            //get the auditoriums from the files
            int tempRow = 0;
            Node RowHeadStore = a1.getHead();
            while (fileScanner1.hasNext()){
                String line = fileScanner1.nextLine();
                char tempCol = 65;
                //loop through the line
                for(int i = 0; i < line.length(); i++){
                    //get the column
                    tempCol = (char)(65 + i);
                    if (line.charAt(i) == 'A' || line.charAt(i) == 'S' || line.charAt(i) == 'C' || line.charAt(i) == '.') {
                        //if the seat is empty, create a new seat object and add it to the linked list
                        if (RowHeadStore.getSeat().getTicketType() == 0) {
                            //create a new seat object
                            RowHeadStore.setSeat(new Seat(tempRow, tempCol, line.charAt(i)));
                        } 
                        
                        else {
                            //if the seat is not empty, traverse the linked list until the end and add the seat there
                            Node tempNode = RowHeadStore;
                            //traverse the linked list
                            while (tempNode.getNext() != null) {
                                tempNode = tempNode.getNext();
                            }
                            //create a new node and add it to the linked list
                            Node newNode = new Node(tempRow, tempCol, line.charAt(i));
                            //add the node to the linked list
                            tempNode.setNext(newNode);
                        }
                    }
                }
                //create a new node and add it to the linked list
                Node oneRowDown = new Node();
                //add the node to the linked list
                RowHeadStore.setDown(oneRowDown);
                //move the head down one row
                RowHeadStore = oneRowDown;
                //increment the row
                tempRow++;
                //get the max column
                maxcol1 = tempCol;
            }
            //get the max row
            maxrow1 = tempRow;
            //close scanner
            fileScanner1.close();
            //print auditorium
            tempRow = 0;
            //reset the head
            RowHeadStore = a2.getHead();
            //loop through the file
            while (fileScanner2.hasNext()){
                //get the line
                String line = fileScanner2.nextLine();
                //start column at A
                char tempCol = 65;
                //loop through the line
                for(int i = 0; i < line.length(); i++){
                    //get the column
                    tempCol = (char)(65 + i);
                    //if the seat is empty, create a new seat object and add it to the linked list
                    if (line.charAt(i) == 'A' || line.charAt(i) == 'S' || line.charAt(i) == 'C' || line.charAt(i) == '.') {
                        //if the seat is empty, create a new seat object and add it to the linked list
                        if (RowHeadStore.getSeat().getTicketType() == 0) {
                            RowHeadStore.setSeat(new Seat(tempRow, tempCol, line.charAt(i)));
                        } 
                        //if the seat is not empty, traverse the linked list until the end and add the seat there
                        else {
                            Node tempNode = RowHeadStore;
                            //traverse the linked list
                            while (tempNode.getNext() != null) {
                                tempNode = tempNode.getNext();
                            }
                            //create a new node and add it to the linked list
                            Node newNode = new Node(tempRow, tempCol, line.charAt(i));
                            tempNode.setNext(newNode);
                        }
                    }
                }
                //create a new node and add it to the linked list
                Node oneRowDown = new Node();
                RowHeadStore.setDown(oneRowDown);
                RowHeadStore = oneRowDown;
                tempRow++;
                maxcol2 = tempCol;
            }
            maxrow2 = tempRow;
            //close scanner
            fileScanner2.close();
            //reset the row
            tempRow = 0;
            //reset the head
            RowHeadStore = a3.getHead();
            //loop through the file
            while (fileScanner3.hasNext()){
                //get the line
                String line = fileScanner3.nextLine();
                //start column at A
                char tempCol = 65;
                //loop through the line
                for(int i = 0; i < line.length(); i++){
                    //get the column
                    tempCol = (char)(65 + i);
                    //if the seat is empty, create a new seat object and add it to the linked list
                    if (line.charAt(i) == 'A' || line.charAt(i) == 'S' || line.charAt(i) == 'C' || line.charAt(i) == '.') {
                        //if the seat is empty, create a new seat object and add it to the linked list
                        if (RowHeadStore.getSeat().getTicketType() == 0) {
                            RowHeadStore.setSeat(new Seat(tempRow, tempCol, line.charAt(i)));
                        } 
                        //if the seat is not empty, traverse the linked list until the end and add the seat there
                        else {
                            //traverse the linked list
                            Node tempNode = RowHeadStore;
                            while (tempNode.getNext() != null) {
                                tempNode = tempNode.getNext();
                            }
                            //create a new node and add it to the linked list
                            Node newNode = new Node(tempRow, tempCol, line.charAt(i));
                            tempNode.setNext(newNode);
                        }
                    }
                }
                //create a new node and add it to the linked list
                Node oneRowDown = new Node();
                //set the node to the linked list one row down
                RowHeadStore.setDown(oneRowDown);
                //move the head down one row
                RowHeadStore = oneRowDown;
                //increment the row
                tempRow++;
                //get the max column
                maxcol3 = tempCol;
            }
            maxrow3 = tempRow;
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
            //variable initializations
            boolean login = false;
            Customer c = new Customer();
            //print menu, using a lavel to restart the loop if password is wrong
            pr:
            while (true) {
                //enter username
                System.out.println("Enter your username: ");
                username = s.nextLine();
                //check if username is in the hashmap
                if (custList.containsKey(username)) {
                    c = custList.get(username);
                    String password = "";
                    int i = 0;
                    //enter password if wrong 3 times loop back with label
                    while (i < 3) {
                        //enter password
                        System.out.println("Enter your password: ");
                        password = s.nextLine();
                        //check if password is correct
                        if (password.equals(c.password)) {
                            i = 0;
                            System.out.println("Welcome " + username);
                            //set login to true and break loop
                            login = true;
                            break;
                        }
                        //if password is wrong, increment i
                        else {
                            System.out.println("Invalid password");
                            i++;
                        }
                        //if i is 3, break loop and go back to login
                        if (i == 3) {
                            System.out.println("Too many incorrect attempts");
                            c = null;
                            break pr;
                        }
                            
                    }
                }
                //if username is not in the hashmap, print this
                else {
                    System.out.println("Username not found");
                }
                if(login){
                    //break loop
                    break;
                }
            }
            // if admin is logged in
            if (login && c.getUsername().equals("admin")){
                int choice = 0;
                do{
                    //give menu
                    System.out.println("1. Print Report\n2. Log Out\n3. Exit");

                    //get user input with try catch
                    try{
                        choice = s.nextInt();
                    }
                    catch(InputMismatchException e){
                        s.nextLine();
                        System.out.println("Invalid input");
                    }

                    if (choice == 1){
                        //print report
                        a1.auditoriumReport(a1,a2,a3);
                    }
                    else if (choice == 2){
                        //log out
                        //basically do nothing
                    }
                    else if (choice == 3){
                        //exit
                        adminExit = true;
                        //load data of auditoriums into a1 a2 and a3 files
                        //save data of auditoriums into a1 a2 and a3 files
                        a1.fileMaker(a1, 1);
                        a2.fileMaker(a2, 2);
                        a3.fileMaker(a3, 3);

                    }
                }while (choice != 2 && choice != 3);
            }

            //user menu
            else if(c != null){
                int choice = 0;
                do{
                    //give menu
                    System.out.println("1. Reserve Seats");
                    System.out.println("2. View Orders");
                    System.out.println("3. Update Orders");
                    System.out.println("4. Display Receipt");
                    System.out.println("5. Log Out");
                    try{
                        //get user input with try catch
                        choice = s.nextInt();
                    }
                    catch(InputMismatchException e){
                        s.nextLine();
                        System.out.println("Invalid input");
                    }

                if(choice == 1){
                    //reserve seats
                    System.out.println("What auditorium would you like to reserve in?");
                    System.out.println("1. Auditorium 1\n2. Auditorium 2\n3. Auditorium 3");
                    int audichoice = 0;
                    do{
                        try{
                            //get user input with try catch
                            audichoice = s.nextInt();
                        }
                        catch(InputMismatchException e){
                            s.nextLine();
                            System.out.println("Invalid input");
                        }
                        //validate user input
                        if (audichoice != 1 && audichoice != 2 && audichoice != 3){
                            System.out.println("Invalid input, try again: ");
                        }

                    }while (audichoice != 1 && audichoice != 2 && audichoice != 3);
                        //variable initializations
                        int maxcol;
                        int maxrow;
                        Auditorium audi;
                        //get the auditorium
                        if (audichoice == 1){
                            //set the auditorium for auditorium 1
                            audi = a1;
                            maxcol = maxcol1;
                            maxrow = maxrow1;
                            System.out.println("Auditorium 1 was selected!");
                        }
                        else if (audichoice == 2){
                            //set the auditorium for auditorium 2
                            audi = a2;
                            maxcol = maxcol2;
                            maxrow = maxrow2;
                            System.out.println("Auditorium 2 was selected!");
                        }
                        else{
                            //set the auditorium for auditorium 3
                            audi = a3;
                            maxcol = maxcol3;
                            maxrow = maxrow3;
                            System.out.println("Auditorium 3 was selected!");
                        }

                    //variable initializations
                    int adult = -1;
                    int child = -1;
                    int senior = -1;
                    //reserve seats
                    //print auditorium
                    System.out.println("\n");
                    System.out.println(audi.toString());
                    System.out.println();
                    //get user input
                    //validate user input for every input

                    //try catch the row they want to reserve
                    int userRow = -1;
                    do{
                        System.out.println("Enter the row of the seat you want to reserve: ");
                        try{
                            userRow = s.nextInt();
                        }
                        catch(InputMismatchException e){
                            s.nextLine();
                            System.out.println("Row must be a number");
                        }
                        if (userRow < 1 || userRow > maxrow){
                            System.out.println("Invalid row");
                        }
                    }while (userRow < 1 || userRow > maxrow);

                    //column validation
                    //try catch the column they want to reserve
                    char userCol = 0;
                    do{
                        System.out.println("Enter the column of the seat you want to reserve: ");
                        try{
                            userCol = s.next().charAt(0);
                        }
                        catch(InputMismatchException e){
                            s.nextLine();
                            System.out.println("Column must be a letter");
                        }
                        if (userCol < 65 || userCol > maxcol){
                            System.out.println("Invalid column");
                        }
                    }while (userCol < 65 || userCol > maxcol);

                    //adult validation
                    //try catch the number of adult seats they want to reserve
                    do{
                        System.out.println("Enter the number of adult seats you want to reserve: ");
                        try{
                            adult = s.nextInt();
                        }
                        catch(InputMismatchException e){
                            s.nextLine();
                            System.out.println("Number of adult seats must be a number");
                        }
                        if (adult < 0){
                            System.out.println("Invalid number of adult seats");
                        }
                    }while (adult < 0);

                    //child validation
                    //try catch the number of child seats they want to reserve
                    do{
                        System.out.println("Enter the number of child seats you want to reserve: ");
                        try{
                            child = s.nextInt();
                        }
                        catch(InputMismatchException e){
                            s.nextLine();
                            System.out.println("Number of child seats must be a number");
                        }
                        if (child < 0){
                            System.out.println("Invalid number of child seats");
                        }
                    }while (child < 0);

                    //senior validation
                    //try catch the number of senior seats they want to reserve
                    do{
                        System.out.println("Enter the number of senior seats you want to reserve: ");
                        try{
                            senior = s.nextInt();
                        }
                        catch(InputMismatchException e){
                            s.nextLine();
                            System.out.println("Number of senior seats must be a number");
                        }
                        if (senior < 0){
                            System.out.println("Invalid number of senior seats");
                        }
                    }while (senior < 0);
                    
                    //check if seats are available
                    //if seats are available, reserve them
                    if (audichoice == 1){
                        //reserve seats
                        String seatIdentifier = a1.reserveSeats(userRow-1, userCol, adult, child, senior, a1, s, 1);
                        
                        if(seatIdentifier.equals("")){
                            System.out.println("Seats could not be reserved");
                            continue;
                        }
                        else{
                            //break string into two parts
                            char[] split = seatIdentifier.toCharArray();
                            //add order to customer
                            userRow = Character.getNumericValue(split[0]) + 1;
                            userCol = split[1];
                        }
                        //add order to customer
                        c.addOrder(a1, 1,  userRow, userCol, adult, child, senior);
                        // add order to arraylist in seat class
                        char tempCol = userCol;
                        while(adult > 0){
                            //access the seat arraylist in the order class
                            Customer.order.seats seat = new Customer.order.seats(userRow, tempCol, 'A');
                            //add seat to the arraylist
                            c.orders.get(c.orders.size()-1).seatz.add(seat);
                            //decrement adult
                            adult--;
                            tempCol++;
                        }
                        while(child > 0){
                            //access the seat arraylist in the order class
                            Customer.order.seats seat = new Customer.order.seats(userRow, tempCol, 'C');
                            //add seat to the arraylist
                            c.orders.get(c.orders.size()-1).seatz.add(seat);
                            //decrement child
                            child--;
                            tempCol++;
                        }
                        while(senior > 0){
                            //access the seat arraylist in the order class
                            Customer.order.seats seat = new Customer.order.seats(userRow, tempCol, 'S');
                            //add seat to the arraylist
                            c.orders.get(c.orders.size()-1).seatz.add(seat);
                            //decrement senior
                            senior--;
                            tempCol++;
                        }

                    }
                    else if (audichoice == 2){
                        //reserve seats
                        String seatIdentifier = a2.reserveSeats(userRow-1, userCol, adult, child, senior, a2, s, 1);

                        //check if seats are available
                        if(seatIdentifier.equals("")){
                            System.out.println("Seats could not be reserved");
                            continue;
                        }
                        else{
                            //break string into two parts
                            char[] split = seatIdentifier.toCharArray();
                            //add order to customer
                            userRow = Character.getNumericValue(split[0]) + 1;
                            userCol = split[1];
                        }

                        //add order to customer
                        c.addOrder(a2, 2, userRow, userCol, adult, child, senior);

                        char tempCol = userCol;
                        while(adult > 0){
                            //access the seat arraylist in the order class
                            Customer.order.seats seat = new Customer.order.seats(userRow, tempCol, 'A');
                            c.orders.get(c.orders.size()-1).seatz.add(seat);
                            adult--;
                            tempCol++;
                        }
                        while(child > 0){
                            //access the seat arraylist in the order class
                            Customer.order.seats seat = new Customer.order.seats(userRow, tempCol, 'C');
                            c.orders.get(c.orders.size()-1).seatz.add(seat);
                            child--;
                            tempCol++;
                        }
                        while(senior > 0){
                            //access the seat arraylist in the order class
                            Customer.order.seats seat = new Customer.order.seats(userRow, tempCol, 'S');
                            c.orders.get(c.orders.size()-1).seatz.add(seat);
                            senior--;
                            tempCol++;
                        }
                    }
                    else{
                        //reserve seats
                        String seatIdentifier = a3.reserveSeats(userRow-1, userCol, adult, child, senior, a3, s, 1);

                        if(seatIdentifier.equals("")){
                            System.out.println("Seats could not be reserved");
                            continue;
                        }
                        else{
                            //break string into two parts
                            char[] split = seatIdentifier.toCharArray();
                            //add order to customer
                            userRow = Character.getNumericValue(split[0]) + 1;
                            userCol = split[1];
                        }

                        //add order to customer
                        c.addOrder(a3, 3, userRow, userCol, adult, child, senior);

                        char tempCol = userCol;
                        while(adult > 0){
                            //access the seat arraylist in the order class
                            Customer.order.seats seat = new Customer.order.seats(userRow, tempCol, 'A');
                            c.orders.get(c.orders.size()-1).seatz.add(seat);
                            adult--;
                            tempCol++;
                        }
                        while(child > 0){
                            //access the seat arraylist in the order class
                            Customer.order.seats seat = new Customer.order.seats(userRow, tempCol, 'C');
                            c.orders.get(c.orders.size()-1).seatz.add(seat);
                            child--;
                            tempCol++;
                        }
                        while(senior > 0){
                            //access the seat arraylist in the order class
                            Customer.order.seats seat = new Customer.order.seats(userRow, tempCol, 'S');
                            c.orders.get(c.orders.size()-1).seatz.add(seat);
                            senior--;
                            tempCol++;
                        }
                    }
                    
                }
                else if(choice == 2){
                    //view orders
                    //sort through the arraylist and order to find exact seats
                    //print out the seats
                    for(int i = 0; i < c.orders.size(); i++){
                        int total = c.orders.get(i).getAdultSeats() + c.orders.get(i).getChildSeats() + c.orders.get(i).getSeniorSeats();
                        System.out.print("Auditorium " + c.orders.get(i).auditoriumNumber + ", ");
                        if (!c.orders.get(i).seatz.isEmpty()) { 
                            // Check if seats list is not empty
                            // Loop through seats list
                            for(int j = 0; j < total; j++){
                                // Print out the seats
                                System.out.print(c.orders.get(i).seatz.get(j).getRow());
                                System.out.print(c.orders.get(i).seatz.get(j).getColumn());
                                if(j != total-1){
                                    // Print out a comma if not the last seat
                                    System.out.print(",");
                                }
                            }
                        }
                        // Print out the number of adult, child, and senior seats
                        System.out.println("\n" + c.orders.get(i).getAdultSeats() + " adult, " + c.orders.get(i).getChildSeats() + " child, " + c.orders.get(i).getSeniorSeats() + " senior\n");
                    }
                    if (c.orders.size() == 0){
                        System.out.println("No orders");
                    }
                }
                else if(choice == 3){
                    //update orders
                    //display orders
                    for(int i = 0; i < c.orders.size(); i++){
                        // Print out the orders
                        System.out.println("Order number: " + (i+1));
                        int total = c.orders.get(i).getAdultSeats() + c.orders.get(i).getChildSeats() + c.orders.get(i).getSeniorSeats();
                        System.out.print("Auditorium " + c.orders.get(i).auditoriumNumber + ", ");
                        // Check if seats list is not empty
                        if (!c.orders.get(i).seatz.isEmpty()) { // Check if seats list is not empty
                            for(int j = 0; j < total; j++){
                                // Print out the seats
                                System.out.print(c.orders.get(i).seatz.get(j).getRow());
                                System.out.print(c.orders.get(i).seatz.get(j).getColumn());
                                if(j != total-1){
                                    System.out.print(",");
                                }
                            }
                        }
                        // Print out the number of seats
                        System.out.println("\n" + c.orders.get(i).getAdultSeats() + " adult, " + c.orders.get(i).getChildSeats() + " child, " + c.orders.get(i).getSeniorSeats() + " senior\n");
                    }

                    //ask which order they want to update
                    System.out.println("Which order would you like to update?");
                    int orderChoice = 0;
                    if(c.orders.size() != 0){
                        do{
                            //get user input with try catch
                            try{
                                orderChoice = s.nextInt();
                            }
                            catch(InputMismatchException e){
                                s.nextLine();
                                System.out.println("Invalid input");
                            }
                            //validate user input
                            if (orderChoice < 1 || orderChoice > c.orders.size()){
                                System.out.println("Invalid order number");
                            }
                        }while (orderChoice < 1 || orderChoice > c.orders.size());
                    }
                    else{
                        //if there are no orders, print this
                        System.out.println("No orders");
                        continue;
                    }
                    //variable initializations to end the loop
                    boolean jobfinished = false;
                    do{
                        //display order
                        System.out.println("1. Add tickets to order\n2. Delete tickets from order\n3. Cancel order");
                        //get user input with try catch
                        int updateChoice = 0;
                        if (c.orders.size() != 0){
                        do{
                            //get user input with try catch
                            try{
                                updateChoice = s.nextInt();
                            }
                            catch(InputMismatchException e){
                                s.nextLine();
                                System.out.println("Invalid input");
                            }
                            //error message
                            if (updateChoice < 1 || updateChoice > 3){
                                System.out.println("Invalid choice");
                            }
                        }while (updateChoice < 1 || updateChoice > 3);
                        }

                        //only run if the orders arraylist is not empty
                        if(!c.orders.isEmpty())
                        {
                            if (updateChoice == 1){
                                //add tickets to order
                                //get user input
                                //validate user input for every input

                                //find out which auditorium the order is in
                                Auditorium audi;
                                if (c.orders.get(orderChoice-1).auditoriumNumber == 1){
                                    audi = a1;
                                }
                                else if (c.orders.get(orderChoice-1).auditoriumNumber == 2){
                                    audi = a2;
                                }
                                else{
                                    audi = a3;
                                }
                                //display auditorium
                                System.out.println(audi.toString());
                                System.out.println();
                                //try catch the row they want to reserve
                                int userRow = -1;
                                do{
                                    //get user input with try catch
                                    System.out.println("Enter the row of the seat you want to reserve: ");
                                    try{
                                        userRow = s.nextInt();
                                    }
                                    catch(InputMismatchException e){
                                        s.nextLine();
                                        System.out.println("Row must be a number");
                                    }
                                    if (userRow < 1 || userRow > maxrow1){
                                        System.out.println("Invalid row");
                                    }
                                }while (userRow < 1 || userRow > maxrow1);

                                //column validation
                                //try catch the column they want to reserve
                                char userCol = 0;
                                do{
                                    System.out.println("Enter the column of the seat you want to reserve: ");
                                    try{
                                        userCol = s.next().charAt(0);
                                    }
                                    catch(InputMismatchException e){
                                        s.nextLine();
                                        System.out.println("Column must be a letter");
                                    }
                                    if (userCol < 65 || userCol > maxcol1){
                                        System.out.println("Invalid column");
                                    }
                                }while (userCol < 65 || userCol > maxcol1);

                                //adult validation
                                //try catch the number of adult seats they want to reserve
                                int adult = -1;
                                do{
                                    System.out.println("Enter the number of adult seats you want to reserve: ");
                                    try{
                                        adult = s.nextInt();
                                    }
                                    catch(InputMismatchException e){
                                        s.nextLine();
                                        System.out.println("Number of adult seats must be a number");
                                    }
                                    if (adult < 0){
                                        System.out.println("Invalid number of adult seats");
                                    }
                                }while (adult < 0);

                                //child validation
                                //try catch the number of child seats they want to reserve
                                int child = -1;
                                do{
                                    System.out.println("Enter the number of child seats you want to reserve: ");
                                    try{
                                        child = s.nextInt();
                                    }
                                    catch(InputMismatchException e){
                                        s.nextLine();
                                        System.out.println("Number of child seats must be a number");
                                    }
                                    if (child < 0){
                                        System.out.println("Invalid number of child seats");
                                    }
                                }while (child < 0);

                                //senior
                                //try catch the number of senior seats they want to reserve
                                int senior = -1;
                                do{
                                    System.out.println("Enter the number of senior seats you want to reserve: ");
                                    try{
                                        senior = s.nextInt();
                                    }
                                    catch(InputMismatchException e){
                                        s.nextLine();
                                        System.out.println("Number of senior seats must be a number");
                                    }
                                    if (senior < 0){
                                        System.out.println("Invalid number of senior seats");
                                    }
                                }while (senior < 0);

                                //check if seats are available
                                //if seats are available, reserve them
                                
                                //reserve seats
                                String seatIdentifier = a1.reserveSeats(userRow-1, userCol, adult, child, senior, audi, s, 0);
                                jobfinished = true;

                                if(seatIdentifier.equals("-1") || seatIdentifier.equals("")){
                                    System.out.println("Seats could not be reserved");
                                    jobfinished = false;
                                    continue;
                                }
                                else{
                                    //break string into two parts
                                    char[] split = seatIdentifier.toCharArray();
                                    //add order to customer
                                    userRow = Character.getNumericValue(split[0]) + 1;
                                    userCol = split[1];
                                }

                                // add order to arraylist in seat class
                                char tempCol = userCol;
                                c.orders.get(orderChoice-1).setAdultSeats(c.orders.get(orderChoice-1).getAdultSeats() + adult);
                                c.orders.get(orderChoice-1).setChildSeats(c.orders.get(orderChoice-1).getChildSeats() + child);
                                c.orders.get(orderChoice-1).setSeniorSeats(c.orders.get(orderChoice-1).getSeniorSeats() + senior);

                                //add seats to arraylist
                                while(adult > 0){
                                    //access the seat arraylist in the order class
                                    Customer.order.seats seat = new Customer.order.seats(userRow, tempCol, 'A');
                                    c.orders.get(orderChoice-1).seatz.add(seat);
                                    adult--;
                                    tempCol++;
                                }
                                //add seats to arraylist
                                while(child > 0){
                                    //access the seat arraylist in the order class
                                    Customer.order.seats seat = new Customer.order.seats(userRow, tempCol, 'C');
                                    c.orders.get(orderChoice-1).seatz.add(seat);
                                    child--;
                                    tempCol++;
                                }
                                //add seats to arraylist
                                while(senior > 0){
                                    //access the seat arraylist in the order class
                                    Customer.order.seats seat = new Customer.order.seats(userRow, tempCol, 'S');
                                    c.orders.get(orderChoice-1).seatz.add(seat);
                                    senior--;
                                    tempCol++;
                                }
                            }
                            else if(updateChoice == 2){
                                //delete a ticket from order
                                //remove ticket from arraylist
                                //prompt user for row and column
                                //check if seat exists
                                //if it does, remove it from the arraylist
                                //if it doesn't, print error message and go back to menu screen
                                //display menu screen

                                //display order
                                int userRow = -1;
                                do{
                                    System.out.println("Enter the row of the seat you want to delete: ");
                                    //try catch the row they want to reserve
                                    try{
                                        userRow = s.nextInt();
                                    }
                                    catch(InputMismatchException e){
                                        s.nextLine();
                                        System.out.println("Row must be a number");
                                        }
                                    if (userRow < 1 || userRow > maxrow1){
                                        System.out.println("Invalid row");
                                    }
                                }while (userRow < 1 || userRow > maxrow1);

                                //column validation
                                //try catch the column they want to reserve
                                char userCol = 0;
                                do{
                                    //get user input with try catch
                                    System.out.println("Enter the column of the seat you want to delete: ");
                                    try{
                                        userCol = s.next().charAt(0);
                                    }
                                    catch(InputMismatchException e){
                                        s.nextLine();
                                        System.out.println("Column must be a letter");
                                    }
                                    if (userCol < 65 || userCol > maxcol1){
                                        System.out.println("Invalid column");
                                    }
                                }while (userCol < 65 || userCol > maxcol1);

                                //check if seat exists
                                if (verifySeat(c, c.orders.get(orderChoice-1).auditoriumNumber, userRow-1, userCol)){
                                    //remove seat from arraylist
                                    for(int i = 0; i < c.orders.size(); i++){
                                        if(c.orders.get(i).auditoriumNumber == c.orders.get(orderChoice-1).auditoriumNumber){
                                            //loop through the arraylist of seats
                                            for(int j = 0; j < c.orders.get(i).seatz.size(); j++){
                                                if(c.orders.get(i).seatz.get(j).getRow() == userRow && c.orders.get(i).seatz.get(j).getColumn() == userCol){
                                                    c.orders.get(i).seatz.remove(j);
                                                }
                                            }
                                        }
                                    }
                                    //open seat from auditorium
                                    if (c.orders.get(orderChoice-1).auditoriumNumber == 1){
                                        //open seat from auditorium
                                        char type = a1.removeTicket(userRow, userCol, a1);
                                        //decrement the number of seats in the order
                                        if (type == 'A'){
                                            c.orders.get(orderChoice-1).setAdultSeats(c.orders.get(orderChoice-1).getAdultSeats() - 1);
                                        }
                                        else if (type == 'C'){
                                            c.orders.get(orderChoice-1).setChildSeats(c.orders.get(orderChoice-1).getChildSeats() - 1);
                                        }
                                        else{
                                            c.orders.get(orderChoice-1).setSeniorSeats(c.orders.get(orderChoice-1).getSeniorSeats() - 1);
                                        }

                                        jobfinished = true;
                                    }
                                    else if (c.orders.get(orderChoice-1).auditoriumNumber == 2){
                                        //open seat from auditorium
                                        char type = a2.removeTicket(userRow, userCol, a2);
                                        //decrement the number of seats in the order
                                        if (type == 'A'){
                                            c.orders.get(orderChoice-1).setAdultSeats(c.orders.get(orderChoice-1).getAdultSeats() - 1);
                                        }
                                        else if (type == 'C'){
                                            c.orders.get(orderChoice-1).setChildSeats(c.orders.get(orderChoice-1).getChildSeats() - 1);
                                        }
                                        else if (type == 'S'){
                                            c.orders.get(orderChoice-1).setSeniorSeats(c.orders.get(orderChoice-1).getSeniorSeats() - 1);
                                        }

                                        jobfinished = true;
                                    }
                                    else{
                                        //open seat from auditorium
                                        char type = a3.removeTicket(userRow, userCol, a3);
                                        //decrement the number of seats in the order
                                        if (type == 'A'){
                                            c.orders.get(orderChoice-1).setAdultSeats(c.orders.get(orderChoice-1).getAdultSeats() - 1);
                                        }
                                        else if (type == 'C'){
                                            c.orders.get(orderChoice-1).setChildSeats(c.orders.get(orderChoice-1).getChildSeats() - 1);
                                        }
                                        else if (type == 'S'){
                                            c.orders.get(orderChoice-1).setSeniorSeats(c.orders.get(orderChoice-1).getSeniorSeats() - 1);
                                        }
                                        //set jobfinished to true
                                        jobfinished = true;
                                    }
                                    //if the seats arraylist is empty job is finished
                                    jobfinished = true;
                                }
                                else{
                                    //print error message
                                    System.out.println("Seat does not exist");
                                    jobfinished = false;
                                }
                                //if the seats arraylist is empty, delete the order
                                if(c.orders.get(orderChoice-1).seatz.isEmpty()){
                                    c.orders.remove(orderChoice-1);
                                }
                        }
                        if(updateChoice == 3){
                            //cancel order
                            //get auditorium number
                            int audinum = c.orders.get(orderChoice-1).auditoriumNumber;
                            
                            //for every row and column in the order, open the seat
                            for(int i = 0; i < c.orders.get(orderChoice-1).seatz.size(); i++){
                                //open seat from auditorium
                                if (audinum == 1){
                                    a1.removeTicket(c.orders.get(orderChoice-1).seatz.get(i).getRow(), c.orders.get(orderChoice-1).seatz.get(i).getColumn(), a1);
                                }
                                else if (audinum == 2){
                                    a2.removeTicket(c.orders.get(orderChoice-1).seatz.get(i).getRow(), c.orders.get(orderChoice-1).seatz.get(i).getColumn(), a2);
                                }
                                else{
                                    a3.removeTicket(c.orders.get(orderChoice-1).seatz.get(i).getRow(), c.orders.get(orderChoice-1).seatz.get(i).getColumn(), a3);
                                }
                            }

                            //delete the order from the arraylist
                            try{
                                c.orders.remove(orderChoice-1);
                                jobfinished = true;
                            }
                            catch(IndexOutOfBoundsException e){
                                System.out.println("caught");
                            }
                            //c.orders.remove(orderChoice-1);
                        
                        }
                
                    }
                    }while(!jobfinished);

                    if(c.orders.size() == 0){
                        //if there are no orders, print this
                        System.out.println("No orders");
                    }
            }
                else if(choice == 4){
                    //display receipt
                    double total1Res = 0;
                    double totalAllRes = 0;
                    //loop through the arraylist of orders
                    for(int i = 0; i < c.orders.size(); i++){
                        // Print out the orders
                        int total = c.orders.get(i).getAdultSeats() + c.orders.get(i).getChildSeats() + c.orders.get(i).getSeniorSeats();
                        System.out.print("Auditorium " + c.orders.get(i).auditoriumNumber + ", ");
                        // Check if seats list is not empty
                        if (!c.orders.get(i).seatz.isEmpty()) { // Check if seats list is not empty
                            //loop through the arraylist of seats
                            for(int j = 0; j < total; j++){
                                // Print out the seats
                                System.out.print(c.orders.get(i).seatz.get(j).getRow());
                                System.out.print(c.orders.get(i).seatz.get(j).getColumn());
                                // Print out a comma if not the last seat
                                if(j != total-1){
                                    System.out.print(",");
                                }
                            }
                        }
                        // Print out the number of adult, child, and senior seats
                        System.out.println("\n" + c.orders.get(i).getAdultSeats() + " adult, " + c.orders.get(i).getChildSeats() + " child, " + c.orders.get(i).getSeniorSeats() + " senior");
                        // Print out the total cost of the order
                        total1Res = c.orders.get(i).getAdultSeats()*10 + c.orders.get(i).getChildSeats()*5 + c.orders.get(i).getSeniorSeats()*7.5;
                        // Print out the total cost of the order
                        totalAllRes += total1Res;
                        // Print out the total cost of the order
                        System.out.printf("Order Total: $%.2f\n", total1Res);
                    }
                    // Print out the total cost of the order
                    System.out.printf("Customer Total: $%.2f\n\n", totalAllRes);
                }
                else if(choice == 5){
                    //log out
                    //basically do nothing
                }


                }while(choice != 5);
            }
        //if admin exited break loop end program
        }while(!adminExit);
        s.close();
    }

}