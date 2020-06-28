package _25_AllPairsShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;
import _24_SingleSourceShortestPaths.BellmanFord;
import _24_SingleSourceShortestPaths.Dijkstra;

import java.util.Map;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;
import static _25_AllPairsShortestPaths.Util.VertexMatrix;

public final class Johnson<T>
{
    public static <T> VertexMatrix<T, Integer> johnson(Graph<T> graph) {
        Graph<T> g = copy(graph);
        Vertex<T> s = new Vertex<>(null);

        g.addVertex(s);
        for (Vertex<T> v : graph.vertices())
            g.addEdge(s.getKey(), v.getKey(), 0);

        Map<Vertex<T>, Integer> bellman = BellmanFord.bellmanFordDistance(g, s);

        for (Edge<T> e : g.edges())
            e.setWeight(e.getWeight() + bellman.get(e.getSrc()) - bellman.get(e.getDest()));

        VertexMatrix<T, Integer> D = new VertexMatrix<>(graph.vertices(), 0);
        for (Vertex<T> u : graph.vertices()) {
            Map<Vertex<T>, Integer> dijkstra = Dijkstra.dijkstraDistance(g, u);
            for (Vertex<T> v : graph.vertices())
                D.set(u, v, dijkstra.get(v) + bellman.get(v) - bellman.get(u));
        }

        return D;
    }

    private static <T> Graph<T> copy(Graph<T> graph) {
        Graph<T> temp = new Graph<>(graph.isDirected());
        for (Vertex<T> v : graph.vertices())
            temp.addVertex(v.getKey());
        for (Edge<T> e : graph.edges())
            temp.addEdge(e.getSrc().getKey(), e.getDest().getKey(), e.getWeight());
        return temp;
    }

    private static class Example
    {
        public static void main(String[] args) {
            Graph<Integer> graph = new Graph<>(true);
            Integer[] V = {1, 2, 3, 4, 5};
            Integer[][] E = {
                    {1, 2}, {1, 3}, {1, 5}, {2, 4}, {2, 5},
                    {3, 2}, {4, 1}, {4, 3}, {5, 4}
            };
            Integer[] W = {3, 8, -4, 1, 7, 4, 2, -5, 6};

            for (Integer i : V)
                graph.addVertex(i);
            for (int i = 0; i < E.length; i++)
                graph.addEdge(E[i][0], E[i][1], W[i]);

            System.out.println(Johnson.johnson(graph));
        }
    }
}
