public class first extends tickets {
    private static final boolean ComplementaryFlightInstruments = true;
    private static final boolean ComplementaryBeerAndWine = true;
    private boolean YokeAndRudder;

    //getters and setters
    public boolean isYokeAndRudder() {
        return YokeAndRudder;
    }
    public void setYokeAndRudder(boolean yokeAndRudder) {
        YokeAndRudder = yokeAndRudder;
    }
    public static boolean isComplementaryFlightInstruments() {
        return ComplementaryFlightInstruments;
    }
    public static boolean isComplementaryBeerAndWine() {
        return ComplementaryBeerAndWine;
    }

    // Constructor
    public void setPoints() {
        // Implement logic to set points for first class
        //first class is the highest class, so it gets the most points
        int pointss = 1000;
        if (YokeAndRudder) {
            pointss += 1; // Add points if cargo is picked
        }
        if(isThrillSeeker()) {
            pointss += 1; // Add points if thrill seeker
        }
        if(isWingman()) {
            pointss += 1; // Add points if wingman
        }
        if(getWingmanWingPick() != 0) {
            pointss += 1; // Add points if wingman picks a wing
        }
        this.points = pointss;
    }
    // Constructor
    public first(boolean isSitting, int points, boolean YokeAndRudder) {
        super(isSitting, points);
        this.YokeAndRudder = YokeAndRudder;
    }
    public first(boolean isSitting, int points, boolean YokeAndRudder, boolean isThrillSeeker, boolean isWingman) {
        super(isSitting, points, isThrillSeeker, isWingman, 2); // 2 bathroom passes for first class
        this.YokeAndRudder = YokeAndRudder;
    }
    // Default constructor
    public first() {
        super();
        this.YokeAndRudder = false;
    }

    @Override
    public String toString() {
        //build string
        StringBuilder sb = new StringBuilder();
        sb.append("______________________________\nFirst Class: \n");
        sb.append("this ticket is located in the cockpit\n");
        sb.append("You have complementary flight instruments!\n");
        sb.append("You have complementary beer and wine!\n");
        if(isYokeAndRudder()) {
            sb.append("You have added the option to include a yoke and rudder!\n");
        }
        if(isThrillSeeker()) {
            sb.append("You are a thrill seeker!\n");
        }
        if(isWingman()) {
            sb.append("this ticket is a wingman: Booked ticket in ");
        }
        if(isWingman() && WingmanWingPick != 0) {
            if(WingmanWingPick == 1) {
                sb.append("the left wing (wing choice upgrade)\n");
            } 
            else if (WingmanWingPick == 2) {
                sb.append("the right wing (wing choice upgrade)\n");
            }
            else {
                sb.append("a wing\n");
            }
        }
        return sb.toString();
    }
}