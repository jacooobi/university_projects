#include <iostream>
#include <list>
#include <set>
#include <vector>
#include <map>
using namespace std;

class Graph
{
    int V;    // No. of vertices
    map<int, list<int>> adjacent;
public:
    Graph(int V);   // Constructor
    void print();
    int indegrees(map<int, list<int>> &adj);
    void sort();
    void add(int v, int w);
};

Graph::Graph(int V)
{
    this->V = V;
    for(int i = 0; i < V; i++) {
        adjacent[i];
    }
}

void Graph::add(int v, int w)
{
    if(v >= V || v < 0) {
        cout << "Value out of range: 0 - " << V - 1 << endl;
        return;
    }
    
    adjacent[v].push_back(w);
}

int Graph::indegrees(map<int, list<int>> &adj)
{
    map<int, list<int>>::iterator it;
    vector<int> vertices;
    set<int> indegrees;
    
    for(it = adj.begin(); it != adj.end(); it++) {
        vertices.push_back(it->first);
    }
    
    for(it = adj.begin(); it != adj.end(); it++) {
        for(list<int>::iterator list_it = it->second.begin(); list_it != it->second.end(); list_it++) {
            indegrees.insert(*list_it);
        }
    }
    
    for(vector<int>::iterator vector_it = vertices.begin(); vector_it != vertices.end(); vector_it++) {
        if(indegrees.find(*vector_it) == indegrees.end()) return *vector_it;
    }
    
    return -1;
}

void Graph::sort()
{
    map<int, list<int>> temp = adjacent;
    int i = -1;
    
    while(!temp.empty()) {
        i = indegrees(temp);
        if(i >= 0) {
            temp.erase(i);
            cout << " " << i;
        } else {
            cout << "Couldn't sort this graph" << endl;
            return;
        }
    }
    cout << endl;
}

void Graph::print()
{
    for(map<int, list<int>>::iterator it = adjacent.begin(); it != adjacent.end(); ++it) {
        cout << it->first << ": ";
        for(list<int>::iterator its = this->adjacent[it->first].begin(); its != this->adjacent[it->first].end(); its++) {
            cout << *its << " ";
        }
        
        cout << endl;
    }
}

int main()
{
    Graph g(4);
    g.add(0, 1);
    g.add(2, 1);
    g.add(2, 0);
    g.add(3, 2);
    g.sort();
    
    return 0;
}