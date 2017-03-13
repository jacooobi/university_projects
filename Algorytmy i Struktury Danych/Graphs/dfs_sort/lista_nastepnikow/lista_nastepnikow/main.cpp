#include <iostream>
#include <list>
#include <stack>

using namespace std;

class Graph
{
    int V;
    list<int> *adj;
    void sortUtil(int v, bool visited[], stack<int> &Stack);
public:
    Graph(int V);
    void addEdge(int v, int w);
    void sort();
};

Graph::Graph(int V)
{
    this->V = V;
    adj = new list<int>[V];
}

void Graph::addEdge(int v, int w)
{
    adj[v].push_back(w);
}

void Graph::sortUtil(int v, bool visited[], stack<int> &Stack)
{
    visited[v] = true;
    
    list<int>::iterator i;
    for(i = adj[v].begin(); i != adj[v].end(); ++i)
        if(!visited[*i])
            sortUtil(*i, visited, Stack);

    Stack.push(v);
}

void Graph::sort()
{
    stack<int> Stack;
    bool *visited = new bool[V];
    
    for(int i = 0; i < V; i++)
        visited[i] = false;
    
    for(int i = 0; i < V; i++)
        if(visited[i] == false)
            sortUtil(i, visited, Stack);
    
    while(Stack.empty() == false) {
        cout << Stack.top() << " ";
        Stack.pop();
    }
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