//Pratyush Niraula
//pxn210033
//package RedboxInventorySystem;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main{
    //deal with adding new titles into the tree when processing transaction file and also deal with already added titles or duplicates in the tree
    //whatever comes to mind when adding is something to make sure to check
    public static DVD processInventory(String line){
        //title
        //find the first and second instance of " character
        int firstQuote = line.indexOf("\"");
        int secondQuote = line.indexOf("\"", firstQuote + 1);
        String title = line.substring(firstQuote+1, secondQuote);
        title = title.trim();

        //available rentals check
        int comma = line.indexOf(",");
        int secondComma = line.indexOf(",", comma + 1);
        //if there are reading errors check if this is the case or not that is causing problems*******
        int available = Integer.parseInt(line.substring(comma + 1, secondComma));

        //rentable
        int rentable = Integer.parseInt(line.substring(secondComma + 1));

        DVD dvd = new DVD(title, available, rentable);
        return dvd;
    }

    public static DVD processTransactions(Node<DVD> searchNode, String command, String title, int addOrSubtract){
        title = title.trim();
        //check to see if it is add, return, rent, or remove in the first instance of a space
        if(command.equals("add")){
            //assuming if the object was not found this will just be adding to the current object
            //add to available
            searchNode.getData().setAvailable(searchNode.getData().getAvailable() + addOrSubtract);
        }
        else if (command.equals("remove") && (searchNode.getData().getAvailable() - searchNode.getData().getRented()) >= addOrSubtract){
            searchNode.getData().setAvailable(searchNode.getData().getAvailable() - addOrSubtract);
        }
        else if (command.equals("remove") && searchNode.getData().getAvailable() - searchNode.getData().getRented() < addOrSubtract){
            searchNode.getData().setAvailable(0);
            System.out.println("trying to remove more copies than available to rent! Removed all available copies");
        }
        else if (command.equals("rent")){
            searchNode.getData().setRented(searchNode.getData().getRented() + 1);
            searchNode.getData().setAvailable(searchNode.getData().getAvailable() - 1);
        }
        else if (command.equals("return")){
            searchNode.getData().setRented(searchNode.getData().getRented() - 1);
            searchNode.getData().setAvailable(searchNode.getData().getAvailable() + 1);
        }
        else{
            System.out.println("Invalid command");
        }
        return searchNode.getData();
    }
    //final recursive check to see if there are any nodes where available is 0 or rented is 0 and delete them
    public static void finalCheck(BinTree<DVD> tree, Node<DVD> root){
        if(root == null){

        }
        else if(root.getData().getAvailable() == 0 && root.getData().getRented() == 0){
            tree.delete(root);
        }
        else{
            finalCheck(tree, root.getNodeLeft());
            finalCheck(tree, root.getNodeRight());
        }
        //return tree;
    }


    public static void main(String[] args) {
        //create a scanner object to read the file
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the name of the inventory file: ");
        String inventoryFile = s.nextLine();

        //create a binary tree to store the data
        BinTree<DVD> tree = new BinTree<DVD>();
        //read from the file
        try {
            File file = new File(inventoryFile);
            Scanner fileScanner = new Scanner(file);
            
            //read the file and store the data in the tree
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if(tree.getRoot() == null || tree.search(new Node<DVD>(processInventory(line))) == null){
                    tree.insert(new Node<DVD>(processInventory(line)));
                }
                else{
                    System.out.println("Duplicate title found in inventory file");
                }

            }
            //close the file
            fileScanner.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }


        System.out.println("Enter the name of the transaction file: ");
        String transactionFile = s.nextLine();
        //read from the file
        try {
            File file = new File(transactionFile);
            Scanner fileScanner = new Scanner(file);

            while(fileScanner.hasNextLine()){
                //test
                String line = fileScanner.nextLine();
                //check to see if it is add, return, rent, or remove in the first instance of a space
                int space = line.indexOf(" ");
                String command = line.substring(0, space);
                //get the title of the dvd
                int firstQuote = line.indexOf("\"");
                int secondQuote = line.indexOf("\"", firstQuote + 1);
                String title = line.substring(firstQuote+1, secondQuote);
                title = title.trim();
                //if command is add check to see if title exists in the tree using the dvd class compareTo method, if it exists add to available and rentable
                // if(command.equals("add")){
                //     //check to see if the title exists in the tree
                //     DVD dvd = new DVD(title);
                //     Node<DVD> searchNode = new Node<DVD>(dvd);
                //     int comma = line.indexOf(",");
                //     int adding = Integer.parseInt(line.substring(comma + 1));
                //     //if it exists add to available and rentable
                //     if(tree.search(searchNode) != null){

                //         //get the node from the tree 
                //         Node<DVD> node = new Node<DVD>(tree.search(searchNode));
                //         DVD newDVD = processTransactions(node, command, title, adding);
                //         //set the node to the new dvd
                //         tree.search(searchNode).setDVD(newDVD);
                //         //see if you replaced node or not in the tree or just set it to the new value
                //     }
                //     //if it does not exist add it to the tree
                //     else{
                //         //create a new node and add it to the tree ///////else statement looks fine
                //         dvd.setAvailable(adding);
                //         dvd.setRented(0);
                //         Node<DVD> newNode = new Node<DVD>(dvd);
                //         tree.insert(newNode);
                //     }
                // }
                //new test for add
                if(command.equals("add")){
                    //check to see if the title exists in the tree
                    DVD dvd = new DVD(title);
                    Node<DVD> searchNode = new Node<DVD>(dvd);
                    int comma = line.indexOf(",");
                    int adding = Integer.parseInt(line.substring(comma + 1));
                    //if it exists add to available and rentable
                    if(tree.search(searchNode) != null){
                        //get the node from the tree 
                        Node<DVD> node = new Node<DVD>(tree.search(searchNode));
                        DVD newDVD = processTransactions(node, command, title, adding);
                        //set the node to the new dvd
                        if (searchNode != null && searchNode.getData() != null) {
                            tree.search(searchNode).setDVD(newDVD);
                        }
                        //see if you replaced node or not in the tree or just set it to the new value
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
                    //find how many titles to remove
                    int comma = line.indexOf(",");
                    int removing = Integer.parseInt(line.substring(comma + 1));
                    //find the title in the tree and remove it
                    DVD dvd = new DVD(title);
                    //use dvd compareTo to find the node
                    Node<DVD> searchNode = new Node<DVD>(dvd);
                    //get the node and subtract from available
                    Node<DVD> node = new Node<DVD>(tree.search(searchNode));
                    DVD newDVD;
                    if (node.getData() != null){
                    newDVD = processTransactions(node, command, title, removing);
                    }
                    else{ //could be a new source for error, make sure to check this and possibly see if something went wrong
                        newDVD = new DVD(title, 0, 0);
                    }
                    //set the node to the new dvd
                    if(tree.search(searchNode) != null){ //if the node is null then do not set the dvd
                        tree.search(searchNode).setDVD(newDVD);
                        //if available is 0 and rented is zero remove the node from the tree
                        if(tree.search(searchNode).getAvailable() == 0 && tree.search(searchNode).getRented() == 0){
                            tree.delete(searchNode);
                        }
                    }

                }
                else{
                    System.out.println("Invalid command");
                }
                
                                                    System.out.println();//test********8
                    System.out.println(tree);//test********8
                
            }
            //close the file
            fileScanner.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }


        System.out.println("finished with program. Goodbye!");
        s.close();
        //check if work
        finalCheck(tree, tree.getRoot());
        //print all of the tree
        System.out.println(tree);
        
        
        System.exit(0);
    }
}
