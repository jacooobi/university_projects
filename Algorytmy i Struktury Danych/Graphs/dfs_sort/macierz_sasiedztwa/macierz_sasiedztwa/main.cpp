#include <iostream>
#include <stack>

using namespace std;

class Graph
{
    int V;
    bool **matrix;
    void sortUtil(int v, bool visited[], stack<int> &Stack);
public:
    Graph(int V);
    ~Graph();
    void sort();
    void addEdge(int v, int w);
};

Graph::Graph(int V)
{
    this->V = V;
    matrix = new bool* [V];
    
    for(int i=0; i<V; i++) {
        matrix[i] = new bool[V];
        for(int j=0; j<V; j++)
            matrix[i][j] = false;
    }
}

Graph::~Graph() {
    for(int i=0; i<V; i++)
        delete [] matrix[i];
    
    delete [] matrix;
}

void Graph::addEdge(int v, int w) {
    matrix[v][w] = true;
}

void Graph::sort() {
    stack<int> Stack;
    bool * visited = new bool[V];
    
    for(int i=0; i<V; i++)
        visited[i] = false;
    
    for(int i=0; i<V; i++)
        if (visited[i] == false)
            sortUtil(i, visited, Stack);
    
    while(Stack.empty() == false) {
        cout << Stack.top() << " ";
        Stack.pop();
    }
}

void Graph::sortUtil(int v, bool visited[], stack<int> &Stack) {
    
    visited[v] = true;
    
    for(int i=0; i<V; i++)
        if (matrix[v][i] == true && visited[i] == false)
            sortUtil(i, visited, Stack);
    
    Stack.push(v);
}

int main()
{
    Graph g(8);
    
    g.addEdge(0, 3);
    g.addEdge(3, 7);
    g.addEdge(1, 2);
    g.addEdge(2, 4);
    
    g.addEdge(2, 5);
    g.addEdge(2, 6);
    g.addEdge(5, 6);
    g.addEdge(6, 7);
    
    g.sort();
    
    return 0;
}