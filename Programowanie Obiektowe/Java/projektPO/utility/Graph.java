package projektPO.utility;

public class Graph {
    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    /**
     * Konstruktor grafu
     * @param V liczba wierzcholkow w grafie
     */
    public Graph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Integer>();
    }

    /**
     * Zwraca wierzcholki
     * @return lista wierzcholkow
     */
    public int V()  {  return V;  }

    /**
     * Zwraca krawedzie
     * @return lista krawedzi
     */
    public int E()  {  return E;  }

    /**
     * Tworzy krawedz miedzy dwoma wierzcholkami
     * @param v wierzcholek A
     * @param w wierzcholek B
     */
    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    /**
     * @param v wierzcholek startowy
     * @return lista wierzcholkow polaczych z wierzcholkiem v
     */
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }
}
