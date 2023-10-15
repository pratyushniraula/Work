// package RewardsSystem;
//Pratyush Niraula
//pxn210022

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
    @Override
    public double getDiscount() {
        return discountPercent;
    }
    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }


}