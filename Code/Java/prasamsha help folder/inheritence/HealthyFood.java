public class HealthyFood extends Food {
    private String foodGroup;
    private String foodColor;

    public HealthyFood(String foodName, int calories, String foodGroup, String foodColor) {
        super(foodName, calories);
        this.foodGroup = foodGroup;
        this.foodColor = foodColor;
    }
    
    public HealthyFood(String foodName, int calories, String foodColor) {
        super(foodName, calories);
        System.out.print("overloaded");
        this.foodColor = foodColor;
    }

    public String getGroup() {
        return foodGroup;
    }

    public String getColor() {
        return foodColor;
    }
}