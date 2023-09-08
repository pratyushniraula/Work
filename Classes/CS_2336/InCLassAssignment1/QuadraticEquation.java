import java.lang.Math;
// Name: Ahmed Elghazi NETID: AXE210038
// Name: Pratyush Niraula NETID: PXN210033

/*********************************************************************************************************
**********************************************************************************************************
this project was a 2 person effort and so credit goes to Ahmed Elghazi for the functionality of the code 
**********************************************************************************************************
**********************************************************************************************************/

public class QuadraticEquation {
    private int a;
    private int b;
    private int c;
    public QuadraticEquation(){
        a = 0;
        b = 0;
        c = 0;
    }
    public QuadraticEquation(int num1, int num2, int num3){
        a = num1;
        b = num2;
        c = num3;
    }
    public int getA(){
        return this.a;
    }
    public int getB(){
        return this.b;
    }
    public int getC(){
        return this.c;
    }

    public double getDiscriminant(){
        return (Math.pow(b,2) - (4*a*c));
    }

    public double getRoot1(){
        if(this.getDiscriminant() < 0){
            return 0;
        }
        return ((-1*b) + Math.sqrt(this.getDiscriminant()))/(2*a);

    }

    public double getRoot2(){
        if(this.getDiscriminant() < 0){
            return 0;
        }
        return ((-1*b) - Math.sqrt(this.getDiscriminant()))/(2*a);
    }

}
