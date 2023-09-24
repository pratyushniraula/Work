//Pratyush Niraula
//Pxn210033
public class Node{
    //node constructor should contain a seat constructor and a node next along with a node down
    private Seat seat = null;
    private Node next;
    private Node down;
    
    public Node(Seat seat){
        this.seat = seat;
        this.next = null;
        this.down = null;
    }
    public Node(){
        this.seat = new Seat();
        this.next = null;
        this.down = null;
    }
    public Node(int rows, char columns, char seatType){
        this.seat = new Seat(rows, columns, seatType);
        this.next = null;
        this.down = null;
    }

    public Seat getSeat(){
        return this.seat;
    }
    public Node getNext(){
        return this.next;
    }
    public Node getDown(){
        return this.down;
    }
    public void setSeat(Seat seat){
        this.seat = seat;
    }
    public void setNext(Node next){
        this.next = next;
    }
    public void setDown(Node down){
        this.down = down;
    }

    //return seat's to seatString function
    public String toString(){
        return seat.toString();
    }
    

}