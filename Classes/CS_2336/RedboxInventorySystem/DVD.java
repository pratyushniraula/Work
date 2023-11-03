//Pratyush Niraula
//pxn210033
//package RedboxInventorySystem;

public class DVD implements Comparable<DVD>{
    protected String title;
    private int available;
    private int rented;

    public DVD() {
        this.title = "";
        this.available = 0;
        this.rented = 0;
    }
    public DVD(String title, int available, int rentable) {
        this.title = title;
        this.available = available;
        this.rented = rentable;
    }
    public DVD(DVD dvd) {
        this.title = dvd.title;
        this.available = dvd.available;
        this.rented = dvd.rented;
    }

    public DVD (String title){
        this.title = title;
        this.available = 0;
        this.rented = 0;
    }

    //getters
    public String getTitle() {
        return this.title;
    }
    public int getAvailable() {
        return this.available;
    }
    public int getRented() {
        return this.rented;
    }

    //setters
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAvailable(int available) {
        this.available = available;
    }
    public void setRented(int rentable) {
        this.rented = rentable;
    }
    public void setDVD (DVD dvd){
        this.title = dvd.title;
        this.available = dvd.available;
        this.rented = dvd.rented;
    }

    //compareTo method
    @Override
    public int compareTo(DVD dvd) {
        return this.title.compareTo(dvd.getTitle());
    }
    // @Override
    // public String toString() {
    //     return String.format(this.title + "\t" + this.available + "\t" + this.rentable);
    // }
   
    //toString method
    @Override
    public String toString() {
        return String.format("%-30s %-10d%-10d", title, available, rented);
    }

}