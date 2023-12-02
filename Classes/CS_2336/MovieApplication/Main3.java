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
public class Main3{
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
            private int auditoriumNumber;
            private int row;
            private char startingColumn;
            private int adultSeats;
            private int childSeats;
            private int seniorSeats;
            private ArrayList <seats> seats = new ArrayList<seats>();
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
            public order(){
                this.auditorium = null;
                this.row = 0;
                this.startingColumn = 'A';
                this.adultSeats = 0;
                this.childSeats = 0;
                this.seniorSeats = 0;
            }
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
                return this.seats;
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
            public void setSeats(ArrayList<seats> seats){
                this.seats = seats;
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
        public void addOrder(Auditorium a, int audnum,  int r, char col, int adult, int child, int senior){
            order o = new order(a, audnum, r, col, adult, child, senior);
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
        System.out.println("Enter the name of the file: ");
        //String filename = s.nextLine();
        custList = readFromFile("userdb.dat");
        String username = "";
        
        ////////////////////////////////////////////////////////////////////////
        try{
            System.out.println("Enter the name of the file for auditorium 1: ");
            //filename = s.nextLine();
            File file1 = new File("A1.txt");
            System.out.println("Enter the name of the file for auditorium 2: ");
            //filename = s.nextLine();
            File file2 = new File("A2.txt");
            System.out.println("Enter the name of the file for auditorium 3: ");
            //filename = s.nextLine();
            File file3 = new File("A3.txt");
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
                maxcol1 = tempCol;
            }
            maxrow1 = tempRow;
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
                maxcol2 = tempCol;
            }
            maxrow2 = tempRow;
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
            if (c.getUsername().equals("admin") && login){
                int choice = 0;
                do{
                    System.out.println("1. Print Report\n2. Log Out\n3. Exit");

                    try{
                        choice = s.nextInt();
                    }
                    catch(InputMismatchException e){
                        s.nextLine();
                        System.out.println("Invalid input");
                    }

                    if (choice == 1){
                        a1.auditoriumReport(a1,a2,a3);
                    }
                    else if (choice == 2){
                        //log out
                        //basically do nothing
                    }
                    else if (choice == 3){
                        adminExit = true;
                        //load data of auditoriums into a1 a2 and a3 files
                        //save data of auditoriums into a1 a2 and a3 files
                        a1.fileMaker(a1, 1);
                        a2.fileMaker(a2, 2);
                        a3.fileMaker(a3, 3);

                    }
                }while (choice != 2 && choice != 3);
            }

            else if(c != null){
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

                    if(choice == 1){
                    System.out.println("What auditorium would you like to reserve in?");
                    System.out.println("1. Auditorium 1\n2. Auditorium 2\n3. Auditorium 3");
                    int audichoice = 0;
                    do{
                        try{
                            audichoice = s.nextInt();
                        }
                        catch(InputMismatchException e){
                            s.nextLine();
                            System.out.println("Invalid input");
                        }
                        if (audichoice != 1 && audichoice != 2 && audichoice != 3){
                            System.out.println("Invalid input, try again: ");
                        }

                    }while (audichoice != 1 && audichoice != 2 && audichoice != 3);

                        Auditorium audi;
                        //get the auditorium
                        if (audichoice == 1){
                            audi = a1;
                            System.out.println("Auditorium 1 was selected!");
                        }
                        else if (audichoice == 2){
                            audi = a2;
                            System.out.println("Auditorium 2 was selected!");
                        }
                        else{
                            audi = a3;
                            System.out.println("Auditorium 3 was selected!");
                        }


                    int adult = -1;
                    int child = -1;
                    int senior = -1;
                    //reserve seats
                    //print auditorium
                    // auditorium.printAuditorium(tempCol-65);
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
                        String seatIdentifier = a1.reserveSeats(userRow-1, userCol, adult, child, senior, a1, s);
                        
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
                    }
                    else if (audichoice == 2){
                        //reserve seats
                        String seatIdentifier = a2.reserveSeats(userRow-1, userCol, adult, child, senior, a2, s);

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
                    }
                    else{
                        //reserve seats
                        String seatIdentifier = a3.reserveSeats(userRow-1, userCol, adult, child, senior, a3, s);

                        if(seatIdentifier.equals("")){
                            System.out.println("Seats could not be reserved");
                            continue;
                        }
                        else{
                            System.out.println("seatIdentifier: " + seatIdentifier);
                            //break string into two parts
                            char[] split = seatIdentifier.toCharArray();
                            //add order to customer
                            userRow = Character.getNumericValue(split[0]) + 1;
                            userCol = split[1];
                        }

                        //add order to customer
                        c.addOrder(a3, 3, userRow, userCol, adult, child, senior);
                    }
                    
                }
                else if(choice == 2){
                    //view orders
                    for(int i = 0; i < c.orders.size(); i++){
                        int total = c.orders.get(i).getAdultSeats() + c.orders.get(i).getChildSeats() + c.orders.get(i).getSeniorSeats();
                        System.out.print("Auditorium " + c.orders.get(i).auditoriumNumber + ", ");
                        for(int j = 0; j < total; j++){
                            System.out.print(c.orders.get(i).row);
                            System.out.print((char)(c.orders.get(i).startingColumn + j));
                            if(j != total-1){
                                System.out.print(",");
                            }
                        }
                        System.out.println("\n" + c.orders.get(i).getAdultSeats() + " adult, " + c.orders.get(i).getChildSeats() + " child, " + c.orders.get(i).getSeniorSeats() + " senior\n");
                    }
                }
                else if(choice == 3){
                    //update orders
                    System.out.println("1. Add tickets to order\n2. Delete tickets from order\n3. Cancel order");
                    int updateChoice = s.nextInt();

                }
                else if(choice == 4){
                    //display receipt
                    double total1Res = 0;
                    double totalAllRes = 0;
                    for(int i = 0; i < c.orders.size(); i++){
                        int total = c.orders.get(i).getAdultSeats() + c.orders.get(i).getChildSeats() + c.orders.get(i).getSeniorSeats();
                        System.out.print("Auditorium " + c.orders.get(i).auditoriumNumber + ", ");
                        for(int j = 0; j < total; j++){
                            System.out.print(c.orders.get(i).row);
                            System.out.print((char)(c.orders.get(i).startingColumn + j));
                            if(j != total-1){
                                System.out.print(",");
                            }
                        }
                        System.out.println("\n" + c.orders.get(i).getAdultSeats() + " adult, " + c.orders.get(i).getChildSeats() + " child, " + c.orders.get(i).getSeniorSeats() + " senior\n");
                        total1Res = c.orders.get(i).getAdultSeats()*10 + c.orders.get(i).getChildSeats()*5 + c.orders.get(i).getSeniorSeats()*7.5;
                        totalAllRes += total1Res;
                        //print total up to 2 decimal places
                        System.out.printf("Order Total: $%.2f\n", total1Res);
                    }
                    System.out.printf("\nTotal for all reservations: $%.2f\n", totalAllRes);
                }
                else if(choice == 5){
                    //log out
                    //basically do nothing
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