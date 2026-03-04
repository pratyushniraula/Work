@SuppressWarnings("unchecked")
public class HashNumSet<E extends Number> implements NumSet<E> {
    private ListNode[] table;
    private int size;

    // constructor
    public HashNumSet(int initialCapacity) {
        if (initialCapacity <= 0)
            throw new IllegalArgumentException("Capacity must be positive");
        table = new ListNode[initialCapacity];
        size = 0;
    }

    @Override
    public boolean add(E e) {
        // Check if the element is null
        if (e == null) 
            throw new NullPointerException("Null elements not supported");
        // Check if the element is already in the set
        // If it is, return false
        if (contains(e)) 
            return false;

        //if not, add it to the set
        //hash the element
        int hashed = hash(e);
        //prepend new node
        ListNode node = new ListNode(e);
        node.next = table[hashed];
        table[hashed] = node;
        size++;

        //check if load factor > .75
        if ((double) size / table.length > 0.75) {
            resize();
        }
        return true;
    }

    // Check if the set contains an element
    @Override
    public boolean contains(E e) {
        // Check if the element is null
        if (e == null) 
            throw new NullPointerException("Null elements not supported");
        
        // Check if the element is in the set
        int hashed = hash(e);
        // Traverse the linked list at the hashed index
        for (ListNode cur = table[hashed]; cur != null; cur = cur.next) {
            if (e.equals(cur.getItem())) 
                // If the current node's item is equal to the element, return true
                return true;
        }
        // If the element is not found, return false
        return false;
    }

    // Remove an element from the set
    @Override
    public boolean remove(E e) {
        // Check if the element is null
        if (e == null) 
            throw new NullPointerException("Null elements not supported");
        int hashed = hash(e);
        // Check if the element is in the set
        ListNode cur = table[hashed], prev = null;
        while (cur != null) {
            // Check if the current node's item is equal to the element
            if (e.equals(cur.getItem())) {
                // If the current node is the head of the list, update the head
                if (prev == null) {
                    table[hashed] = cur.next;
                } 
                // If the current node is not the head, update the previous node's next pointer
                else {
                    prev.next = cur.next;
                }
                // Decrement the size of the set
                size--;
                return true;
            }
            prev = cur;
            cur = cur.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int capacity() {
        return table.length;
    }

    // Hash function to map a Number to an index in the table
    private int hash(Number element) {
        int h = element.hashCode();
        return (h & 0x7FFFFFFF) % table.length;
    }

    // Resize the table to double its size and rehash all elements
    @SuppressWarnings("unchecked")
    private void resize() {
        ListNode[] old = table;
        int newCap = old.length * 2;
        table = new ListNode[newCap];
        size = 0;

        for (ListNode head : old) {
            for (ListNode cur = head; cur != null; cur = cur.next) {
                // safe unchecked cast since we only ever put E’s in
                E val = (E) cur.getItem();
                add(val);
            }
        }
    }

    public static void main(String[] args) {
        HashNumSet<Integer> set = new HashNumSet<>(5);
        set.add(1);
        set.add(2);
        set.add(3);
        System.out.println(set.size()); // 3
        System.out.println(set.contains(2)); // true
        set.remove(2);
        System.out.println(set.contains(2)); // false
    }
}
