import java.lang.*;
import java.util.*;

public class Main {
    public static int[] binarySearch(int []arr, int target){
        //variable declaration
        int[] range = {-1,-1};
        int left = 0;
        int right = arr.length-1;

        //call the helper function
        range = binarySearchRecursion(arr, target, left, right);
        System.out.println("return = [" + range[0] + ", " + range[1] + "]");
        //return the range
        return range;
    }


    private static int[] binarySearchRecursion(int[] arr, int target, int left, int right){
        //variable declaration for each recursive call
        int[] range = {-1,-1};

        //base case
        if(left > right){
            return range;
        }

        //recursive case
        //find the middle
        int middle = left + (right - left) / 2;

        //if statements for where target is
        if(arr[middle] == target){
            //if target is found, find the left and right range to find duplicates as well as the range
            int[] leftRange = binarySearchRecursion(arr, target, left, middle - 1);
            int[] rightRange = binarySearchRecursion(arr, target, middle + 1, right);

            //if leftRange is not -1, then the left range is the left range of leftRange
            if(leftRange[0] != -1 && leftRange[0] <= range[0]){
                range[0] = leftRange[0];
            }
            //if leftRange is -1, then the left range is the mid of the current recursive call
            else{
                range[0] = middle;
            }
            
            //if rightRange is not -1, then the right range is the right range of rightRange
            if(rightRange[1] != -1 && rightRange[1] >= range[1]){
                range[1] = rightRange[1];
            }
            //if rightRange is -1, then the right range is the mid of the current recursive call
            else{
                range[1] = middle;
            }
            //return the range of the duplicates
            return range;
        }
        
        else if(arr[middle] < target){
            //if target is greater than mid, search the right side
            return binarySearchRecursion(arr, target, middle+1, right);
        }
        
        else if (arr[middle] > target){
            //if target is less than mid, search the left side
            return binarySearchRecursion(arr, target, left, middle-1);
        }
        
        else{
            //if target is not found, return -1
            return range;
        }
    }





    public static void main(String[] args) {
        int[] arr = {0, 1, 1, 2, 21, 33, 45, 45, 61, 71, 93};
        int target = 1;
        binarySearch(arr, target);
    }
}