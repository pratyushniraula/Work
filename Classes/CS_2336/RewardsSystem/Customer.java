package RewardsSystem;

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
    public void setMoneySpent(double moneySpent) {
        this.moneySpent = moneySpent;
    }


}