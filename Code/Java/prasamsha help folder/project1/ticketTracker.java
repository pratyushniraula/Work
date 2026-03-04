import java.util.ArrayList;

public class ticketTracker {
    private ArrayList<tickets> ticketsList = new ArrayList<>();
    private int totalTickets;

    // Constructor
    public ticketTracker() {
        this.totalTickets = 0;
    }
    // getters and setters
    public ArrayList<tickets> getTicketsList() {
        return ticketsList;
    }
    public void setTicketsList(ArrayList<tickets> ticketsList) {
        this.ticketsList = ticketsList;
    }

    public int getTotalTickets() {
        return totalTickets;
    }
    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

     //append a ticket to the list
    public void addTicket(tickets ticket) {
        ticketsList.add(ticket);
        totalTickets++;
    }

}