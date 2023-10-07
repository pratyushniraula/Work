import java.util.ArrayList;

public class Main {
    public static int bin2Dec(String binary) throws BinaryFormatException {
        for (char c : binary.toCharArray()) {
            if (c != '0' && c != '1') {
                throw new BinaryFormatException(binary);
            }
        }

        return Integer.parseInt(binary, 2);
    }

    public static void main(String[] args) {
        // Testing with Integer
        ArrayList<Integer> intList = new ArrayList<>();
        intList.add(5);
        intList.add(2);
        intList.add(8);

        GenericList<Integer> intGenericList = new GenericList<>(intList);
        intGenericList.setList(3);
        intGenericList.insertionSort();
        System.out.println("Sorted Integer List: " + intGenericList.getList());
        System.out.println("Binary Search (5): " + intGenericList.binarySearch(5));

        // Testing with Double
        ArrayList<Double> doubleList = new ArrayList<>();
        doubleList.add(2.5);
        doubleList.add(1.0);
        doubleList.add(3.8);

        GenericList<Double> doubleGenericList = new GenericList<>(doubleList);
        doubleGenericList.setList(2.0);
        doubleGenericList.insertionSort();
        System.out.println("Sorted Double List: " + doubleGenericList.getList());
        System.out.println("Binary Search (1.0): " + doubleGenericList.binarySearch(1.0));

        // Testing with String
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("apple");
        stringList.add("banana");
        stringList.add("cherry");

        GenericList<String> stringGenericList = new GenericList<>(stringList);
        stringGenericList.setList("date");
        stringGenericList.insertionSort();
        System.out.println("Sorted String List: " + stringGenericList.getList());
        System.out.println("Binary Search ('banana'): " + stringGenericList.binarySearch("banana"));

        // Testing bin2Dec
        try {
            int decimalValue = bin2Dec("1010");
            System.out.println("Binary to Decimal: 1010 => " + decimalValue);
        } catch (BinaryFormatException e) {
            System.out.println("BinaryFormatException: " + e.getMessage());
        }
    }
}