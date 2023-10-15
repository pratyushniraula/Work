// import java.util.*;
// import java.io.*;
// import java.lang.*;
// public class Main{
//     //infix to postfix using stack
//     public static void main(String[] args){
//         Scanner s = new Scanner(System.in);
//         String file = s.nextLine();
//         File filename = new File(file);
//         Scanner fileScanner = null;
//         String postfix;
//         try {
//             fileScanner = new Scanner(filename);
//         } catch (FileNotFoundException e) {
//             System.out.println("File not found");
//         }
//         while(fileScanner.hasNextLine()){
//             //scans line
//             String line = fileScanner.nextLine();
            
//             //creates stack
//             Stack stack = new Stack();

//             for (int i = 0; i < line.length(); i++){
//                 if(line.charAt(i) <=57 && line.charAt(i) >= 48){
//                     System.out.print(line.charAt(i));
//                 }
//                 else if(line.charAt(i) == '('){
//                     stack.push(line.charAt(i), 0);
//                 }
//                 else if(line.charAt(i) == ')'){
//                     while(stack.peek() != '('){
//                         System.out.print(stack.pop().data);
//                     }
//                     stack.pop();
//                 }
//                 else if(line.charAt(i) == '+' || line.charAt(i) == '-'){
//                     while(stack.peek() == '+' || stack.peek() == '-' || stack.peek() == '*' || stack.peek() == '/'){
//                         System.out.print(stack.pop().data);
//                     }
//                     stack.push(line.charAt(i), 1);
//                 }
//                 else if(line.charAt(i) == '*' || line.charAt(i) == '/'){
//                     while(stack.peek() == '*' || stack.peek() == '/'){
//                         System.out.print(stack.pop().data);
//                     }
//                     stack.push(line.charAt(i), 2);
//                 }
//                 else if(line.charAt(i) == '^'){
//                     while(stack.peek() == '^'){
//                         System.out.print(stack.pop().data);
//                     }
//                     stack.push(line.charAt(i), 3);
//                 }
//             }
//             while (!(stack.isEmpty())){
//                 System.out.print(stack.pop().data);
//             }


//             }

            


//         }

// }

//pratyush niraula pxn210033: contributed to stack.java and main.java
//yash maheshwari yxm210016: contributed to node.java and main.java
import java.util.*;
import java.io.*;
import java.lang.*;
public class Main{
    //infix to postfix using stack
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        String file = s.nextLine();
        File filename = new File(file);
        Scanner fileScanner = null;
        String postfix;
        FileWriter fw = null;

        try{
            fw = new FileWriter("postfix.txt");
        }catch(IOException e){}

        try {
            fileScanner = new Scanner(filename);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        while(fileScanner != null || fileScanner.hasNextLine()){
            //scans line
            String line = fileScanner.nextLine();
            
            //creates stack
            Stack stack = new Stack();

            for (int i = 0; i < line.length(); i++){
                if(line.charAt(i) <=57 && line.charAt(i) >= 48){
                    
                    System.out.print(line.charAt(i));
                    try{
                        fw.write(line.charAt(i));
                    }catch(IOException e){}
                    
                }
                else if(line.charAt(i) == '('){
                    stack.push(line.charAt(i), 0);
                }
                else if(line.charAt(i) == ')'){
                    while(stack.peek() != '('){
                        System.out.print(stack.pop().data);
                        try{
                            if(stack.isEmpty()){//might be problem
                                break;
                            }
                            fw.write(stack.pop().data);
                        }catch(IOException e){}
                    }
                    stack.pop();
                }
                else if(line.charAt(i) == '+' || line.charAt(i) == '-'){
                    while(stack.peek() == '+' || stack.peek() == '-' || stack.peek() == '*' || stack.peek() == '/'){
                        System.out.print(stack.pop().data);
                        try{
                            if(stack.isEmpty()){//might be problem
                                break;
                            }
                            fw.write(stack.pop().data);
                        }catch(IOException e){
                            
                        }
                    }
                    stack.push(line.charAt(i), 1);
                }
                else if(line.charAt(i) == '*' || line.charAt(i) == '/'){
                    while(stack.peek() == '*' || stack.peek() == '/'){
                        System.out.print(stack.pop().data);
                        try{
                            fw.write(stack.pop().data);
                        }catch(IOException e){}
                    }
                    stack.push(line.charAt(i), 2);
                }
                else if(line.charAt(i) == '^'){
                    while(stack.peek() == '^'){
                        System.out.print(stack.pop().data);
                        try{
                            fw.write(stack.pop().data);
                        }catch(IOException e){}
                    }
                    stack.push(line.charAt(i), 3);
                }
            }
            while (!(stack.isEmpty())){
                System.out.print(stack.pop().data);
                try{
                    if(stack.isEmpty()){//might be problem
                        break;
                    }
                    else if(!(stack.isEmpty())){
                        fw.write(stack.pop().data);
                    }
                    
                }catch(IOException e){

                }
            }


            }
            
            s.close();
            // fw.close();
        }
        

}