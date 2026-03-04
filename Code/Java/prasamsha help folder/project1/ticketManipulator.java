import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

//class to manage tickets for the airline
public class ticketManipulator {
    private int totalTickets;
    Scanner s;

    //constructor
    public ticketManipulator(Scanner scanner) {
        this.s = scanner;
    }

    //method to add a ticket
    public void addTicket(ticketTracker ticketer){
        System.out.println("Adding a ticket...");
        int choice = 0;
        boolean isThrillSeeker = false;
        boolean isWingman = false;
        int pickedWing = 0;
        // Display the list of tickets
        System.out.println("Please select a class:");
        System.out.println("1. Steerage");
        System.out.println("2. Coach");
        System.out.println("3. Business");
        System.out.println("4. First");
        System.out.print("Enter your choice: ");
        try {
            choice = s.nextInt();
        } catch (Exception e) {
            System.out.println("\nInvalid input. Please enter a number.\n");
            s.next(); // Clear the invalid input
            return; // Skip to the next iteration
        }

        switch (choice) {
            case 1:
                //variable initializtion
                boolean pickedCargo = false;

                // Create a steerage ticket
                System.out.println("We're almost done! Please answer the following questions:");
                System.out.print("Do you want the option to sit on the cargo?\n1: yes\n2: no \nenter your choice: ");
                choice = 0;
                try {
                    choice = s.nextInt();
                } catch (Exception e) {
                    System.out.println("\nInvalid input. Please try again.\n");
                    s.next(); // Clear the invalid input
                    return; // Skip to the next iteration
                }
                if (choice == 1) {
                    // Ask if the user wants to pick cargo
                    pickedCargo = true;
                } else if (choice == 2) {
                    // No cargo option
                    pickedCargo = false;
                } else {
                    System.out.println("Invalid choice. Please try booking again.");
                    return; // Skip to the next iteration
                }
                
            
                System.out.print("Is the ticket a thrill seeker?\n1: yes\n2: no \nenter your choice: ");
                choice = 0;
                try {
                    choice = s.nextInt();
                } catch (Exception e) {
                    System.out.println("\nInvalid input. Please try booking again.\n");
                    s.next(); // Clear the invalid input
                    return; // Skip to the next iteration
                }
                if(choice == 1) {
                    // Ask if the user wants to pick cargo
                    isThrillSeeker = true;
                } else if (choice == 2) {
                    // No cargo option
                    isThrillSeeker = false;
                } else {
                    System.out.println("Invalid choice. Please try booking again.\n");
                    return; // Skip to the next iteration
                }

                System.out.print("Is the ticket a wingman?\n1: yes\n2: no \nenter your choice: ");
                choice = 0;
                try {
                    choice = s.nextInt();
                } catch (Exception e) {
                    System.out.println("\nInvalid input. Please try booking again.\n");
                    s.next(); // Clear the invalid input
                    return; // Skip to the next iteration
                }
                if(choice == 1) {
                    // Ask if the user wants to pick cargo
                    isWingman = true;
                    choice = 0;
                    System.out.print("Is the ticket a wingman? (1: left wing, 2: right wing): ");
                    try {
                        choice = s.nextInt();
                    } catch (Exception e) {
                        System.out.println("\nInvalid input. Please try booking again.\n");
                        s.next(); // Clear the invalid input
                        return; // Skip to the next iteration
                    }
                    if (choice == 1) {
                        // Ask if the user wants to pick cargo
                        pickedWing = 1;
                    } else if (choice == 2) {
                        // No cargo option
                        pickedWing = 2;
                    } else {
                        System.out.println("Invalid choice. Please try booking again.\n");
                        return; // Skip to the next iteration
                    }
                } else if (choice == 2) {
                    // No cargo option
                    isWingman = false;
                } else {
                    System.out.println("Invalid choice. Please try booking again.\n");
                    return; // Skip to the next iteration
                }
                // Create the ticket object
                steerage ticket = new steerage(false, 0, pickedCargo, isThrillSeeker, isWingman);
                // Set the wingman pick
                ticket.setWingmanWingPick(pickedWing);
                // Set the points for the ticket
                ticket.setPoints();
                // Print the ticket details
                System.out.println("Ticket created: " + ticket);
                //add ticket to list
                System.out.println("Ticket added to the list.");
                ticketer.addTicket(ticket);
                break;

            case 2: // coach ticket
                //variable initializtion
                boolean handRail = false;
                boolean carryOnBaggageAdditionalFee = false;

                // Create a coach ticket
                System.out.println("We're almost done! Please answer the following questions:");
                System.out.print("Do you want the option to use a handrail?\n1: yes\n2: no \nenter your choice: ");
                choice = 0;
                try {
                    choice = s.nextInt();
                } catch (Exception e) {
                    System.out.println("\nInvalid input. Please try booking again.\n");
                    s.next(); // Clear the invalid input
                    return; // Skip to the next iteration
                }
                if (choice == 1) {
                    // Ask if the user wants to pick cargo
                    handRail = true;
                } else if (choice == 2) {
                    // No cargo option
                    handRail = false;
                } else {
                    System.out.println("Invalid choice. Please try booking again.");
                    return; // Skip to the next iteration
                }
                System.out.print("Do you want the option to carry on baggage?\n1: yes\n2: no \nenter your choice: ");
                choice = 0;
                try {
                    choice = s.nextInt();
                } catch (Exception e) {
                    System.out.println("\nInvalid input. Please try booking again.\n");
                    s.next(); // Clear the invalid input
                    return; // Skip to the next iteration
                }
                if (choice == 1) {
                    // Ask if the user wants to pick cargo
                    carryOnBaggageAdditionalFee = true;
                } else if (choice == 2) {
                    // No cargo option
                    carryOnBaggageAdditionalFee = false;
                } else {
                    System.out.println("Invalid choice. Please try booking again.\n");
                    return; // Skip to the next iteration
                }
                System.out.print("Is the ticket a thrill seeker?\n1: yes\n2: no \nenter your choice: ");
                choice = 0;
                try {
                    choice = s.nextInt();
                }
                catch (Exception e) {
                    System.out.println("\nInvalid input. Please try booking again.\n");
                    s.next(); // Clear the invalid input
                    return; // Skip to the next iteration
                }
                if(choice == 1) {
                    // Ask if the user wants to pick cargo
                    isThrillSeeker = true;
                } 
                else if (choice == 2) {
                    // No cargo option
                    isThrillSeeker = false;
                } 
                else {
                    System.out.println("\nInvalid choice. Please try booking again.\n");
                    return; // Skip to the next iteration
                }

                System.out.print("Is the ticket a wingman?\n1: yes\n2: no \nenter your choice: ");
                choice = 0;
                try {
                    choice = s.nextInt();
                } catch (Exception e) {
                    System.out.println("\nInvalid input. Please try booking again.\n");
                    s.next(); // Clear the invalid input
                    return; // Skip to the next iteration
                }
                if(choice == 1) {
                    isWingman = true;
                    choice = 0;
                    System.out.println("would you like to pick a wing?\n1: yes\n2: no\nenter your choice: ");
                    if(choice == 1) {
                        System.out.print("pick your wing? (1: left wing, 2: right wing): ");
                        try {
                            choice = s.nextInt();
                        } catch (Exception e) {
                            System.out.println("\nInvalid input. Please try booking again.\n");
                            s.next(); // Clear the invalid input
                            return; // Skip to the next iteration
                        }
                        if (choice == 1) {
                            pickedWing = 1;
                        } 
                        else if (choice == 2) {
                            // No cargo option
                            pickedWing = 2;
                        } 
                        else {
                            System.out.println("\nInvalid choice. Please try booking again.\n");
                            return; // Skip to the next iteration
                        }
                    } 
                    else if (choice == 2) {
                        pickedWing = 0;
                    } 
                    else {
                        System.out.println("\nInvalid choice. Please try booking again.\n");
                        return; // Skip to the next iteration
                    }
                    
                } else if (choice == 2) {
                    // No cargo option
                    isWingman = false;
                } else {
                    System.out.println("\nInvalid choice. Please try booking again.\n");
                    return; // Skip to the next iteration
                }
                // Create the ticket object
                coach ticket2 = new coach(false, 0, handRail, carryOnBaggageAdditionalFee, isThrillSeeker, isWingman);
                // Set the wingman pick
                ticket2.setWingmanWingPick(pickedWing);
                // Set the points for the ticket
                ticket2.setPoints();
                // Print the ticket details
                System.out.println("Ticket created: " + ticket2);
                //add ticket to list
                System.out.println("Ticket added to the list.");
                ticketer.addTicket(ticket2);
                break;

            case 3: // business ticket
                //variable initializtion
                boolean overheadBin = false;
                int seatPick = 0;
                // Create a business ticket
                System.out.println("We're almost done! Please answer the following questions:");
                System.out.print("Do you want the option to use an overhead bin?\n1: yes\n2: no \nenter your choice: ");
                choice = 0;
                try {
                    choice = s.nextInt();
                } catch (Exception e) {
                    System.out.println("\nInvalid input. Please try booking again.\n");
                    s.next(); // Clear the invalid input
                    return; // Skip to the next iteration
                }
                if (choice == 1) {
                    // Ask if the user wants to pick cargo
                    overheadBin = true;
                } else if (choice == 2) {
                    // No cargo option
                    overheadBin = false;
                } else {
                    System.out.println("\nInvalid choice. Please try booking again.\n");
                    return; // Skip to the next iteration
                }
                System.out.print("Do you want the option to pick a seat other than the middle?\n1: yes\n2: no \nenter your choice: ");
                choice = 0;
                try {
                    choice = s.nextInt();
                } catch (Exception e) {
                    System.out.println("\nInvalid input. Please try booking again.\n");
                    s.next(); // Clear the invalid input
                    return; // Skip to the next iteration
                }
                if (choice == 1) {
                    // Ask if the user wants to pick cargo
                    System.out.print("pick your seat? (1: aisle, 2: window): ");
                    try {
                        choice = s.nextInt();
                    } catch (Exception e) {
                        System.out.println("\nInvalid input. Please try booking again.\n");
                        s.next(); // Clear the invalid input
                        return; // Skip to the next iteration
                    }
                    if (choice == 1) {
                        seatPick = 1;
                    } else if (choice == 2) {
                        // No cargo option
                        seatPick = 2;
                    } else {
                        System.out.println("\nInvalid choice. Please try booking again.\n");
                        return; // Skip to the next iteration
                    }
                } 
                else if (choice == 2) {
                    seatPick = 0;
                } 
                else {
                    System.out.println("\nInvalid choice. Please try booking again.\n");
                    return; // Skip to the next iteration
                }

                System.out.print("Is the ticket a thrill seeker?\n1: yes\n2: no \nenter your choice: ");
                choice = 0;
                try {
                    choice = s.nextInt();
                } catch (Exception e) {
                    System.out.println("\nInvalid input. Please try booking again.\n");
                    s.next(); // Clear the invalid input
                    return; // Skip to the next iteration
                }
                if(choice == 1) {
                    // Ask if the user wants to pick cargo
                    isThrillSeeker = true;
                } else if (choice == 2) {
                    // No cargo option
                    isThrillSeeker = false;
                } else {
                    System.out.println("\nInvalid choice. Please try booking again.\n");
                    return; // Skip to the next iteration
                }
                System.out.print("Is the ticket a wingman?\n1: yes\n2: no \nenter your choice: ");
                choice = 0;
                try {
                    choice = s.nextInt();
                } catch (Exception e) {
                    System.out.println("\nInvalid input. Please try booking again.\n");
                    s.next(); // Clear the invalid input
                    return; // Skip to the next iteration
                }
                if(choice == 1) {
                    // Ask if the user wants to pick cargo
                    isWingman = true;
                    choice = 0;
                    System.out.print("Would you like to pick the wing you want?\n1: yes\n2: no\nenter your choice: ");
                    choice = 0;
                    try {
                        choice = s.nextInt();
                    } catch (Exception e) {
                        System.out.println("\nInvalid input. Please try booking again.\n");
                        s.next(); // Clear the invalid input
                        return; // Skip to the next iteration
                    }
                    if(choice == 1) {
                        System.out.print("pick your wing? (1: left wing, 2: right wing): ");
                        try {
                            choice = s.nextInt();
                        } catch (Exception e) {
                            System.out.println("\nInvalid input. Please try booking again.\n");
                            s.next(); // Clear the invalid input
                            return; // Skip to the next iteration
                        }
                        if (choice == 1) {
                            pickedWing = 1;
                        } else if (choice == 2) {
                            // No cargo option
                            pickedWing = 2;
                        } else {
                            System.out.println("\nInvalid choice. Please try booking again.\n");
                            return; // Skip to the next iteration
                        }
                    } 
                    else if (choice == 2) {
                        pickedWing = 0;
                    } 
                    else {
                        System.out.println("\nInvalid choice. Please try booking again.\n");
                        return; // Skip to the next iteration
                    }
                } 
                else if (choice == 2) {
                    // No cargo option
                    isWingman = false;
                } 
                else {
                    System.out.println("\nInvalid choice. Please try booking again.\n");
                    return; // Skip to the next iteration
                }
                // Create the ticket object
                business ticket3 = new business(true, 0, seatPick, overheadBin, isThrillSeeker, isWingman);
                // Set the wingman pick
                ticket3.setWingmanWingPick(pickedWing);
                // Set the points for the ticket
                ticket3.setPoints();
                // Print the ticket details
                System.out.println("Ticket created: " + ticket3);
                //add ticket to list
                System.out.println("Ticket added to the list.");
                ticketer.addTicket(ticket3);

                break;

            case 4: // first ticket
                //variable initializtion
                boolean yokeAndRudder = false;
                // Create a first ticket
                System.out.println("We're almost done! Please answer the following questions:");
                System.out.print("Do you want the option to use a yoke and rudder?\n1: yes\n2: no \nenter your choice: ");
                choice = 0;
                try {
                    choice = s.nextInt();
                } catch (Exception e) {
                    System.out.println("\nInvalid input. Please try booking again.\n");
                    s.next(); // Clear the invalid input
                    return; // Skip to the next iteration
                }
                if (choice == 1) {
                    // Ask if the user wants to pick cargo
                    yokeAndRudder = true;
                } else if (choice == 2) {
                    // No cargo option
                    yokeAndRudder = false;
                } else {
                    System.out.println("\nInvalid choice. Please try booking again.\n");
                    return; // Skip to the next iteration
                }
                System.out.print("Is the ticket a thrill seeker?\n1: yes\n2: no \nenter your choice: ");
                choice = 0;
                try {
                    choice = s.nextInt();
                } catch (Exception e) {
                    System.out.println("\nInvalid input. Please try booking again.\n");
                    s.next(); // Clear the invalid input
                    return; // Skip to the next iteration
                }
                if(choice == 1) {
                    // Ask if the user wants to pick cargo
                    isThrillSeeker = true;
                } else if (choice == 2) {
                    // No cargo option
                    isThrillSeeker = false;
                } else {
                    System.out.println("\nInvalid choice. Please try booking again.\n");
                    return; // Skip to the next iteration
                }
                System.out.print("Is the ticket a wingman?\n1: yes\n2: no \nenter your choice: ");
                choice = 0;
                try {
                    choice = s.nextInt();
                } catch (Exception e) {
                    System.out.println("\nInvalid input. Please try booking again.\n");
                    s.next(); // Clear the invalid input
                    return; // Skip to the next iteration
                }
                if(choice == 1) {
                    // Ask if the user wants to pick cargo
                    isWingman = true;
                    choice = 0;
                    System.out.print("Would you like to pick the wing you want?\n1: yes\n2: no\nenter your choice: ");
                    choice = 0;
                    try {
                        choice = s.nextInt();
                    } catch (Exception e) {
                        System.out.println("\nInvalid input. Please try booking again.\n");
                        s.next(); // Clear the invalid input
                        return; // Skip to the next iteration
                    }
                    if(choice == 1) {
                        System.out.print("pick your wing? (1: left wing, 2: right wing): ");
                        try {
                            choice = s.nextInt();
                        } catch (Exception e) {
                            System.out.println("\nInvalid input. Please try booking again.\n");
                            s.next(); // Clear the invalid input
                            return; // Skip to the next iteration
                        }
                        if (choice == 1) {
                            pickedWing = 1;
                        } else if (choice == 2) {
                            // No cargo option
                            pickedWing = 2;
                        } else {
                            System.out.println("\nInvalid choice. Please try booking again.\n");
                            return; // Skip to the next iteration
                        }
                    } 
                    else if (choice == 2) {
                        pickedWing = 0;
                    } 
                    else {
                        System.out.println("\nInvalid choice. Please try booking again.\n");
                        return; // Skip to the next iteration
                    }
                } 
                else if (choice == 2) {
                    // No cargo option
                    isWingman = false;
                } 
                else {
                    System.out.println("\nInvalid choice. Please try booking again.\n");
                    return; // Skip to the next iteration
                }
                // Create the ticket object
                first ticket4 = new first(false, 0, yokeAndRudder, isThrillSeeker, isWingman);
                // Set the wingman pick
                ticket4.setWingmanWingPick(pickedWing);
                // Set the points for the ticket
                ticket4.setPoints();
                // Print the ticket details
                System.out.println("Ticket created: " + ticket4);
                //add ticket to list
                System.out.println("Ticket added to the list.");
                ticketer.addTicket(ticket4);

                break;

            default:
                System.out.println("\nInvalid choice. Please try booking again\n");
                return; // Skip to the next iteration
        }
        
    }

    //method to view tickets
    public void viewTickets(ArrayList<tickets> ticketsList){
        System.out.println("Viewing tickets...");
        int choice = 0;
        // Display the list of tickets
        System.out.println("Would you like to view all tickets (sorted), or display based on upgrades?");
        System.out.println("1. View all tickets");
        System.out.println("2. View tickets based on upgrades");
        System.out.print("Enter your choice: ");
        try {
            choice = s.nextInt();
        } catch (Exception e) {
            System.out.println("\nInvalid input. Please enter a number.\n");
            s.next(); // Clear the invalid input
            return; // Skip to the next iteration
        }
        if (choice == 1){
            //sort tickets based on points
            //do a collections.sort
            Collections.sort(ticketsList);
            //print the tickets
            for (tickets ticket : ticketsList) {
                System.out.println(ticket);
            }
        }

        else if(choice == 2){
            //view the tickets based on upgrades
            System.out.println("1. view all with thrill seeker");
            System.out.println("2. view all with wingman");
            System.out.println("3. view all with no options and upgrades");
            System.out.println("\nEnter your choice: ");
            try {
                choice = s.nextInt();
            } catch (Exception e) {
                System.out.println("\nInvalid input. Please enter a number.\n");
                s.next(); // Clear the invalid input
                return; // Skip to the next iteration
            }
            if (choice == 1){
                //sort tickets based on points
                //do a collections.sort
                Collections.sort(ticketsList);
                //print the tickets
                for (tickets ticket : ticketsList) {
                    if(ticket.isThrillSeeker()){
                        System.out.println(ticket);
                    }
                }
            }
            else if(choice == 2){
                //sort tickets based on points
                //do a collections.sort
                Collections.sort(ticketsList);
                //print the tickets
                for (tickets ticket : ticketsList) {
                    if(ticket.isWingman()){
                        System.out.println(ticket);
                    }
                }
            }
            else if(choice == 3){
                //sort tickets based on points
                //do a collections.sort
                Collections.sort(ticketsList);
                //print the tickets
                for (tickets ticket : ticketsList) {
                    if(!ticket.isWingman() && !ticket.isThrillSeeker()){
                        //if it's a steerage ticket check if it has cargo
                        if(ticket instanceof steerage){
                            steerage sTicket = (steerage) ticket;
                            if(!sTicket.isPickedCargo()){
                                System.out.println(ticket);
                            }
                        }
                        else if(ticket instanceof coach){
                            coach cTicket = (coach) ticket;
                            if(!cTicket.isCarryOnBaggageAdditionalFee() && !cTicket.isHandRail()){
                                System.out.println(ticket);
                            }
                        }
                        else if(ticket instanceof business){
                            business bTicket = (business) ticket;
                            if(!bTicket.isOverheadBin() && bTicket.getSeat() == 0){
                                System.out.println(ticket);
                            }
                        }
                        else if(ticket instanceof first){
                            first fTicket = (first) ticket;
                            if(!fTicket.isYokeAndRudder()){
                                System.out.println(ticket);
                            }
                        }
                        else{
                            System.out.println(ticket);
                        }
                    }
                }
            }
        }
        else{
            System.out.println("\nInvalid choice.\n");
        }
    }


}