package projektPO.utility;

import java.util.Stack;

public class DepthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    /**
     * Konstruktor
     * @param G graf nieskierowany
     * @param s wierzcholek poczatkowy
     */
    public DepthFirstPaths(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
    }

    /**
     * Sprawdza czy istnieje polaczenie miedzy dwoma wierzcholkami
     * @param v wierzcholek
     * @return true jezeli istnieje polaczenie, w przeciwnym wypadku false
     */
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    /**
     * Oblicza droge miedzy dwoma wierzcholkami
     * @param v wierzcholek
     * @return lista wierzcholkow oznaczajaca droge
     */
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }
}