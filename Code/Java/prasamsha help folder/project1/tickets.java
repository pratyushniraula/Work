public abstract class tickets implements Comparable<tickets> {
    protected boolean isSitting;
    protected int points;
    protected boolean thrillSeeker;
    protected boolean Wingman;
    protected int WingmanWingPick; //if 0 wing not picked, if 1 wing is left, if 2 wing is right
    protected int bathroomPasses;
    
    //getters and setters
    public boolean isSitting() {
        return isSitting;
    }
    public void setSitting(boolean sitting) {
        isSitting = sitting;
    }
    public int getPoints() {
        return points;
    }

    //abstract force the child classes to implement
    public abstract void setPoints();

    public boolean isThrillSeeker() {
        return thrillSeeker;
    }
    public void setThrillSeeker(boolean thrillSeeker) {
        this.thrillSeeker = thrillSeeker;
    }
    public boolean isWingman() {
        return Wingman;
    }
    public void setWingman(boolean wingman) {
        Wingman = wingman;
    }
    public int getWingmanWingPick() {
        return WingmanWingPick;
    }
    public void setWingmanWingPick(int wingmanWingPick) {
        this.WingmanWingPick = wingmanWingPick;
    }

    // Constructor
    public tickets(boolean isSitting, int points) {
        this.isSitting = isSitting;
        this.points = points;
    }

    // Constructor with thrillSeeker and Wingman
    public tickets(boolean isSitting, int points, boolean thrillSeeker, boolean Wingman, int bathroomPasses) {
        this.isSitting = isSitting;
        this.points = points;
        this.thrillSeeker = thrillSeeker;
        this.Wingman = Wingman;
        this.bathroomPasses = bathroomPasses;
    }

    // Default constructor
    public tickets() {
        this.isSitting = false;
        this.points = 0;
        this.thrillSeeker = false;
        this.Wingman = false;
        this.bathroomPasses = 0;
    }

    @Override
    public int compareTo(tickets other) {
        // Compare based on points
        return Integer.compare(this.points, other.points);
    }
}