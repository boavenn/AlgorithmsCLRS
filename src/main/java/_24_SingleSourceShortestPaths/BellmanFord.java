package _24_SingleSourceShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;

import java.util.HashMap;
import java.util.Map;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public final class BellmanFord
{
    public static <T> Map<Vertex<T>, Integer> bellmanFordDistance(Graph<T> graph, Vertex<T> src) {
        Map<Vertex<T>, Integer> map = Util.initializeSource(graph.vertices(), src);

        int V = map.size();
        for (int i = 1; i < V; i++) {
            for (Edge<T> e : graph.edges()) {
                Util.relax(map, e);
            }
        }
        validateGraph(graph, map);

        return map;
    }

    public static <T> Map<Vertex<T>, Vertex<T>> bellmanFordPath(Graph<T> graph, Vertex<T> src) {
        Map<Vertex<T>, Integer> map = Util.initializeSource(graph.vertices(), src);
        Map<Vertex<T>, Vertex<T>> util = new HashMap<>();

        int V = map.size();
        for (int i = 1; i < V; i++) {
            for (Edge<T> e : graph.edges()) {
                if (Util.relax(map, e))
                    util.put(e.getDest(), e.getSrc());
            }
        }
        validateGraph(graph, map);

        return util;
    }

    private static <T> void validateGraph(Graph<T> graph, Map<Vertex<T>, Integer> map) {
        for (Edge<T> e : graph.edges()) {
            Vertex<T> src = e.getSrc();
            Vertex<T> dest = e.getDest();
            if (map.get(src) != Integer.MAX_VALUE && map.get(dest) > map.get(src) + e.getWeight())
                throw new IllegalArgumentException("No shortest paths found in the given graph");
        }
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

            System.out.println("\nShortest distances from root 's': ");
            System.out.println(BellmanFord.bellmanFordDistance(graph, new Vertex<>('s')));

            System.out.println("\nShortest paths from root 's': ");
            Map<Vertex<Character>, Vertex<Character>> paths = BellmanFord.bellmanFordPath(graph, new Vertex<>('s'));
            System.out.println(paths);

            System.out.println("\nShortest path from 's' to 't': ");
            System.out.println(Util.getShortestPath(paths, new Vertex<>('s'), new Vertex<>('t')));
        }
    }
}
