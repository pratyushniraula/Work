package hw3;
import java.util.*;
import java.io.*;
import java.lang.*;

public class AvlNode{
    int data;
    int height;
    AvlNode left;
    AvlNode right;

    public AvlNode(int data){
        this.data = data;
        this.height = 1;
        this.left = null;
        this.right = null;
    }

    public AvlNode(int data, AvlNode left, AvlNode right){
        this.data = data;
        this.height = 1;
        this.left = left;
        this.right = right;
    }

    public AvlNode(int data, AvlNode left, AvlNode right, int height){
        this.data = data;
        this.height = height;
        this.left = left;
        this.right = right;
    }

    //Design a linear-time algorithm that verifies T.
    //Check that T is a binary search tree. Check that height of each node is correct. Check that each node is balanced.
    public static boolean verifyAvlTree(AvlNode T){
        if(T == null){
            return true;
        }
        if(T.left != null && T.left.data > T.data){
            return false;
        }
        if(T.right != null && T.right.data < T.data){
            return false;
        }
        int leftHeight = 0;
        int rightHeight = 0;

        if (T.left != null) {
            leftHeight = T.left.height;
        }
        if (T.right != null) {
        rightHeight = T.right.height;
        }
       if (T.height != 1 + Math.max(leftHeight, rightHeight)) {
            return false; // The height of T is incorrect
        }

        // Check if T is balanced
        int balanceFactor = Math.abs(leftHeight - rightHeight);
        if (balanceFactor > 1) {
            return false; // T is not balanced
        } 
        return verifyAvlTree(T.left) && verifyAvlTree(T.right);
    }

    


}