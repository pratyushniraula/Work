//Pratyush Niraula
//pxn210033
//generic binary tree class 
//package RedboxInventorySystem;
import java.util.ArrayList;

public class BinTree<T extends Comparable<T>> {
    private Node<T> root;

    //default constructor
    public BinTree() {
        this.root = null;
    }

    //overloaded constructor
    public BinTree(Node<T> root) {
        this.root = root;
    }

    //getter
    public Node<T> getRoot() {
        return this.root;
    }

    //setter
    public void setRoot(Node<T> root){
        this.root = root;
    }

    
    //create a recursive insert method
    //helper method for insert
    public void insert(Node<T> newNode) {
        insert(this.root, newNode);
    }
    //helped recursive method that inserts the node in the correct place
    private void insert(Node<T> root, Node<T> newNode) {
        //if the root is null, set the root to the new node
        if (root == null) {
            this.root = newNode;
        }

        //if the root is less than the new node, insert the new node to the right
        else if (root.compareTo(newNode) < 0) {
            //if the right node is null, set the right node to the new node
            if (root.getNodeRight() == null) {
                root.setNodeRight(newNode);
            }

            //else, recursively call the insert method
            else {
                insert(root.getNodeRight(), newNode);
            }
        }

        //if the root is greater than the new node, insert the new node to the left
        else if (root.compareTo(newNode) > 0) {
            //if the left node is null, set the left node to the new node
            if (root.getNodeLeft() == null) {
                root.setNodeLeft(newNode);
            }

            //else, recursively call the insert method
            else {
                insert(root.getNodeLeft(), newNode);
            }
        }
    }

    //create a recursive search method
    //helper recursive method that displays T instead of the the entire node for privacy
    public T search(Node<T> searchNode){
        //if the search node is found, return the data
        if(searchHelp(this.root, searchNode) != null){
            return searchHelp(this.root, searchNode).getData();
        }

        //else, return null
        else{
            return null;
        }
    }
    //actual recursive method
    private Node<T> searchHelp(Node<T> root, Node<T> searchNode) {
        //if the root is null, return null
        if (root == null) {
            return null;
        }

        //if the root is equal to the search node, return the root
        else if (root.compareTo(searchNode) == 0) {
            return root;
        }

        //if the root is less than the search node, recursively call the search method
        else if (root.compareTo(searchNode) < 0) {
            return searchHelp(root.getNodeRight(), searchNode);
        }

        //if the root is greater than the search node, recursively call the search method
        else if(root.compareTo(searchNode) > 0){
            return searchHelp(root.getNodeLeft(), searchNode);
        }
        
        //if nothing else works return null anyway
        return null;
    }


    //create a recursive delete method
    public void delete(Node<T> deleteNode) {
        //just calls the actual delete method
        deleteHelper(deleteNode.getData());
        //no return statement as none is needed for this program to verify deletion
    }

    private void deleteHelper(T key) {
        //variable initialization
        Node<T> parent = null;
        Node<T> current = this.root;
        //while the current node is not null
        while (current != null) {
            //if the current node is equal to the key, ei the node to be deleted has been located
            if (current.getData().equals(key)) {
                //if the current node has no children, set the root to null
                if (current.getNodeLeft() == null && current.getNodeRight() == null) {
                    if (parent == null) {
                        this.root = null;
                    } 
                    
                    //if the current node is the left child of the parent, set the left child to null
                    else if (parent.getNodeLeft() == current) {
                        parent.setNodeLeft(null);
                    } 

                    //if the current node is the right child of the parent, set the right child to null
                    else {
                        parent.setNodeRight(null);
                    }
                } 
                //if the current node has one child and the right node is null continue with else if
                else if (current.getNodeRight() == null) {
                    //if the parent is null then set the root to the current node's left child
                    if (parent == null) {
                        this.root = current.getNodeLeft();
                    } 

                    //if the current node is the left child of the parent, set the left child's left node to the parent node's left child
                    else if (parent.getNodeLeft() == current) {
                        parent.setNodeLeft(current.getNodeLeft());
                    } 

                    //if the current node is the right child of the parent, set the right child to the current node's left child
                    else {
                        parent.setNodeRight(current.getNodeLeft());
                    }
                } 

                //if the current node has one child, set the parent to the child
                else if (current.getNodeLeft() == null) {
                    //if the parent is null, set the root to the current node's right child
                    if (parent == null) {
                        this.root = current.getNodeRight();
                    } 
                    
                    //if the current node is the left child of the parent, set the left child of the parent to the current node's right child
                    else if (parent.getNodeLeft() == current) {
                        parent.setNodeLeft(current.getNodeRight());
                    } 
                    //if the current node is the right child of the parent, set the right child of the parent to the current node's right child
                    else {
                        parent.setNodeRight(current.getNodeRight());
                    }
                } 

                //if the current node has two children
                else {
                    //find the successor of the current node
                    Node<T> successor = current.getNodeRight();
                    //find the leftmost node of the successor
                    // while (successor.getNodeLeft() != null) {
                    //     successor = successor.getNodeLeft();
                    // }
                    successor = leftMost(successor);
                    //set the current node's data to the successor's data
                    T successorData = successor.getData();
                    //delete the successor
                    deleteHelper(successorData);
                    //set the current node's data to the successor's data
                    current.setData(successorData);
                }
                //as the program is now done, there is no point in continuing the recursion
                return;
            } 
            
            //if the current node is less than the key, set the parent to the current node and the current node to the right child
            else if (current.getData().compareTo(key) < 0) {
                parent = current;
                current = current.getNodeRight();
            } 
            //if the current node is greater than the key, set the parent to the current node and the current node to the left child
            else {
                parent = current;
                current = current.getNodeLeft();
            }
        }
    }

    //finder for leftmost node in the tree for the delete method
    public Node<T> leftMost(Node<T> root){
        //if the root is null, return the root as you have reached the leftmost node
        if(root.getNodeLeft() == null){
            return root;
        }
        //else, recursively call the leftmost method
        else{
            return leftMost(root.getNodeLeft());
        }
    }


    @Override
    public String toString() {
        //create a string builder
        StringBuilder sb = new StringBuilder();
        //call the recursive method to traverse the tree in order and append the data to the string builder
        traverseInOrder(this.root, sb);
        //return the string builder as a string
        return sb.toString();
    }

    private void traverseInOrder(Node<T> node, StringBuilder sb) {
        //if the node is not null, recursively call the method
        if (node != null) {
            //inorder traversal, left, process (append into stringbuilder to be loaded and ready to print), right
            traverseInOrder(node.getNodeLeft(), sb);
            sb.append(node.getData().toString()).append("\n");
            traverseInOrder(node.getNodeRight(), sb);
        }
    }
}