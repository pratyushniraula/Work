import java.util.Scanner;

public class Main{

    private static class Hashmap{
        private int[] arr = new int[11];
        private int size = 11;

        public Hashmap(int size){
            this.size = size;
            arr = new int[size];
        }
        public Hashmap(){
            this.size = 11;
            arr = new int[size];
        }

        //quadradic probing
        public void insert(int value){
            int index = value % size;
            int i = 1;
            while(arr[index] != 0){
                index = (index + i * i) % size;
                i++;
            }
            arr[index] = value;
        }

        public void delete(int value){
            int index = value % size;
            arr[index] = 0;
        }

        public boolean search(int value){
            int index = value % size;
            return arr[index] == value;
        }

        public void print(){
            for(int i = 0; i < size; i++){
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
        public void rehash(){
            int[] temp = arr;
            arr = new int[size * 2 + 1];
            for(int i = 0; i < size; i++){
                if(temp[i] != 0){
                    insert(temp[i]);
                }
            }
            size = size * 2 + 1;
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int LoopIterations = sc.nextInt();
        Hashmap map = new Hashmap();
        int n = sc.nextInt();
        for(int i = 0; i < n; i++){
            int value = sc.nextInt();
            map.insert(value);
            if(i == map.size - 1){
                map.rehash();
            }
        }
        map.print();
        sc.close();
    }
}