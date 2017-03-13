#include <iostream>
#include <list>
#include <map>
using namespace std;

class Graph
{
    int V;
    list<int> *adj;
    list<int> *pre;
    list<int> *no;
    int **graph_matrix;
    bool **matrix;
public:
    Graph(int V);
    ~Graph();
    void print();
    void make_lists();
    int get_zero_indegree(map<int, int> vertices);
    map<int, int> get_indegree_map();
    void make_graph_matrix();
    void sort();
    void add(int v, int w);
};

Graph::Graph(int V)
{
    this->V = V;
    adj = new list<int>[V + 1];
    pre = new list<int>[V + 1];
    no = new list<int>[V + 1];
    
    int size = V + 2;
    graph_matrix = new int* [size];
    for(int i = 0; i < size; i++) {
        graph_matrix[i] = new int[size];
        for(int j = 0; j < size; j++) {
            graph_matrix[i][j] = 0;
        }
    }
    
    matrix = new bool*[V + 1];
    for(int i = 0; i < V + 1; i++) {
        matrix[i] = new bool[V + 1];
        for(int j = 0; j < V + 1; j++) {
            matrix[i][j] = false;
        }
    }
}

Graph::~Graph()
{
    for(int i = 0; i < V + 1; i++) delete[] matrix[i];
    for(int i = 0; i < V + 2; i++) delete[] graph_matrix[i];
    
    delete[] graph_matrix;
    delete[] matrix;
}

void Graph::add(int v, int w)
{
    if(v > 0 && v <= V && w > 0 && w <= V) {
        matrix[v][w] = true;
    }
}

void Graph::make_lists()
{
    for(int i = 1; i <= V; i++) {
        for(int j = 1; j <= V; j++) {
            if(matrix[i][j]) {
                adj[i].push_back(j);
                pre[j].push_back(i);
            }
            else if(!matrix[i][j] && !matrix[j][i]) {
                no[i].push_back(j);
            }
        }
    }
}

map<int, int> Graph::get_indegree_map()
{
    map<int, int> tmp;
    
    for(int i = 1; i <= V; i++) {
        tmp[i] = 0;
    }
    
    return tmp;
}

int Graph::get_zero_indegree(map<int, int> vertices)
{
    map<int, int>::iterator it, inner_it;
    
    for(it = vertices.begin(); it != vertices.end(); it++) {
        for(inner_it = vertices.begin(); inner_it != vertices.end(); inner_it++) {
            int val = graph_matrix[it->first][inner_it->first];
            if(val >= 1 && val <= V) vertices[it->first]++;
        }
    }
    
    for(it = vertices.begin(); it != vertices.end(); it++) {
        if(it->second == 0) return it->first;
    }
    
    return -1;
}

void Graph::make_graph_matrix()
{
    list<int>::iterator it;
    
    for(int i = 1; i <= V; i++) {
        // Adj List
        if(adj[i].empty()) {
            graph_matrix[i][V + 1] = 0;
            graph_matrix[V + 1][i] = 0;
        } else {
            it = adj[i].begin();
            graph_matrix[i][V + 1] = *it;
            int prev = *it;
            it++;
            
            while(it != adj[i].end()) {
                graph_matrix[i][prev] = *it + V;
                prev = *it;
                it++;
            }
            
            graph_matrix[i][adj[i].back()] = adj[i].back() + V;
            graph_matrix[V + 1][i] = adj[i].back();
        }
        
        // Prev list
        if(pre[i].empty()) {
            graph_matrix[i][0] = 0;
            graph_matrix[0][i] = 0;
        } else {
            it = pre[i].begin();
            graph_matrix[i][0] = *it;
            int prev = *it;
            it++;
            
            while(it != pre[i].end()) {
                graph_matrix[i][prev] = *it;
                prev = *it;
                it++;
            }
            
            graph_matrix[i][pre[i].back()] = pre[i].back();
            graph_matrix[0][i] = pre[i].back();
        }
        
        // No list
        if(!no[i].empty()) {
            it = no[i].begin();
            int prev = *it;
            it++;
            while(it != no[i].end()) {
                graph_matrix[i][prev] = -(*it);
                prev = *it;
                it++;
            }
            graph_matrix[i][no[i].back()] = -(no[i].back());
        }
    }
}

void Graph::sort()
{
    this->make_lists();
    this->make_graph_matrix();
    
    map<int, int> vertices = get_indegree_map();
    while(!vertices.empty()) {
        int i = -1;
        i = get_zero_indegree(vertices);
        if(i < 0) {
            cout << "Couldn't sort" << endl;
            return;
        }
        vertices.erase(i);
        cout << i << " ";
    }
    
    cout << endl;
}

void Graph::print()
{
    for (int i = 0; i< V + 2; i++) {
        for (int j = 0; j< V + 2; j++) cout << graph_matrix[i][j] << "\t";
    
        cout << endl;
    }
}

int main()
{
    Graph g(6);
    
    g.add(1, 2);
    g.add(1, 3);
    g.add(4, 2);
    g.add(4, 5);
    g.add(5, 6);
    g.add(5, 1);
    g.add(6, 2);

    g.sort();
    return 0;
}