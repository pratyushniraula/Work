package ImprovedDatabaseJava;

public class Node<T>{
    private T data; //stores generic type into hashmap
    private Node<T> next; //since this is seperate chaining hashmap I will need to create a linked list

    //accessors
    public Node<T> getNext(){
        return next;
    }
    public T getData(){
        return data;
    }

    //mutators
    public void setNext(Node<T> n){
        this.next = n;
    }
    public void setData(T d){
        this.data = d;
    }
    
}