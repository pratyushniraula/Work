import java.lang.reflect.Array;

public class ArrayNumSet<E extends Number> implements NumSet<E> {

    // The array that holds the elements
    public E[] arr;
    
    // Current number of elements in the set
    private int size;
    
    // The current capacity of the array
    private int currentCapacity;
    
    // Stores the actual type of elements once the first element is added
    private Class<? extends E> type;
    
    public ArrayNumSet(int initialCapacity) {
        if (initialCapacity <= 0) {
            initialCapacity = 1;
        }
        this.currentCapacity = initialCapacity;
        this.size = 0;
        // Delay allocation until knowing the type of E
        this.arr = null;
        this.type = null;
    }
    @Override
    @SuppressWarnings("unchecked")
    public boolean add(E e) {
        //check if the element is null
        if (e == null) {
            throw new NullPointerException("Cannot add null element.");
        }
        
        // Check for duplicates
        if (contains(e)) {
            return false;
        }
        
        // If the array hasn't been allocated yet, determine the runtime type using e.getClass()
        if (arr == null) {
            type = (Class<? extends E>) e.getClass();
            arr = createArray(type, currentCapacity);
        }
        
        // If the array is full, allocate a new array with double the capacity
        if (size == arr.length) {
            currentCapacity = arr.length * 2;
            E[] newArr = createArray(type, currentCapacity);
            System.arraycopy(arr, 0, newArr, 0, arr.length);
            arr = newArr;
        }
        
        // Add the element and update size
        arr[size] = e;
        size++;
        return true;
    }

    @SuppressWarnings("unchecked")
    private E[] createArray(Class<? extends E> classs, int capacity) {
        // This creates a new array with runtime type
        return (E[]) Array.newInstance(classs, capacity);
    }
    
    @Override
    public int capacity() {
        // If the array hasn't been allocated yet, return the current capacity
        // If the array is allocated, return its length
        if(arr == null) {
            return currentCapacity;
        } else {
            return arr.length;
        }
    }

    @Override
    public boolean contains(E e) {
        //check if the element is null
        if (e == null) {
            throw new NullPointerException("Cannot search for null element.");
        }
        // check whether the element is in the array
        for (int i = 0; i < size; i++) {
            if (arr[i].equals(e)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean remove(E e) {
        //check if the element is null
        if (e == null) {
            throw new NullPointerException("Cannot remove null element.");
        }
        // Check if the element exists in the array
        for (int i = 0; i < size; i++) {
            if (arr[i].equals(e)) {
                // Shift all elements to the left to fill the gap.
                for (int j = i; j < size - 1; j++) {
                    arr[j] = arr[j + 1];
                }
                // make last element null and decrease size
                arr[size - 1] = null;
                size--;
                return true;
            }
        }
        // Element not found, return false
        return false;
    }
    
    @Override
    public int size() {
        return size;
    }


    public E get(int index) {
        //check if the element is null
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Invalid index: " + index);
        }
        // Return the element at the index
        return arr[index];
    }
}