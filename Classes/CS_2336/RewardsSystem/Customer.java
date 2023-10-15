// package RewardsSystem;
//Pratyush Niraula
//pxn210022

public class Customer{
    protected String firstName;
    protected String lastName;
    protected String GuestID;
    protected double moneySpent;

    public Customer(){

    }
    public Customer(String first, String last, String guestid, double spent){
        this.firstName = first;
        this.lastName = last;
        this.GuestID = guestid;
        this.moneySpent = spent;
    }

    //getters and setters
    public String getFirstName() {
        return firstName;
    }
    public String getGuestID() {
        return GuestID;
    }   
    public String getLastName() {
        return lastName;
    }   
    public double getMoneySpent() {
        return moneySpent;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setGuestID(String guestID) {
        GuestID = guestID;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setMoneySpent(double moneySpent2) {
        this.moneySpent = moneySpent2;
    }

    //only way to access or not access specific discount as parent cannot use child methods but can use overriden child method
    public double getDiscount(){
        return 0;
    }


}