package _24_SingleSourceShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;

import java.util.HashMap;
import java.util.Map;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public abstract class BellmanFord<T>
{
    public static <T> Map<Vertex<T>, Integer> bellmanFord(Graph<T> graph, Vertex<T> src) {
        Map<Vertex<T>, Integer> map = Util.initializeSource(graph.getVertices(), src);
        // if we want to have information about "how to get to" a vertex from the src to get the shortest path
        Map<Vertex<T>, Vertex<T>> util = new HashMap<>();

        int V = map.size();
        for (int i = 1; i < V; i++) {
            for (Edge<T> e : graph.getEdges()) {
                if (Util.relax(map, e))
                    util.put(e.getDest(), e.getSrc());
            }
        }

        for (Edge<T> e : graph.getEdges()) {
            Vertex<T> u = e.getSrc();
            Vertex<T> v = e.getDest();
            if (map.get(u) != Integer.MAX_VALUE && map.get(v) > map.get(u) + e.getWeight())
                throw new IllegalArgumentException("No shortest paths found in the given graph");
        }

        //System.out.println(util);

        return map;
    }

    private static class Example
    {
        public static void main(String[] args) {
            Graph<Character> graph = new Graph<>(true);

            Character[] V = {'s', 't', 'x', 'y', 'z'};
            Character[][] E = {
                    {'s', 't'}, {'s', 'y'}, {'t', 'x'}, {'x', 't'}, {'t', 'y'},
                    {'t', 'z'}, {'y', 'x'}, {'y', 'z'}, {'z', 'x'}, {'z', 's'}
            };
            Integer[] W = {6, 7, 5, -2, 8, -4, -3, 9, 7, 2};

            for (Character c : V)
                graph.addVertex(c);
            for (int i = 0; i < E.length; i++)
                graph.addEdge(E[i][0], E[i][1], W[i]);

            System.out.println(graph);
            System.out.println("Shortest paths from root 's': ");
            System.out.println(BellmanFord.bellmanFord(graph, new Vertex<>('s')));
        }
    }
}
