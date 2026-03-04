// Interface for a number set.  Provides the bare minimum for a useful set
// implementation.

public interface NumSet<E extends Number> {
    
    // add an element e to the set if it does not already exist in the set
    // return true if this set did not already contain the specified element
    // throws NullPointerException if element e is null
    public boolean add(E e);
    
    // return the size of the underlying array
    public int capacity();
    
    // return true if given element e is in the set
    // throws NullPointerException if element e is null
    public boolean contains(E e);
    
    // remove an element e from the set if it exists in the set
    // return true if an element was removed (set changed)
    // throws NullPointerException if element e is null
    public boolean remove(E e);
    
    // return the set size (number of elements in set)
    public int size();
}