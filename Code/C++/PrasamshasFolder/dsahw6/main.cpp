//
// ========================================
// HW7: Implement Graph, Graph Traverse
//       and Dijkstra's algorithm. 
// ========================================
// 
// In this assignment, we will implement an 
// undirected graph, two traverse algorithms 
// and the Dijkstra's algorithm. 
//  
// This template is based on matrix-based 
// graph implementation, but you are free 
// to workn list-based graph implementation. 
// 
// For simplicity, the template assumes no 
// satellite data is used and focuses on 
// dealing with integers (node numbers) only. 
// 
// Finally, feel free to modify the given template 
// including the classes, functions, etc. Just make 
// sure the outputs of functions tested in "main" 
// stay the same (for auto-grading). 
// 

#include <iostream>
using namespace std;

class Result {
public:
    int length; 
    int weight;
    int* path;
};

// Please omplete the graph class. 
// Remember this is an undirected graph 
// of "size" nodes labeled from 0 to size-1. 
class Graph {

private:

    // this variable holds the matrix 
    int** m;

    // this variable holds the number of nodes in graph 
    int size;

public:

    // this function returns the degree of node i 
    int Degree(int i);


    // this function adds an edge between node i and 
    // node j, and assigns a weight "w" to the edge 
    // 
    // recall: if we do not want a weighted graph, 
    // simply set w = 1 for all edges 
    //
    // you may also check boundary but in the testing 
    // samples we assume all inputs are within boundary 
    void Add(int i, int j, int w);


    // this function returns 1 if node i and 
    // node j are connected and returns 0 otherwise.
    // 
    // note: you can also let it return the weight;  
    //       weight is zero if no edge exists 
    int IsEdge(int i, int j);


    // this function returns 1 if there is a path 
    // from node i to node j and returns 0 otherwise.  
    int IsPath(int i, int j);


    // this function performs depth-first search 
    // starting at node i. break any tie based on 
    // node number (smaller node goes first) e.g., 
    // if you can pick node 2 or node 3, pick 2. 
    // at last, this function should return an array 
    // of size n holding the traverse sequence of nodes. 
    // (Assume input graph is always connected.)
    int* DFT(int i);


    // this function performs breadth-first search 
    // starting at node i. when exploring neighbors 
    // of a set of nodes, explore them based on the 
    // order of nodes in the queue. 
    // 
    // this means once you pop a node from the queue, 
    // add its neighbors to the queue. (here, break 
    // ties based on neighbor node numbers -- smaller 
    // node gets added to the queue first)
    // 
    // at last, this function should return an array 
    // of size n holding the traverse sequence of nodes. 
    int* BFT(int i);

    // 
    // The following performs the Dijkstra's algorithm
    // to find the shorest path from node i to node j.  
    // 
    // It returns address of an object of the 
    // Result class, which contains three 
    // public variables (see definition at top): 
    // (i) int length: length of the shorest path 
    // (ii) int weight: total weight of the shortest path
    // (iii) int *path: array of nodes on the path 
    // Example: 
    // If the shortest path is 2 -> 3 -> 0, and 
    // weight on (2,3) is 5 and weight on (3,0) is 1, 
    // then path[0] = 2, path[1] = 3, path[2] = 0
    // and length = 3 and weight = 6. 
    // 
    Result* Dijkstra(int i, int j);


    // this is the constructor with input arguments 
    // remember to allocate space for matrix "m" and 
    // initialize all entries to zero 
    // 
    // Initialization is important because in "main" 
    // we only add edges to the matrix and assume its 
    // rest entries are zeros. 
    Graph(int n);
};

Graph::Graph(int n) {
    size = n;
    // allocate space for matrix
    m = new int*[size];
    // initialize all entries to zero
    for (int i = 0; i < size; i++) {
        // allocate space for row i
        m[i] = new int[size];
        // initialize row i
        for (int j = 0; j < size; j++) {
            //all entries are zero initially
            m[i][j] = 0; // initialize all edges to 0 (no edge)
        }
    }
}

int Graph::Degree(int i) {
    // count non-zero entries in row i
    int deg = 0;
    // scan row i
    for (int j = 0; j < size; j++) {
        // count non-zero entries
        if (m[i][j] != 0) {
            // found an edge
            deg++;
        }
    }
    return deg;
}

void Graph::Add(int i, int j, int w) {
    // add edge between i and j with weight w
    m[i][j] = w;
    m[j][i] = w;
}

int Graph::IsEdge(int i, int j) {
    // check if there is an edge between i and j
    if (m[i][j] != 0) {
        return 1;
    } else {
        return 0;
    }
}

int Graph::IsPath(int i, int j) {
    // BFS from i, see if we ever reach j
    bool* visited = new bool[size];
    // initialize visited array
    for (int k = 0; k < size; k++){
        visited[k] = false;
    }

    // initialize queue
    int* queue = new int[size];
    int front = 0, back = 0;

    // start from node i
    visited[i] = true;
    queue[back++] = i;

    // BFS loop
    while (front < back) {
        // dequeue
        int u = queue[front++];
        // check if we reached j
        if (u == j) {
            delete[] visited;
            delete[] queue;
            return 1;
        }
        // enqueue unvisited neighbors
        for (int v = 0; v < size; v++) {
            if (m[u][v] != 0 && !visited[v]) {
                visited[v] = true;
                queue[back++] = v;
            }
        }
    }

    // did not reach j
    delete[] visited;
    delete[] queue;
    return 0;
}

int* Graph::DFT(int i) {
    // iterative DFS using stack
    int* order = new int[size];
    // initialize visited array
    bool* visited = new bool[size];
    // initialize all to false
    for (int k = 0; k < size; k++){
        visited[k] = false;
    }

    // initialize stack
    int* stack = new int[size];
    int top = -1;
    int idx = 0;

    // start from node i
    stack[++top] = i;

    // DFS loop
    while (top >= 0) {
        // pop
        int u = stack[top--];

        // visit if not visited
        if (!visited[u]) {
            // mark visited
            visited[u] = true;
            // record in order
            order[idx++] = u;

            // push neighbors in *reverse* order so smaller node is processed first
            for (int v = size - 1; v >= 0; v--) {
                // check for edge and unvisited
                if (m[u][v] != 0 && !visited[v]) {
                    // push to stack
                    stack[++top] = v;
                }
            }
        }
    }
    // delete and deallocate
    delete[] visited;
    delete[] stack;
    return order;
}

int* Graph::BFT(int i) {
    // BFS using queue
    int* order = new int[size];
    // initialize visited array
    bool* visited = new bool[size];
    // initialize all to false
    for (int k = 0; k < size; k++){
        visited[k] = false;
    }

    // initialize queue
    int* queue = new int[size];
    int front = 0, back = 0;
    int idx = 0;

    // start from node i
    visited[i] = true;
    queue[back++] = i;

    // BFS loop
    while (front < back) {
        // dequeue
        int u = queue[front++];
        // record in order
        order[idx++] = u;

        // visit neighbors in increasing node number order
        for (int v = 0; v < size; v++) {
            // check for edge and unvisited
            if (m[u][v] != 0 && !visited[v]) {
                // mark visited and enqueue
                visited[v] = true;
                // enqueue
                queue[back++] = v;
            }
        }
    }
    // delete and deallocate
    delete[] visited;
    delete[] queue;
    return order;
}

Result* Graph::Dijkstra(int i, int j) {
    // big number to make infinity
    const int INF = 1000000000;

    // initialize dist, used, prev arrays
    int* dist = new int[size];
    bool* used = new bool[size];
    int* prev = new int[size];

    // set initial values
    for (int k = 0; k < size; k++) {
        dist[k] = INF;
        used[k] = false;
        prev[k] = -1;
    }

    // distance to source is 0
    dist[i] = 0;

    // Dijkstra using adjacency matrix
    for (int count = 0; count < size; count++) {
        // find unvisited node with smallest dist
        int u = -1;
        int bestDist = INF;

        // pick unvisited node with smallest dist
        for (int v = 0; v < size; v++) {
            // check if unvisited and better dist
            if (!used[v] && dist[v] < bestDist) {
                bestDist = dist[v];
                u = v;
            }
        }
        // if no such node, break
        if (u == -1) { 
            break; 
        }
        // mark u as used
        used[u] = true;
        // if we reached destination, break
        if (u == j) {
            break;
        }

        // for loop to update distances
        for (int v = 0; v < size; v++) {
            // check for edge and unvisited
            if (m[u][v] != 0 && !used[v]) {
                // make alternative distance
                int alt = dist[u] + m[u][v];
                // update if better
                if (alt < dist[v]) {
                    // found better path to v
                    dist[v] = alt;
                    prev[v] = u;
                }
            }
        }
    }

    // initialize result
    Result* r = new Result;

    // if no path to j
    if (dist[j] == INF) {
        // no path found
        r->length = 0;
        r->weight = 0;
        r->path = nullptr;
    } 
    // else, reconstruct path
    else {
        // reconstruct path from j back to i
        int* temp = new int[size];
        int len = 0;
        int cur = j;

        // backtrack from j to i
        while (cur != -1) {
            temp[len++] = cur;
            if (cur == i) break;
            cur = prev[cur];
        }

        // reverse temp into final path (i -> ... -> j)
        int* path = new int[len];
        for (int k = 0; k < len; k++) {
            path[k] = temp[len - 1 - k];
        }

        // fill result
        r->length = len;
        r->weight = dist[j];
        r->path = path;

        // delete temp
        delete[] temp;
    }

    // delete and deallocate
    delete[] dist;
    delete[] used;
    delete[] prev;

    //return result
    return r;
}

int main()
{

    int mode, size, i, j, w;

    int a, b; // node numbers used for testing 

    cin >> mode >> size >> a >> b;

    Graph x(size);

    // each time input a pair 
    // of indices and a weight 
    // remember to separate them 
    // when inputing from keyboard 
    // also, we assume inputs are 
    // within boundary 
    while (cin >> i >> j >> w) {
        x.Add(i, j, w);
    }

    // Mode 0: test IsEdge()
    if (mode == 0) {
        cout << x.IsEdge(a, b);
    }
    // Mode 1: test IsPath()
    else if (mode == 1) {
        cout << x.IsPath(a, b);
    }
    // Mode 2: test Degree()
    else if (mode == 2) {
        cout << x.Degree(a);
    }
    // Mode 3: test DFT()
    else if (mode == 3) {
        int* s = new int[size];
        s = x.DFT(a);
        for (int i = 0; i < size; i++) {
            cout << s[i];
        }
    }
    // Mode 4: test BFT()
    else if (mode == 4) {
        int* s = new int[size];
        s = x.BFT(a);
        for (int i = 0; i < size; i++) {
            cout << s[i];
        }
    }
    // Mode 5: test Dijkstra()
    else if (mode == 5) {
        Result *z = x.Dijkstra(a, b);
        cout << z->length << endl;
        cout << z->weight << endl;
        for (int i = 0; i < z->length; i++) {
            cout << z->path[i];
        }
    }

    return 0;

}