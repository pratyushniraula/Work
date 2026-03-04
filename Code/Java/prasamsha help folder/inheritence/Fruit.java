public class Fruit extends HealthyFood {
    private boolean isInSeason;
    private String majorVitamins;

    public Fruit(String foodName, int calories, String foodColor, boolean isInSeason, String majorVitamins) {
        super(foodName, calories, foodColor);
        this.isInSeason = isInSeason;
        this.majorVitamins = majorVitamins;
    }

    public boolean isInSeason() {
        return isInSeason;
    }

    public String getVitamins() {
        return majorVitamins;
    }
}