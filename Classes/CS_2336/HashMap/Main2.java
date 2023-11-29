// Pratyush Niraula
//pxn210033
import java.util.Scanner;

public class Main2{

    private static class Hashmap{
        private int[] arr; // array to store values
        private int size; // size of array
        private int count; // number of elements in array
        private final double LOAD_FACTOR = 0.5; // load factor

        // overloaded constructor
        public Hashmap(int size){
            this.size = size;
            this.count = 0;
            arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = -1;
            }
        }

        // default constructor
        public Hashmap(){
            this(11);
        }

        // quadratic probing
        public void insert(int value){
            // rehash if load factor is greater than 0.5
            if (count / (double)size >= LOAD_FACTOR) {
                rehash();
            }
            // insert value
            int index = value % size;
            // quadratic probing
            int ogindex = index;
            int i = 0;
            // find next empty spot
            while(arr[index] != -1){
                // quadratic probing
                i++;
                index = (ogindex + (i * i)) % size;
            }
            // insert value
            arr[index] = value;
            count++;
        }

        public void print(){
            // print hashmap
            for(int i = 0; i < size; i++){
                if (arr[i] == -1) {
                    // print empty spot
                    System.out.print("_ ");
                } else {
                    // print value
                    System.out.print(arr[i] + " ");
                }
            }
            //get a newline
            System.out.println();
        }

        public void rehash(){
            int oldSize = size;
            // double size and add 1 to make it prime
            size = 2 * oldSize + 1;
            int[] oldArr = arr;
            //make a new array and make all elements -1
            arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = -1;
            }
            count = 0;
            // insert all values from old array
            for(int i = 0; i < oldSize; i++){
                if(oldArr[i] != -1){
                    insert(oldArr[i]);
                }
            }
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Hashmap map = new Hashmap();
        int n = sc.nextInt();
        for(int i = 0; i < n; i++){
            int value = sc.nextInt();
            map.insert(value);
        }
        map.print();
        sc.close();
    }
}