public class steerage extends tickets {
    private boolean pickedCargo;

    // Constructor
    public steerage(boolean isSitting, int points, boolean pickedCargo) {
        super(false, points, false, false, 0); 
        this.pickedCargo = pickedCargo;
    }
    public steerage(boolean isSitting, int points, boolean pickedCargo, boolean isThrillSeeker, boolean isWingman) {
        super(false, points, isThrillSeeker, isWingman, 0); 
        this.pickedCargo = pickedCargo;
    }
    // Default constructor
    public steerage() {
        super();
        this.pickedCargo = false;
    }

    // Getters and setters
    public boolean isPickedCargo() {
        return pickedCargo;
    }
    public void setPickedCargo(boolean pickedCargo) {
        this.pickedCargo = pickedCargo;
    }

    @Override
    public void setPoints() {
        // Implement logic to set points for steerage
        //steerage is the lowest class, so it gets the least points
        int pointss = 10;
        if (isPickedCargo()) {
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

    @Override
    public String toString() {
        //build string
        StringBuilder sb = new StringBuilder();
        sb.append("______________________________\nSteerage Class: \n");
        sb.append("this ticket is located in the cargo hold\n");
        if(isPickedCargo()) {
            sb.append("Sit on Cargo Upgrade applied\n");
        } 
        if(isThrillSeeker()) {
            sb.append("this ticket is a thrill seeker: Booked ticket in a Boeing 737 Max\n");
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