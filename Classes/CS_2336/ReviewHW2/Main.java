
import java.io.File;
import java.io.FileNotFoundException;
// import java.util.Arrays;
// import java.util.LinkedList;
// import java.util.Queue;
import java.util.Scanner;

public class Main {
    private static class Graph {
        private int[][] adjMatrix; // adjacency matrix
        private int max; // max number of vertices
        private int current; // current number of vertices

        //constructor
        public Graph(int max) {
            this.max = max;
            this.adjMatrix = new int[max][max];
            this.current = 0;
        }
        //default constructor
        public Graph(){
            this.max = 10;
            this.adjMatrix = new int[max][max];
            this.current = 0;
        }

        //method to check if strongly connected
        public boolean isConnected() {
            //create an array to keep track of visited vertices
            boolean[] visited = new boolean[max];
            //start DFS from the first vertex
            visited = dfs(0, visited);
            //if all vertices have been visited
            for (int i = 0; i < max; i++) {
                //if a vertex has not been visited
                if (!visited[i]) {
                    return false;
                }
            }
            //if all vertices have been visited
            return true;
        }
        
        //method to check if strongly connected
        private boolean[] dfs(int vertex, boolean[] visited) {
            //mark the current vertex as visited
            visited[vertex] = true;
            //for each vertex in the graph
            for (int i = 0; i < max; i++) {
                //if the vertex is adjacent to the current vertex and has not been visited
                if (adjMatrix[vertex][i] == 1 && !visited[i]) {
                    //recursively visit the adjacent vertex
                    dfs(i, visited);
                }
            }
            return visited;
        }


        // Method to create a graph from a file
        public void createGraph(String filename) {
            File file = new File(filename);
            try {
                Scanner scanner = new Scanner(file);

                //while there is another line in the file
                while (scanner.hasNextLine()) {
                    //read in the next line
                    String[] vertices = scanner.nextLine().trim().split(" ");
                    //for each vertex in the line
                    int startVertex = Integer.parseInt(vertices[0])-1;
                    //add the vertex to the graph
                    for (int i = 1; i < vertices.length; i++) {
                        //add the vertex to the graph
                        int endVertex = Integer.parseInt(vertices[i])-1;
                        adjMatrix[startVertex][endVertex] = 1;
                        adjMatrix[endVertex][startVertex] = 1;

                    }
                }

                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
       //open file and read the first int at the top
        //create a graph with that many vertices
        System.out.println("filename: ");
        Scanner scanner = new Scanner(System.in);
        //scan in the file name
        String filename = scanner.nextLine();
        scanner.close();
        File file = new File(filename);
        //set the max number of vertices to zero initially
        int max = 0;
        try {
            Scanner fileScanner = new Scanner(file);
            //set the max number of vertices to the first int in the file
            max = fileScanner.nextInt();
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        //create a graph with the max number of vertices
        Graph graph = new Graph(max);
        //create the graph from the file
        graph.createGraph(filename);
        //check if the graph is connected
        if (graph.isConnected()) {
            System.out.println("connected");
        } else {
            System.out.println("not connected");
        }
    }
}