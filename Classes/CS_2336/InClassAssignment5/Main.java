//Pratyush Niraula: pxn210033
//Ayusha Timalsena: axt210078

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static class Graph {
        private int[][] adjMatrix; // adjacency matrix
        private int max; // max number of vertices
        private int current; // current number of vertices

        public Graph(int max) {
            this.max = max;
            this.adjMatrix = new int[max][max];
            this.current = 0;
        }
        public Graph(){
            this.max = 0;
            this.adjMatrix = new int[max][max];
            this.current = 0;
        }

        public void addEdge(int i, int j) {
            adjMatrix[i][j] = 1;
            adjMatrix[j][i] = 1;
        }

        public boolean isBipartite() {
            int[] colors = new int[max];
            Arrays.fill(colors, -1); // -1 represents colorless
        
            for (int i = 0; i < max; i++) {
                if (colors[i] == -1) {
                    if (!isBipartiteHelper(i, colors)) {
                        return false; // not a bipartite graph
                    }
                }
            }
            return true; // if the loop finishes, it's a bipartite graph
        }
        
        private boolean isBipartiteHelper(int vert, int[] colors) {
            Queue<Integer> queue = new LinkedList<>();
            queue.add(vert);
            colors[vert] = 1; // 1 represents red
        
            while (!queue.isEmpty()) {
                int u = queue.poll();
        
                for (int v = 0; v < max; v++) {
                    if (adjMatrix[u][v] == 1) {
                        if (colors[v] == -1) {
                            colors[v] = 3 - colors[u]; 
                            queue.add(v);
                        } else if (colors[v] == colors[u]) {
                            return false; 
                        }
                    }
                }
            }
            return true;
        }
        // Method to create a graph from a file
        public void createGraph(String filename) {
            File file = new File(filename);
            try {
                Scanner scanner = new Scanner(file);

                while (scanner.hasNextLine()) {
                    String[] vertices = scanner.nextLine().trim().split(" ");
                    int startVertex = Integer.parseInt(vertices[0])-1;
                    for (int i = 1; i < vertices.length; i++) {
                        int endVertex = Integer.parseInt(vertices[i])-1;
                        addEdge(startVertex, endVertex);
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
        String filename = scanner.nextLine();
        scanner.close();
        File file = new File(filename);
        int max = 0;
        try {
            Scanner fileScanner = new Scanner(file);
            max = fileScanner.nextInt();
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

        Graph graph = new Graph(max);
        graph.createGraph(filename);
        if (graph.isBipartite()) {
            System.out.println("The graph is bipartite");
        } else {
            System.out.println("The graph is not bipartite");
        }
    }
}