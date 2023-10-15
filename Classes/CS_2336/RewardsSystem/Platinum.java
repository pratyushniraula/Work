// package RewardsSystem;
//Pratyush Niraula
//pxn210022
public class Platinum extends Customer{
    private int bonusBucks;

    public Platinum(){

    }
    public Platinum(String first, String last, String id, double spent, int bonusBucks){
        super(first, last, id, spent);
        this.bonusBucks = bonusBucks;
    }
    
    //getters and setters
    @Override
    //make sure to caste back into int in the main method
    public double getDiscount() {
        return bonusBucks;
    }
    public void setBonusBucks(int bonusBucks) {
        this.bonusBucks = bonusBucks;
    }
}