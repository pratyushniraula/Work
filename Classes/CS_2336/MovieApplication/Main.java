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
        //open auditorium files



        boolean adminExit = false;
        // Customer m = new Customer();
        // Customer name = new Customer();
        // Customer pass = new Customer();
        // Customer.order o = new Customer.order();
        HashMap<String, Customer> custList;
        custList = readFromFile("userdb.dat");
        Scanner s = new Scanner(System.in);
        String username = "";

        do{
            boolean login = false;
            pr:
            while (true) {
                
                System.out.println("Enter your username: ");
                username = s.nextLine();
                if (custList.containsKey(username)) {
                    Customer c = custList.get(username);
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
            
        }while(!adminExit);

        
    }
    
    


}