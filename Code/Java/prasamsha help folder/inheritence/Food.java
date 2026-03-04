public class Food
{
    private String foodName;
    private int calories;
    
    public Food(String foodName, int calories)
    {
        this.foodName = foodName;
        this.calories = calories;
    }
    
    public String getName()
    {
        return foodName;
    }
    
    public int getCalories()
    {
        return calories;
    }
    public String toString()
    {
        return foodName + " has " + calories + " caloires";
    }
    
}