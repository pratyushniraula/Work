public class Main {
    public static void main(String[] args) {
        // Example usage of the ArrayNumSet class
        ArrayNumSet<Double> numSet = new ArrayNumSet<>(5);
        Double num1 = 1.0;
        // Adding elements
        System.out.println(numSet.add(num1)); // true
        System.out.println(numSet.add(2.4)); // true
        System.out.println(numSet.add(2.4)); // false (duplicate)
        
        // Checking size and capacity
        System.out.println("Size: " + numSet.size()); // 2
        System.out.println("Capacity: " + numSet.capacity()); // 5
        
        // Checking if an element exists
        System.out.println(numSet.contains(1.0)); // true
        System.out.println(numSet.contains(2.4)); // true
        System.out.println(numSet.contains(3.2)); // false
        
        // Removing an element
        System.out.println(numSet.remove(2.4)); // true
        System.out.println(numSet.remove(3.0)); // false (not found)
        
        // Final size check
        System.out.println("Final Size: " + numSet.size()); // 1

        //check the type of an element after getting it from the array
        Double element = numSet.get(0);
        if (element instanceof Double) {
            System.out.println("Element is of type Double: " + element);
        } else {
            System.out.println("Element is not of type Double.");
        }
    }
        // Note: The ArrayNumSet class should be implemented as per the provided interface.
        // Ensure to handle exceptions and edge cases as needed.


}
