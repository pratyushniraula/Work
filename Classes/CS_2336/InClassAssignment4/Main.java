package InClassAssignment4;

import java.util.*;
import java.lang.*;

public class Main{
    public static void main(String[] args) {
        BinaryTree bint = new BinaryTree();
        Scanner s = new Scanner(System.in);

        int i = 0;
        while(i < 10){
            System.out.println("write number: ");
            int b = s.nextInt();
            Node n = new Node(b);
            bint.insert(n);
        }
    }

}