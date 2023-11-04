//Pratyush Niraula
//pxn210033
//generic node class
//package RedboxInventorySystem;
public class Node<T extends Comparable<T>> implements Comparable <Node<T>>{
    private T data;
    private Node<T> right;
    private Node<T> left;

    public Node(){
    }

    public Node(T data) {
        this.data = data;
        this.right = null;
        this.left = null;
    }
    public Node(T data, Node<T> right, Node<T> left) {
        this.data = data;
        this.right = right;
        this.left = left;
    }

    //getters
    public T getData() {
        return this.data;
    }
    public Node<T> getNodeRight() {
        return this.right;
    }
    public Node<T> getNodeLeft() {
        return this.left;
    }

    //setters
    public void setData(T data) {
        this.data = data;
    }
    public void setNodeRight(Node<T> right) {
        this.right = right;
    }
    public void setNodeLeft(Node<T> left) {
        this.left = left;
    }

    //testing method for ide
    public void test(){
        System.out.println("node is successfuly declared");
    }

    //toString method
    @Override
    public String toString() {
        //goes to the data class and calls the toString method
        return this.data.toString();
    }

    //compareTo method
    @Override
    public int compareTo(Node<T> otherNode) {
        // Compare the data of the two nodes
        return this.data.compareTo(otherNode.getData());
    }
}