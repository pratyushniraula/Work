package RewardsSystem;

public class Platinum extends Customer{
    private int bonusBucks;

    public Platinum(){

    }
    public Platinum(String first, String last, String id, double spent, int bonusBucks){
        super(first, last, id, spent);
        this.bonusBucks = bonusBucks;
    }
    
}