import java.util.*;

public class Main{
    //bubble sort that returns the number of swaps and number of comparisons
    public static void bubbleSort(int[] arr){
        int n = arr.length;
        int count = 0;
        int comparisons = 0;
        for(int i=0;i<n-1;i++){
            for(int j=0;j<n-i-1;j++){
                comparisons++;
                if(arr[j]>arr[j+1]){
                    count++;
                    // swap arr[j] and arr[j+1]
                    int temp = arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }
        System.out.println("Number of swaps bubbleSort: "+count);
        System.out.println("Number of comparisons bubbleSort: "+comparisons);
    }
 
    //selection sort that returns the number of swaps
    public static void selectionSort(int[] arr){
        int n = arr.length;
        int comparisons = 0;
        int count = 0;
        for(int i=0;i<n-1;i++){
            int min_idx = i;
            for(int j=i+1;j<n;j++){
                comparisons++;
                if(arr[j]<arr[min_idx]){
                    min_idx = j;
                    count++;
                }
            }
            // swap arr[min_idx] and arr[i]
            int temp = arr[min_idx];
            arr[min_idx]=arr[i];
            arr[i]=temp;
        }
        System.out.println("Number of swaps selectionSort: "+count);
        System.out.println("Number of comparisons selectionSort: "+comparisons);
    }
    //insertion sort that returns the number of swaps
    public static void insertionSort(int[] arr){
        int n = arr.length;
        int count = 0;
        int comparisons = 0;
        for(int i=1;i<n;i++){
            int key = arr[i];
            int j = i-1;
            while(j>=0 && arr[j]>key){
                arr[j+1]=arr[j];
                j=j-1;
                comparisons++;
                count++; // increment count here
            }
            arr[j+1]=key;
        }
        System.out.println("Number of swaps insertionSort: "+count);
        System.out.println("Number of comparisons insertionSort: "+comparisons);
    }

    //quick sort that returns the number of swaps and comparisons for the array it sorted
    public static int[] quickSort(int[] arr, int low, int high){
        int count = 0;
        int comparisons = 0;
        if(low<high){
            int[] result = partition(arr,low,high);
            count += result[1];
            comparisons += result[2];
            int[] left = quickSort(arr,low,result[0]-1);
            int[] right = quickSort(arr,result[0]+1,high);
            count += left[0] + right[0];
            comparisons += left[1] + right[1];
        }
        return new int[]{count, comparisons};
    }

    public static int[] partition(int[] arr, int low, int high){
        int pivot = arr[high];
        int i = low-1;
        int count = 0;
        int comparisons = 0;
        for(int j=low;j<high;j++){
            comparisons++;
            if(arr[j]<pivot){
                i++;
                //swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i]=arr[j];
                arr[j]=temp;
                count++;
            }
        }
        //swap arr[i+1] and arr[high]
        int temp = arr[i+1];
        arr[i+1]=arr[high];
        arr[high]=temp;
        count++;
        return new int[]{i+1, count, comparisons};
    }


    //merge sort that returns the number of swaps and comparisons for the linked list it sorted
    public static int[] mergeSort(LinkedList<Integer> list){
        int count = 0;
        int comparisons = 0;
        if(list.size()>1){
            LinkedList<Integer> left = new LinkedList<Integer>();
            LinkedList<Integer> right = new LinkedList<Integer>();
            int mid = list.size()/2;
            for(int i=0;i<mid;i++){
                left.add(list.get(i));
            }
            for(int i=mid;i<list.size();i++){
                right.add(list.get(i));
            }
            count += mergeSort(left)[0];
            comparisons += mergeSort(left)[1];
            count += mergeSort(right)[0];
            comparisons += mergeSort(right)[1];
            count += merge(left,right,list)[0];
            comparisons += merge(left,right,list)[1];
        }
        return new int[]{count, comparisons};
    }

    public static int[] merge(LinkedList<Integer> left, LinkedList<Integer> right, LinkedList<Integer> list){
        int count = 0;
        int comparisons = 0;
        int i = 0;
        int j = 0;
        int k = 0;
        while(i<left.size() && j<right.size()){
            comparisons++;
            if(left.get(i)<=right.get(j)){
                list.set(k,left.get(i));
                i++;
            }
            else{
                list.set(k,right.get(j));
                j++;
            }
            k++;
            count++;
        }
        while(i<left.size()){
            list.set(k,left.get(i));
            i++;
            k++;
            count++;
        }
        while(j<right.size()){
            list.set(k,right.get(j));
            j++;
            k++;
            count++;
        }
        return new int[]{count, comparisons};
    }

    public static void main(String[] args){
        int n = 5000;
        int[] arr= new int[n];
        Random rand = new Random();
        for(int i=0;i<n;i++){
            arr[i] = rand.nextInt(100000);
        }
        long startTime = System.nanoTime();

        //make 5 copies of the array
        int[] arr1 = Arrays.copyOf(arr,arr.length);
        int[] arr2 = Arrays.copyOf(arr,arr.length);
        int[] arr3 = Arrays.copyOf(arr,arr.length);
        int[] arr4 = Arrays.copyOf(arr,arr.length);

        //make a linked list of the array
        LinkedList<Integer> list = new LinkedList<Integer>();
        for(int i=0;i<n;i++){
            list.add(arr[i]);
        }

        //bubble sort
        bubbleSort(arr1);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Time taken for bubbleSort: "+duration + "\n");

        //selection sort
        startTime = System.nanoTime();
        selectionSort(arr2);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Time taken for selectionSort: "+duration + "\n");

        //insertion sort
        startTime = System.nanoTime();
        insertionSort(arr3);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Time taken for insertionSort: "+duration + "\n");

        //quick sort
        startTime = System.nanoTime();
        int count = quickSort(arr4,0,arr4.length-1)[0];
        int comparisons = quickSort(arr4,0,arr4.length-1)[1];

        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Number of swaps quickSort: "+count);
        System.out.println("Number of comparisons quickSort: "+comparisons);
        System.out.println("Time taken for quickSort: "+duration + "\n");
 

        //merge sort
        startTime = System.nanoTime();
        count = mergeSort(list)[0];
        comparisons = mergeSort(list)[1];
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Number of swaps mergeSort: "+count);
        System.out.println("Number of comparisons mergeSort: "+comparisons);
        System.out.println("Time taken for mergeSort: "+duration + "\n");







    }
}