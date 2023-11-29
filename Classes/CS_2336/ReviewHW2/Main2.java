package ReviewHW2;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;



public class Main2{
    public static class graph{
        private int adjacenyMatrix[][];
        private int currentSize;
        private int maxSize;

        //mutators and accessors
        public int[][] getAdjacenyMatrix(){
            return this.adjacenyMatrix;
        }
        public void setAdjacenyMatrix(int[][] adjacenyMatrix){
            this.adjacenyMatrix = adjacenyMatrix;
        }
        public int getCurrentSize(){
            return this.currentSize;
        }
        public void setCurrentSize(int currentSize){
            this.currentSize = currentSize;
        }
        public int getMaxSize(){
            return this.maxSize;
        }
        public void setMaxSize(int maxSize){
            this.maxSize = maxSize;
        }
        // public void addEdge(int i, int j) {
        //     adjacenyMatrix[i][j] = 1;
        //     adjacenyMatrix[j][i] = 1;
        // }

        //constructors
        public graph(){
            this.maxSize = 10;
            this.currentSize = 0;
            this.adjacenyMatrix = new int[maxSize][maxSize];
        }
        public graph (int maxSize){
            this.maxSize = maxSize;
            this.currentSize = 0;
            this.adjacenyMatrix = new int[maxSize][maxSize];
        }

        public void createGraph(String filename) {
            File file = new File(filename);
            try {
                Scanner scanner = new Scanner(file);

                while (scanner.hasNextLine()) {
                    String[] vertices = scanner.nextLine().trim().split(" ");
                    int startVertex = Integer.parseInt(vertices[0])-1;
                    for (int i = 1; i < vertices.length; i++) {
                        int endVertex = Integer.parseInt(vertices[i])-1;
                        //addEdge(startVertex, endVertex);
                        adjacenyMatrix[startVertex][endVertex] = 1;
                        adjacenyMatrix[endVertex][startVertex] = 1;
                    }
                }

                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + e.getMessage());
            }
        }
    }


    public static void main(String[] args){
        System.out.println("Enter the name of the file: ");
        Scanner s = new Scanner(System.in);
        String fileName = s.nextLine();
        File file = new File(fileName);
        graph g;
        Scanner fileScanner;
        //open file
        try{
            fileScanner = new Scanner(file);
            //read first line and store it as the max size
            int maxSize = fileScanner.nextInt();
            //create graph with max size
            g = new graph(maxSize);

            while (fileScanner.hasNext()){


            }
        }
        catch(FileNotFoundException noFile){
            //if file cannot be found, print this
            System.out.println("File not found");
        }
    }
}