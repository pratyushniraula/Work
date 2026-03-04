public class business extends tickets {
    private int seat; // seat=0 means middle seat, seat=1 means aisle seat, seat=2 means window seat
    private boolean overheadBin;
    public static final boolean carryOnBaggage = true;

    // Constructor
    public business(boolean isSitting, int points, int seat, boolean overheadBin) {
        super(isSitting, points);
        this.seat = seat;
        this.overheadBin = overheadBin;
    }
    public business(boolean isSitting, int points, int seat, boolean overheadBin, boolean isThrillSeeker, boolean isWingman) {
        super(isSitting, points, isThrillSeeker, isWingman, 1); // 1 bathroom pass for business class
        this.seat = seat;
        this.overheadBin = overheadBin;
    }

    // Default constructor
    public business() {
        super();
        this.seat = 0;
        this.overheadBin = false;
    }


    // Getters and setters
    public int getSeat() {
        return seat;
    }
    public void setSeat(int seat) {
        this.seat = seat;
    }
    public boolean isOverheadBin() {
        return overheadBin;
    }
    public void setOverheadBin(boolean overheadBin) {
        this.overheadBin = overheadBin;
    }


    @Override
    public void setPoints() {
        // Implement logic to set points for business class
        //business class is the middle class, so it gets the middle points
        int pointss = 100;
        if (isOverheadBin()) {
            pointss += 1; // Add points if overhead bin is used
        }
        if(getSeat() != 0) {
            pointss += 1; // Add points if seat is not middle
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
    
    @Override
    public String toString() {
        //build string
        StringBuilder sb = new StringBuilder();
        sb.append("______________________________\nBusiness Class: \n");
        sb.append("this ticket is located in the business class\n");
        if(isOverheadBin()) {
            sb.append("this ticket has an overhead bin\n");
        }
        sb.append("this tiket comes with a complimentary carry-on baggage addition\n");
        sb.append("this ticket comes with a");
        if(getSeat() == 0) {
            sb.append(" middle seat included\n");
        } else if(getSeat() == 1) {
            sb.append("n aisle seat option\n");
        } else if(getSeat() == 2) {
            sb.append(" window seat option\n");
        }
        sb.append("and this ticket has 1 bathroom pass!\n");
        
        return sb.toString();
    }
}