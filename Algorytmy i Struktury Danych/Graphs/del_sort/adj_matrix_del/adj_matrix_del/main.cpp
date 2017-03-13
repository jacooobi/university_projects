#include <iostream>
#include <list>
#include <set>
#include <map>
using namespace std;

class Graph
{
    int V;    // No. of vertices
    bool **matrix;
    
private:
    void clear_from(int i);
    set<int> get_all_vertices();
    
public:
    Graph(int V);   // Constructor
    ~Graph();
    void print();
    int indegrees(set<int> &vertices);
    void sort();
    void add(int v, int w);
};

Graph::Graph(int V)
{
    this->V = V;
    matrix = new bool*[V];
    for(int i = 0; i < V; i++) {
        matrix[i] = new bool[V];
        for(int j = 0; j < V; j++) {
            matrix[i][j] = false;
        }
    }
}

Graph::~Graph()
{
    for(int i = 0; i < V; i++) {
        delete [] matrix[i];
    }
    delete [] matrix;
}

set<int> Graph::get_all_vertices()
{
    set<int> tmp;
    for(int i = 0; i < V; i++) {
        tmp.insert(i);
    }
    
    return tmp;
}

void Graph::clear_from(int i)
{
    for(int j = 0; j < V; j++) {
        matrix[j][i] = false;
        matrix[i][j] = false;
    }
}

void Graph::add(int v, int w)
{
    if(v >= V || v < 0) {
        cout << "Value out of range: 0 - " << V - 1 << endl;
        return;
    }
    
    matrix[v][w] = true;
}

int Graph::indegrees(set<int> &vertices)
{
    set<int>::iterator it, inner_it;
    set<int> indegrees;
    
    for(it = vertices.begin(); it != vertices.end(); it++) {
        for(inner_it = vertices.begin(); inner_it != vertices.end(); inner_it++) {
            if(matrix[*it][*inner_it]) indegrees.insert(*inner_it);
        }
    }
    
    for(it = vertices.begin(); it != vertices.end(); it++) {
        if(indegrees.find(*it) == indegrees.end()) return *it;
    }
    
    return -1;
}

void Graph::sort()
{
    set<int> vertices = get_all_vertices();
    
    while(!vertices.empty()) {
        set<int>::iterator it;
        int i = -1;
        i = indegrees(vertices);
        if(i < 0) {
            cout << "Couldnt sort, lol" << endl;
            return;
        }
        vertices.erase(i);
        clear_from(i);
        cout << i << " ";
    }
    cout << endl;
}

void Graph::print(){}

int main()
{
    Graph g(7);
    g.add(0, 1);
    g.add(0, 2);
    g.add(1, 3);
    g.add(3, 6);
    g.add(4, 1);
    g.add(5, 4);
    g.add(5, 0);
    g.sort();
    
    return 0;
}