package RewardsSystem;

public class Gold extends Customer{
    private double discountPercent;

    public Gold(){
        super();
    }

    public Gold(String first, String last, String id, double spent, double discount){
        super(first, last, id, spent);
        this.discountPercent = discount;
    }

    //getters and setters
    public double getDiscountPercent() {
        return discountPercent;
    }
    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

}