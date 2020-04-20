package _24_SingleSourceShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public abstract class Dijkstra
{
    private static class DijkstraNode<T>
    {
        Vertex<T> v;
        Integer d;

        public DijkstraNode(Vertex<T> v, Integer d) {
            this.v = v;
            this.d = d;
        }
    }

    public static <T> Map<Vertex<T>, Integer> dijkstraDistance(Graph<T> graph, Vertex<T> src) {
        Map<Vertex<T>, Integer> map = Util.initializeSource(graph.getVertices(), src);
        Map<Vertex<T>, Boolean> visited = new HashMap<>();
        PriorityQueue<DijkstraNode<T>> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.d));

        queue.add(new DijkstraNode<>(src, 0));

        while (!queue.isEmpty()) {
            Vertex<T> v = queue.poll().v;
            visited.put(v, true);
            for (Edge<T> e : graph.getAdjacentEdgesOf(v)) {
                Vertex<T> dest = e.getDest();
                if (!visited.containsKey(dest)) {
                    Util.relax(map, e);
                    queue.add(new DijkstraNode<>(dest, map.get(dest)));
                }
            }
        }

        return map;
    }

    public static <T> Map<Vertex<T>, Vertex<T>> dijkstraPath(Graph<T> graph, Vertex<T> src) {
        Map<Vertex<T>, Integer> map = Util.initializeSource(graph.getVertices(), src);
        Map<Vertex<T>, Boolean> visited = new HashMap<>();
        PriorityQueue<DijkstraNode<T>> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.d));
        // if we want to have information about "how to get to" a vertex from the src to get the shortest path
        Map<Vertex<T>, Vertex<T>> util = new HashMap<>();

        queue.add(new DijkstraNode<>(src, 0));

        while (!queue.isEmpty()) {
            Vertex<T> v = queue.poll().v;
            visited.put(v, true);
            for (Edge<T> e : graph.getAdjacentEdgesOf(v)) {
                Vertex<T> dest = e.getDest();
                if (!visited.containsKey(dest)) {
                    if (Util.relax(map, e))
                        util.put(dest, v);
                    queue.add(new DijkstraNode<>(dest, map.get(dest)));
                }
            }
        }

        return util;
    }

    private static class Example
    {
        public static void main(String[] args) {
            Graph<Character> graph = new Graph<>(true);

            Character[] V = {'s', 't', 'x', 'y', 'z'};
            Character[][] E = {
                    {'s', 't'}, {'s', 'y'}, {'t', 'x'}, {'t', 'y'}, {'y', 't'},
                    {'y', 'x'}, {'y', 'z'}, {'z', 'x'}, {'z', 's'}, {'x', 'z'}
            };
            Integer[] W = {10, 5, 1, 2, 3, 9, 2, 6, 7, 4};

            for (Character c : V)
                graph.addVertex(c);
            for (int i = 0; i < E.length; i++)
                graph.addEdge(E[i][0], E[i][1], W[i]);

            System.out.println(graph);
            System.out.println("Shortest paths from root 's': ");
            System.out.println(Dijkstra.dijkstraDistance(graph, new Vertex<>('s')));

            System.out.println("\nShortest paths from root 's': ");
            Map<Vertex<Character>, Vertex<Character>> paths = Dijkstra.dijkstraPath(graph, new Vertex<>('s'));
            System.out.println(paths);

            System.out.println("\nShortest path from 's' to 'x': ");
            System.out.println(Util.getShortestPath(paths, new Vertex<>('s'), new Vertex<>('x')));
        }
    }
}
