//Pratyush Niraula
//pxn210033
//generic binary tree class 
//package RedboxInventorySystem;

public class BinTree<T extends Comparable<T>> {
    private Node<T> root;

    public BinTree() {
        this.root = null;
    }

    public BinTree(Node<T> root) {
        this.root = root;
    }

    public Node<T> getRoot() {
        return this.root;
    }

    public void setRoot(Node<T> root){
        this.root = root;
    }

    //create a recursive insert method
    //helper method for insert
    public void insert(Node<T> newNode) {
        insert(this.root, newNode);
    }
    //helped recursive method that displays T instead of the the entire node
    public void insert(Node<T> root, Node<T> newNode) {
        if (root == null) {
            this.root = newNode;
        }
        else if (root.compareTo(newNode) < 0) {
            if (root.getNodeRight() == null) {
                root.setNodeRight(newNode);
            }
            else {
                insert(root.getNodeRight(), newNode);
            }
        }
        else if (root.compareTo(newNode) > 0) {
            if (root.getNodeLeft() == null) {
                root.setNodeLeft(newNode);
            }
            else {
                insert(root.getNodeLeft(), newNode);
            }
        }
    }

    //create a recursive search method
    //helped recursive method that displays T instead of the the entire node
    public T search(Node<T> searchNode){
        if(searchHelp(this.root, searchNode) != null)
        return searchHelp(this.root, searchNode).getData();
        else{
            System.out.println("Node not found in search");
            return null;
        }
    }
    //actual recursive method
    private Node<T> searchHelp(Node<T> root, Node<T> searchNode) {
        if (root == null) {
            return null;
        }
        else if (root.compareTo(searchNode) == 0) {
            return root;
        }
        else if (root.compareTo(searchNode) < 0) {
            return searchHelp(root.getNodeRight(), searchNode);
        }
        else if(root.compareTo(searchNode) > 0){
            return searchHelp(root.getNodeLeft(), searchNode);
        }
        return null;
    }


    //create a recursive delete method
    public T delete(Node<T> deleteNode) {
        Node<T> node = deleteHelper(this.root, deleteNode, null);
        if(node != null)
        return node.getData();
        else{
            System.out.println("Node not found in delete");
            return null;
        }
    }
    //helper method for the deleting for recursive purposes
    public Node<T> deleteHelper(Node<T> child, Node<T> deleteNode, Node<T> parent) {
        if (child == null) {
            return null;
        }
        // else if (child.compareTo(deleteNode) < 0) {
        //     child.setNodeRight(deleteHelper(child.getNodeRight(), deleteNode, child));
        // }
        // else if (child.compareTo(deleteNode) > 0) {
        //     child.setNodeLeft(deleteHelper(child.getNodeLeft(), deleteNode, child));
        // }
        else if (child.compareTo(deleteNode) < 0) {
            Node<T> newChild = deleteHelper(child.getNodeRight(), deleteNode, child);
            child.setNodeRight(newChild);
            if (parent != null) {
                parent.setNodeRight(child);
            }
        }
        else if (child.compareTo(deleteNode) > 0) {
            Node<T> newChild = deleteHelper(child.getNodeLeft(), deleteNode, child);
            child.setNodeLeft(newChild);
            if (parent != null) {
                parent.setNodeLeft(child);
            }
        }
        else /*if(root.compareTo(deleteNode) == 0 && root != null && parent != null)*/{
            //if root is a leaf
            if(child.getNodeLeft() == null && child.getNodeRight() == null){
                Node<T> temp = child;
                child = null;
                return temp;
            }
            //if root has one child
            else if(child.getNodeLeft() != null ^ child.getNodeRight() != null){
                if(parent == null){
                    if(child.getNodeLeft() != null){
                        Node<T> temp = child;
                        this.root = child.getNodeLeft();
                        return temp;
                    }
                    else{
                        Node<T> temp = child;
                        this.root = child.getNodeRight();
                        return temp;
                    }
                }
                else if(child.getNodeLeft() != null){
                    parent.setNodeLeft(child.getNodeLeft());
                    child.setNodeLeft(null);
                    return child;
                }
                else{
                    parent.setNodeRight(child.getNodeRight());
                    child.setNodeRight(null);
                    return child;
                }
            }
            //if root has two children
            // else /*if(root.getNodeLeft() != null && root.getNodeRight() != null)*/{
            //     Node<T> temp = root.getNodeRight();
            //     temp = leftMost(temp);

            //     T triangleTransfer = root.getData();
            //     temp.setData(triangleTransfer);
            //     root.setData(temp.getData());
            //     deleteHelper(root.getNodeRight(), temp, null);
            //     Node<T> temp2 = new Node<>(triangleTransfer);
            //     return temp2;
            // }
            else {
                Node<T> temp = leftMost(child.getNodeRight());
            
                // Save the data of the root node
                T childData = child.getData();
            
                // Copy the data of the minimum node to the root
                child.setData(temp.getData());
            
                // Delete the minimum node from the right subtree
                child.setNodeRight(deleteHelper(child.getNodeRight(), temp, child));
            
                // Create a new node with the original data of the root
                Node<T> temp2 = new Node<>(childData);
            
                return temp2;
            }
        }
        return child;
        
    }

    //finder for leftmost node in the tree
    public Node<T> leftMost(Node<T> root){
        if(root.getNodeLeft() == null){
            return root;
        }
        else{
            return leftMost(root.getNodeLeft());
        }
    }

    //might or might not work but wants to print tostring method
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        traverseInOrder(this.root, sb);
        return sb.toString();
    }

    private void traverseInOrder(Node<T> node, StringBuilder sb) {
        if (node != null) {
            traverseInOrder(node.getNodeLeft(), sb);
            sb.append(node.getData().toString()).append("\n");
            traverseInOrder(node.getNodeRight(), sb);
        }
    }
}