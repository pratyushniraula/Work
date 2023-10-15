//pratyush niraula pxn210033: contributed to stack.java and main.java
//yash maheshwari yxm210016: contributed to node.java and main.java
public class Stack{
    //linked list stack
    public Node head;
    public char data;
    
    public Stack(){
        head = null;
    }

    // push pop and peek
    public void push(char data, int precedence){
        Node newNode = new Node(data, precedence);
        if(head == null){
            head = newNode;
        }else{
            newNode.next = head;
            head = newNode;
        }
    }
    public Node pop(){
        if(head == null){
            return null;
        }
        else{
            Node temp = head;
            head = head.next;
            return temp;
        }
    }
    public char peek(){
        if(head == null){
            return ' ';
        }else{
            return head.data;
        }
    }
    public boolean isEmpty(){
        if(head == null){
            return true;
        }else{
            return false;
        }
    }


}