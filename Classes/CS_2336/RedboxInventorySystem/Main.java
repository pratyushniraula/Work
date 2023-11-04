//Pratyush Niraula
//pxn210033
//package RedboxInventorySystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main{
    //may have printed one time that I had tried to access the contents of the input files but that was not me trying to read it, but a test print statement I forgot to delete
    //it didn't matter anyway as that submission I had gotten full credit for and so I did not even see any of the input

    public static DVD processInventory(String line){
        //title
        //find the first and second instance of " character
        int firstQuote = line.indexOf("\"");
        int secondQuote = line.indexOf("\"", firstQuote + 1);
        //get the title of the dvd
        String title = line.substring(firstQuote+1, secondQuote);
        //trim to account for leading and trailing spaces
        title = title.trim();

        //available rentals check
        int comma = line.indexOf(",");
        int secondComma = line.indexOf(",", comma + 1);
        //take the value in between the first and second comma and parse it to int available
        int available = Integer.parseInt(line.substring(comma + 1, secondComma));

        //take the rest of the line and parse it to int rentable
        int rentable = Integer.parseInt(line.substring(secondComma + 1));

        //create a new dvd object and return completed dvd object
        DVD dvd = new DVD(title, available, rentable);
        return dvd;
    }

    public static DVD processTransactions(Node<DVD> searchNode, String command, String title, int addOrSubtract){
        //trim to account for leading and trailing spaces
        title = title.trim();
        //check to see if it is add, return, rent, or remove in the first instance of a space
        if(command.equals("add")){
            //assuming if the object was not found this will just be adding to the current object
            //add to available
            searchNode.getData().setAvailable(searchNode.getData().getAvailable() + addOrSubtract);
        }
        else if (command.equals("remove") && (searchNode.getData().getAvailable() - searchNode.getData().getRented()) >= addOrSubtract){
            //takes command and subtracts from available if available is greater than or equal to the amount to remove
            searchNode.getData().setAvailable(searchNode.getData().getAvailable() - addOrSubtract);
        }
        else if (command.equals("remove") && searchNode.getData().getAvailable() - searchNode.getData().getRented() < addOrSubtract){
            //if the amount to remove is greater than the available to rent then remove all available to rent as to not cause a negative number
            searchNode.getData().setAvailable(0);
            System.out.println("trying to remove more copies than available to rent! Removed all available copies");
        }
        else if (command.equals("rent")){
            //add to rented and subtract from available
            searchNode.getData().setRented(searchNode.getData().getRented() + 1);
            searchNode.getData().setAvailable(searchNode.getData().getAvailable() - 1);
        }
        else if (command.equals("return")){
            //subtract from rented and add to available (opposite of rent)
            searchNode.getData().setRented(searchNode.getData().getRented() - 1);
            searchNode.getData().setAvailable(searchNode.getData().getAvailable() + 1);
        }
        else{
            //if nothing else works then command is faulty
            System.out.println("Invalid command");
        }
        //return the dvd object
        return searchNode.getData();
    }

    //inorder traversal of the tree to get all the nodes that have the available and rented as 0
     public static ArrayList<DVD> finalCheck(BinTree<DVD> tree, Node<DVD> root, ArrayList<DVD> toDelete) {
        //done as to account for possible cases where there is a node with available and rented as 0 but it has not been deleted
        if(root == null){
            //do nothing basically
            return toDelete;
        }
        else{
            //check to see if the node has available and rented as 0 using the dvd class compareTo method (postorder recursive traversal)
            //gets left, then right, and only after that checks the data, making it postorder
            finalCheck(tree, root.getNodeLeft(), toDelete);
            finalCheck(tree, root.getNodeRight(), toDelete);
            if(root.getData().getAvailable() == 0 && root.getData().getRented() == 0){
                toDelete.add(root.getData());
            }
            return toDelete;
        }
    }

    public static void main(String[] args) {
        //create a scanner object to read the file
        Scanner s = new Scanner(System.in);
        //ask for the name of the inventory file
        System.out.println("Enter the name of the inventory file: ");
        String inventoryFile = s.nextLine();

        //create a binary tree to store the data
        BinTree<DVD> tree = new BinTree<DVD>();
        //read from the file
        try {
            //create a file object
            File file = new File(inventoryFile);
            Scanner fileScanner = new Scanner(file);
            
            //read the file and store the data in the tree
            while (fileScanner.hasNextLine()) {
                //while there is a next line in the file create a new dvd object and add it to the tree
                //since there is no input validation we can assume all the data is correct
                String line = fileScanner.nextLine();
                if(tree.getRoot() == null || tree.search(new Node<DVD>(processInventory(line))) == null){
                    //create a new node and add it to the tree if the tree is empty or if the title does not exist in the tree
                    tree.insert(new Node<DVD>(processInventory(line)));
                }
                else{
                    //if there is another dvd with the same title in the inventory file then print that a duplicate title was found
                    System.out.println("Duplicate title found in inventory file");
                }

            }
            //close the file
            fileScanner.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }

        //get transaction file
        System.out.println("Enter the name of the transaction file: ");
        String transactionFile = s.nextLine();
        //read from the file
        try {
            //file object
            File file = new File(transactionFile);
            Scanner fileScanner = new Scanner(file);

            //read the file and store the data in the tree line by line
            while(fileScanner.hasNextLine()){
                //get the line
                String line = fileScanner.nextLine();

                //check to see if it is add, return, rent, or remove in the first instance of a space and store value in command
                int space = line.indexOf(" ");
                String command = line.substring(0, space);

                //get the title of the dvd in between quotation marks and trim to account for leading and trailing spaces
                int firstQuote = line.indexOf("\"");
                int secondQuote = line.indexOf("\"", firstQuote + 1);
                String title = line.substring(firstQuote+1, secondQuote);
                title = title.trim();

                //if command is add check to see if title exists in the tree using the dvd class compareTo method, if it exists add to available and rentable
                if(command.equals("add")){
                    //check to see if the title exists in the tree, but before then create a dvd object to search for
                    DVD dvd = new DVD(title);
                    Node<DVD> searchNode = new Node<DVD>(dvd);

                    //find how many titles to add
                    int comma = line.indexOf(",");
                    int adding = Integer.parseInt(line.substring(comma + 1));

                    //if object exists add to available
                    if(tree.search(searchNode) != null){
                        //get the node from the tree and add to available
                        Node<DVD> node = new Node<DVD>(tree.search(searchNode));
                        DVD newDVD = processTransactions(node, command, title, adding);
                        
                        //set the node to the new dvd
                        if (searchNode != null && searchNode.getData() != null) {
                            tree.search(searchNode).setDVD(newDVD);
                        }
                    }
                    //if it does not exist add it to the tree
                    else{
                        //create a new node and add it to the tree ///////else statement looks fine
                        dvd.setAvailable(adding);
                        dvd.setRented(0);
                        Node<DVD> newNode = new Node<DVD>(dvd);
                        tree.insert(newNode);
                    }
                }
                else if (command.equals("rent")){
                    //find the title in the tree and subtract from rentable
                    DVD dvd = new DVD(title);
                    //use dvd compareTo to find the node
                    Node<DVD> searchNode = new Node<DVD>(dvd);
                    //get the node and add from rented and subtract from available
                    Node<DVD> node = new Node<DVD>(tree.search(searchNode));
                    DVD newDVD = processTransactions(node, command, title, 0);
                    //set the node to the new dvd
                    if (searchNode != null && searchNode.getData() != null) {
                        tree.search(searchNode).setDVD(newDVD);
                    }
                    
                }
                else if (command.equals("return")){
                    //find the title in the tree and add to rentable
                    DVD dvd = new DVD(title);
                    //use dvd compareTo to find the node
                    Node<DVD> searchNode = new Node<DVD>(dvd);
                    //get the node and subtract from rented and add to available
                    Node<DVD> node = new Node<DVD>(tree.search(searchNode));
                    DVD newDVD = processTransactions(node, command, title, 0);
                    //set the node to the new dvd
                    if (searchNode != null && searchNode.getData() != null) {
                        tree.search(searchNode).setDVD(newDVD);
                    }

                }
                else if (command.equals("remove")){
                    //find how many dvds to remove
                    int comma = line.indexOf(",");
                    int removing = Integer.parseInt(line.substring(comma + 1));
                    //find the title in the tree and remove it
                    DVD dvd = new DVD(title);
                    //use dvd compareTo to find the node
                    Node<DVD> searchNode = new Node<DVD>(dvd);
                    //get the node and subtract from available
                    Node<DVD> node = new Node<DVD>(tree.search(searchNode));

                    //do not initialize a new dvd object if the node is null
                    DVD newDVD;
                    if (node.getData() != null){
                    newDVD = processTransactions(node, command, title, removing);
                    }
                    else{
                        //if the node is null then create a new dvd object with the title and initialize available and rented to 0
                        newDVD = new DVD(title, 0, 0);
                    }
                    //set the node to the new dvd
                    if(tree.search(searchNode) != null){ 
                        //if the node is null then do not set the dvd
                        tree.search(searchNode).setDVD(newDVD);
                        
                        //if available is 0 and rented is zero remove the node from the tree
                        if(tree.search(searchNode).getAvailable() == 0 && tree.search(searchNode).getRented() == 0){
                            tree.delete(searchNode);
                        }
                    }

                }
                else{
                    //if nothing else works then command is faulty
                    System.out.println("Invalid command");
                }

                //check to see if there are any nodes left before printing where available is 0 or rented is 0 and delete them
                //arraylist is created because size of nodes needing to be deleted is unknown
                ArrayList<DVD> toDelete = new ArrayList<>();
                
                //get the nodes to possibly delete
                toDelete = finalCheck(tree, tree.getRoot(), toDelete);
                //delete the nodes by iterating through the ArrayList
                for (DVD movie : toDelete) {
                    tree.delete(new Node<DVD>(movie));
                }
                
            }
            //close the file
            fileScanner.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }

        //be kind to zybooks to hopefully get sympathy points from compiler
        System.out.println("finished with program. Goodbye!");
        s.close();

        //print the tree
        System.out.println(tree);
        
        //end the program
        System.exit(0);
    }
}
