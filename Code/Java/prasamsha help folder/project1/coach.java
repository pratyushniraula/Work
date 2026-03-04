public class coach extends tickets {
    //standing the entire time
    private boolean handRail;
    private boolean carryOnBaggageAdditionalFee;

    // Constructor
    public coach(boolean isSitting, int points, boolean handRail, boolean carryOnBaggageAdditionalFee) {
        super(isSitting, points);
        this.handRail = handRail;
        this.carryOnBaggageAdditionalFee = carryOnBaggageAdditionalFee;
    }
    public coach(boolean isSitting, int points, boolean handRail, boolean carryOnBaggageAdditionalFee, boolean isThrillSeeker, boolean isWingman) {
        super(isSitting, points, isThrillSeeker, isWingman, 0); // 0 bathroom passes for coach class
        this.handRail = handRail;
        this.carryOnBaggageAdditionalFee = carryOnBaggageAdditionalFee;
    }
    // Default constructor
    public coach() {
        super();
        this.handRail = false;
        this.carryOnBaggageAdditionalFee = false;
    }
    // Getters and setters
    public boolean isHandRail() {
        return handRail;
    }
    public void setHandRail(boolean handRail) {
        this.handRail = handRail;
    }
    public boolean isCarryOnBaggageAdditionalFee() {
        return carryOnBaggageAdditionalFee;
    }
    public void setCarryOnBaggageAdditionalFee(boolean carryOnBaggageAdditionalFee) {
        this.carryOnBaggageAdditionalFee = carryOnBaggageAdditionalFee;
    }

    @Override
    public void setPoints() {
        // Implement logic to set points for coach class
        //coach class is the lowest class, so it gets the least points
        int pointss = 50;
        if (isHandRail()) {
            pointss += 1; // Add points if handrail is used
        }
        if(isCarryOnBaggageAdditionalFee()) {
            pointss += 1; // Add points if carry-on baggage is allowed
        }
        if(getWingmanWingPick() != 0) {
            pointss += 1; // Add points if wingman picks a wing
        }
        if(isThrillSeeker()) {
            pointss += 1; // Add points if thrill seeker
        }
        if(isWingman()) {
            pointss += 1; // Add points if wingman
        }
        this.points = pointss;
    }

    @Override
    public String toString() {
        //build string
        StringBuilder sb = new StringBuilder();
        sb.append("______________________________\nCoach Class: \n");
        sb.append("this ticket is located in the coach class! You will be standing\n");
        if(isHandRail()) {
            sb.append("You have added the option of a handrail to hold on to\n");
        }
        if(isCarryOnBaggageAdditionalFee()) {
            sb.append("You have allowed for the option of containing a carry-on bag!\n");
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