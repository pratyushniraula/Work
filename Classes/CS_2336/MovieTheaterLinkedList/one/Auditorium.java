package one;
public class Auditorium{
    Node head = null;

    //linked list is created in auditorium
    public Auditorium(){
        //create head pointer if not created already
        //creates empty linked list
        for (int i = 0; i < 10; i++){
            //if cur.down is null and i is not 9, create a new node and set cur.down to it
            for (int j = 0; j < 25; j++){
                if (head == null){
                head = new Node();
                Node node = new Node();
                head.setNext(node);
                }
                else{
                        Node node = head;
                        while(node.getDown() != null){
                            node = node.getDown();
                        }
                        while(node.getNext() != null){
                            node = node.getNext();
                        }
                        Node newNode = new Node();
                        node.setNext(newNode);
                }
            }

            if (i != 9){
                Node node = head;
                while(node.getDown() != null){
                    node = node.getDown();
                }
                Node newNode = new Node();
                node.setDown(newNode);
            }
        }
    }
    //print auditorium
    public void printAuditorium(){
        Node node = head;
        while(node != null){
            Node node2 = node;
            while(node2 != null){
                if(node2.getSeat().getTicketType() == '.' || node2.getSeat().getTicketType() == 'A' || node2.getSeat().getTicketType() == 'S' || node2.getSeat().getTicketType() == 'C'){
                System.out.print(node2.getSeat().getTicketType());
                node2 = node2.getNext();
                }
            }
            System.out.println();
            node = node.getDown();
        }
    }

}