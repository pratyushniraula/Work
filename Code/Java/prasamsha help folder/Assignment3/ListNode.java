/**
 * A node class for a singly linked list.  This implementation is not generic, and can store items of Number type
 * (or any child type of Number such as Integer, Short, Long, Double, etc).
 */
public class ListNode {
    public ListNode next;
    private final Number item;

    public ListNode(Number item) {
        this.item = item;
        this.next = null;
    }

    public Number getItem() {
        return this.item;
    }
}