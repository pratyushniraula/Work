import java.util.Scanner;
import java.util.ArrayList;

public class Main{
    
    //binary to decimal method
    public static int bin2Dec(String binaryString) throws BinaryFormatException {
        int decimal = 0;
        int power = 0;
        for (int i = binaryString.length() - 1; i >= 0; i--) {
            //if it is a 1 then do the adding for it
            if (binaryString.charAt(i) == '1') {
                decimal += Math.pow(2, power);
            }
            //if it is not a 1 or a 0 then throw an exception created in other class
            else if (binaryString.charAt(i) != '0') {
                throw new BinaryFormatException(binaryString);
            }
            power++;
        }
        return decimal;
    }
    
        public static void main (String[] args) {
        //variables initialized
        Scanner s = new Scanner(System.in);
        System.out.print("Enter a binary number: ");
        String binaryString = s.nextLine();
        
        //try catch block to see if bin2Dec throws an exception
        try {
            System.out.println("The decimal value of " + binaryString + " is " + bin2Dec(binaryString));
        }
        catch (BinaryFormatException ex) {
            System.out.println(ex.getMessage() + " is not a binary number.");
        }

        //test insertion sort
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(52);
        list.add(24);
        list.add(31);
        list.add(1000);
        list.add(42);
        GenericList<Integer> list1 = new GenericList<Integer>(list);
        
        list1.InsertionSort();
        System.out.println(list1.getList());


        //test binary search
        if (list1.BinarySearch(42)) {
            System.out.println("42 is in the list");
        }
        else {
            System.out.println("42 is not in the list");
        }
        if (list1.BinarySearch(100)) {
            System.out.println("100 is in the list");
        }
        else {
            System.out.println("100 is not in the list");
        }
        
        System.exit(0);
    }



}