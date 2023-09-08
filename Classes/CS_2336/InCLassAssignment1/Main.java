// Name: Ahmed Elghazi NETID: AXE210038
// Name: Pratyush Niraula NETID: PXN210033
import java.util.Scanner;

/*********************************************************************************************************
**********************************************************************************************************
this project was a 2 person effort and so credit goes to Ahmed Elghazi for the functionality of the code 
**********************************************************************************************************
**********************************************************************************************************/

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String Equation = in.nextLine();
        String numFirst = Equation.substring(0,Equation.indexOf('x'));
        String numSecond = Equation.substring(Equation.indexOf('x') + 3,Equation.lastIndexOf('x'));
        String numThird = Equation.substring(Equation.indexOf(numSecond) + 3,Equation.indexOf('='));

        QuadraticEquation AP = new QuadraticEquation(Integer.parseInt(numFirst), Integer.parseInt(numSecond), Integer.parseInt(numThird));

        boolean imaginary;
        if(AP.getDiscriminant() < 0){
            imaginary = true;
        }
        else{
            imaginary = false;
        }
        if(AP.getRoot1() == 0 && imaginary){ // last point on instructions about 0 root or discriminant negative is confusing
            System.out.println("One root: " + AP.getRoot2());
        }
        else if(AP.getRoot2() == 0 && imaginary){
            System.out.println("One root: " + AP.getRoot1());
        }
        else{
            System.out.println("Two roots: " + AP.getRoot1() + " " + AP.getRoot2());
        }
    }
}