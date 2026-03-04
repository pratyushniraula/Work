import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        ticketManipulator manipulator = new ticketManipulator(s);
        ticketTracker tracker = new ticketTracker();
        int choice = 0;

        System.out.println("Welcome to Ghost Airlines Ticket Tracker!");
        System.out.println("With our advanced ticket booker we use numbers in order to navigate our software\nPlease select an option:");
        while (choice != 3) {
            System.out.println("\n\n1. Add a ticket");
            System.out.println("2. View all tickets");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            try {
                choice = s.nextInt();
            } catch (Exception e) {
                System.out.println("\nInvalid input. Please enter a number.\n");
                s.next(); // Clear the invalid input
                continue; // Skip to the next iteration
            }

            switch (choice) {
                case 1:
                    manipulator.addTicket(tracker);
                    break;
                case 2:
                    manipulator.viewTickets(tracker.getTicketsList());
                    break;
                case 3:
                    System.out.println("Thanks for Using Ghost!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

        s.close();
    }
}