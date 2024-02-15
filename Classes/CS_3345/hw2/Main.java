import java.util.*;
import java.io.*;
import java.lang.*;
import java.lang.reflect.Array;
//pxn210033
//pratyush niraula


public class Main {

    //a complement b
    //QUESTION 1
    public static ArrayList<Integer> complement(int[] a, int[] b, ArrayList<Integer> c) {

        for (int i = 0; i < a.length; i++) {
            boolean found = false;
            for (int k = 0; k < b.length; k++) {
                if (a[i] == b[k]) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                c.add(a[i]);
            }
        }
        return c;
    }

    //LEMON
    //QUESTION 2
    /* ANSWER TO PART A  */
    public static int Lemon(int m, int n){
        int winner;
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++){
            list.add(i);
        }
    
        int newLemonHolder = 0;
        while(list.size() > 1){
            newLemonHolder = (newLemonHolder + m) % list.size();
            list.remove(newLemonHolder);
            
            System.out.println(list);
        }
        winner = list.get(0);
        //empty list
        list = null;
    
        return winner;
    }

    /* ANSWER TO PART B  
     *
     * 
     * 
     * the runtime of this is O(n^2) since the while loop takes O(n) and the remove method inside 
     * the loop takes O(n) time as well. There is the initial for loop to consider as well but it
     * it does not impact the calculation of O(n^2) since it is not nested inside the while loop.
     * 
     * 
     * 
    */

    //balance symbol
    //QUESTION 3
    public static boolean balanceSymbol(String s){
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);

            if (c == '(' || c == '[' || c == '{'){
                stack.push(c);
            }
            else if (c == ')' || c == ']' || c == '}'){
                if (stack.isEmpty()){
                    return false;
                }

                char match = stack.pop();
                
                if (c == ')' && match != '('){
                    return false;
                }
                if (c == ']' && match != '['){
                    return false;
                }
                if (c == '}' && match != '{'){
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    //Stack with minimum
    //QUESTION 4
    /*
     * a data structure that can be used to have stack features like push and pop
     * while also having the feature of having function to find the minimum value
     * in the stack is a stack that is implemented with a linked list. In this case
     * the linked list will have an extra pointer to the minimum value in the stack.
     * When the stack is created the minimum value is set to the first value pushed.
     * when a new value is pushed into the stack, the new value is compared to the
     * current minimum value in the stack. If the new value is less than the current
     * minimum value, the new value becomes the new minimum value but is still pushed
     * into the stack. When a value is popped from the stack and it is the minimum value
     * in the stack then the next value for min is searched for in the stack. Push will 
     * make the new node the head node while making the old head the next value for the new
     * head. Pop will remove the head node and make the next value the new head node.
     * 
     */

    //PostOrderTraversal
    //QUESTION 5
    /*
     * //tried to implement with the class TreeNode but ide couldn't find the class, so here is the traversal
     * 
     * 
     * public static void PostOrderTraversal(TreeNode root){
     *    if (root == null){
     *      return null;
     *    }
     *    PostOrderTraversal(root.left);
     *   PostOrderTraversal(root.right);
     *  System.out.println(root.data);
     * 
     */



    public static void main(String[] args) {
        int[] a = {1, 2, 3, 3, 4, 5};
        int[] b = {3, 4, 6, 6, 7};
        //int[] c  = new int[a.length];
        ArrayList<Integer> c = new ArrayList<>();
        complement(a, b, c);

        System.out.println("Problem 1:");
        System.out.print("{");
        for (int i = 0; i < c.size(); i++) {
            System.out.print(c.get(i) + " ");
        }
        System.out.print("}");

        System.out.println("\n\nProblem 2:");
        System.out.println(Lemon(1, 5));
        System.out.println(Lemon(0, 4));

        System.out.println("\nProblem 3:");
        System.out.println(balanceSymbol("()[]{}"));
        System.out.println(balanceSymbol("(]"));
        System.out.println(balanceSymbol("([)]"));
        System.out.println(balanceSymbol("{[]}"));

    }
}
