#include <iostream>
#include <stack>
#include <list>

using namespace std;

class Graph
{
    int V;
    list<int> *adj, *pre, *sep;
    bool **matrix;
    int **graphMatrix;
public:
    Graph(int V);
    ~Graph();
    void addEdge(int v, int w);
    void makeLists();
    void makeGraphMatrix();
    void sort();
    void sortUtil(int v, bool visited[], stack<int> &Stack);
    void print();
};

Graph::Graph(int V)
{
    this->V = V;
    
    adj = new list<int>[V + 1];
    pre = new list<int>[V + 1];
    sep = new list<int>[V + 1];
    
    matrix = new bool * [V+1];
    for (int i=0; i<=V; i++) {
        matrix[i] = new bool[V+1];
        for (int j=0; j<=V; j++)
            matrix[i][j] = false;
    }
    
    graphMatrix = new int * [V+2];
    for (int i=0; i<V+2 ; i++) {
        graphMatrix[i] = new int[V+2];
        for (int j=0; j<V+2; j++) {
            graphMatrix[i][j] = 0;
        }
    }
};

Graph::~Graph()
{
    for(int i = 0; i < V + 1; i++) delete[] matrix[i];
    for(int i = 0; i < V + 2; i++) delete[] graphMatrix[i];
    
    delete[] graphMatrix;
    delete[] matrix;
}

void Graph::addEdge(int v, int w)
{
    matrix[v][w] = true;
}

void Graph::makeLists()
{
    for(int i = 1; i <= V; i++) {
        for(int j = 1; j <= V; j++) {
            if(matrix[i][j]) {
                adj[i].push_back(j);
                pre[j].push_back(i);
            }
            else if(!matrix[i][j] && !matrix[j][i]) {
                sep[i].push_back(j);
            }
        }
    }
}

void Graph::makeGraphMatrix()
{
    list<int>::iterator it;
    
    for(int i=1; i<=V; i++) {
        if(adj[i].empty()) {
            graphMatrix[i][V+1] = 0;
            graphMatrix[V+1][i] = 0;
        } else {
            it = adj[i].begin();
            graphMatrix[i][V+1] = *it;
            int prev = *it;
            
            while(it != adj[i].end()) {
                graphMatrix[i][prev] = *it + V;
                prev = *it;
                it++;
            }
            
            graphMatrix[i][adj[i].back()] = adj[i].back() + V;
            graphMatrix[V+1][i] = adj[i].back();
        }
        
        if(pre[i].empty()) {
            graphMatrix[i][0] = 0;
            graphMatrix[0][i] = 0;
        } else {
            it = pre[i].begin();
            graphMatrix[i][0] = *it;
            int prev = *it;
            it++;
            
            while(it != pre[i].end()) {
                graphMatrix[i][prev] = *it;
                prev = *it;
                it++;
            }
            
            graphMatrix[i][pre[i].back()] = pre[i].back();
            graphMatrix[0][i] = pre[i].back();
        }
        
        if(!sep[i].empty()) {
            it = sep[i].begin();
            int prev = *it;
            it++;
            
            while(it != sep[i].end()) {
                graphMatrix[i][prev] = -(*it);
                prev = *it;
                it++;
            }
            graphMatrix[i][sep[i].back()] = -(sep[i].back());
        }
    }
}

void Graph::print()
{
    for (int i = 0; i< V + 2; i++) {
        for (int j = 0; j< V + 2; j++) cout << graphMatrix[i][j] << "\t";
        
        cout << endl;
    }
}

void Graph::sort()
{
    this->makeLists();
    this->makeGraphMatrix();
    
    stack<int> Stack;
    bool * visited = new bool[V+1];
    
    for(int i=1; i<=V; i++)
        visited[i] = false;
    
    for(int i=1; i<=V; i++)
        if(visited[i] == false)
            sortUtil(i, visited, Stack);
    
    while(Stack.empty() == false) {
        cout << Stack.top() << " ";
        Stack.pop();
    }
}

void Graph::sortUtil(int v, bool *visited, stack<int> &Stack)
{
    visited[v] = true;
    
    for(int i=1; i<=V; i++)
        if ((graphMatrix[i][v] >= 1 && graphMatrix[i][v] <= V) && (visited[i] == false))
            sortUtil(i, visited, Stack);
    
    Stack.push(v);
}

int main()
{
    Graph g(8);
    
    g.addEdge(1, 4);
    g.addEdge(4, 8);
    g.addEdge(2, 3);
    g.addEdge(3, 5);
    
    g.addEdge(3, 6);
    g.addEdge(3, 7);
    g.addEdge(6, 7);
    g.addEdge(7, 8);
    
    g.sort();
    return 0;
}
