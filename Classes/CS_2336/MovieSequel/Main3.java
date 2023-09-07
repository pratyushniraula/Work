import java.io.File;
import java.io.FileNotFoundException;

import java.io.PrintWriter;
import java.util.Scanner;


public class Main{
    
    public static void displayAuditorium(String[][] auditorium, int row, int col){
        System.out.print("    ");

        // Print the column letter
        for(int i = 0; i < col; i++){
            char j = (char) (i + 65);
            System.out.print(j);
        }
        System.out.println();

        //print the auditorium
        for(int i = 0; i < row; i++){
            System.out.print(i + 1 + "   ");
            for(int j = 0; j < col; j++){
                if(auditorium[i][j].equals("A") || auditorium[i][j].equals("S") || auditorium[i][j].equals("C")){
                    System.out.print("#");
                }
                else{
                    System.out.print(auditorium[i][j]);
                }
            }
            System.out.println();
        }


    }
    // public static int bestAvailable(String[][] auditorium, int row, int colIndex, int adult, int senior, int child){
    //     int total = adult + senior + child;
    //     int bestSeat = -1;
    //     int bestV = colIndex+1;
    //     int v = colIndex+1;
    //     int middle;
    //     // come back to best available later
    // }

    // public static Boolean checkSeat(String[][] auditorium, int row, int seat, int adult, int senior, int child){


    // }

    public static void reserveSeats(String[][] auditorium, int row, int colIndex, int seat, int adult, int senior, int child){
        
    }
    
    public static void displayReport(){
        int adult = 0;
        int senior = 0;
        int child = 0;
        int total = 0;
    }
    
    public static void main(String[] args) {
        String [][]auditorium = new String[10][26];
        int row = 0;
        int col = -1;
        //get input file
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the name of the file: ");
        String fileName = s.nextLine();
        File file = new File(fileName);
        try{
            Scanner fileScanner = new Scanner(file);
            //get the auditorium
            while (fileScanner.hasNext()){
                String line = fileScanner.next();
                col = line.length();
                for(int j = 0; j < col; j++){
                    auditorium[row][j] = line.substring(j, j+1);
                }
                row++;
            }


            displayAuditorium(auditorium, row, col);
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
        }

        
        
        //displayAuditorium(auditorium, row);
    }
    



}