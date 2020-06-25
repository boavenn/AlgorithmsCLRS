package _24_SingleSourceShortestPaths;

import _19_FibonacciHeap.FibonacciHeap;
import _22_ElementaryGraphAlgorithms.Graph;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public final class Dijkstra
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
        HashSet<Vertex<T>> visited = new HashSet<>();
        // FibonacciHeap is not necessary but changes time complexity from O((E+V)*logV) to O(E+V*logV)
        FibonacciHeap<DijkstraNode<T>> queue = new FibonacciHeap<>(Comparator.comparingInt(n -> n.d));

        queue.insert(new DijkstraNode<>(src, 0));

        while (!queue.isEmpty()) {
            Vertex<T> v = queue.extractMin().v;
            visited.add(v);
            for (Edge<T> e : graph.getAdjacentEdgesOf(v)) {
                Vertex<T> dest = e.getDest();
                if (!visited.contains(dest)) {
                    Util.relax(map, e);
                    queue.insert(new DijkstraNode<>(dest, map.get(dest)));
                }
            }
        }

        return map;
    }

    public static <T> Map<Vertex<T>, Vertex<T>> dijkstraPath(Graph<T> graph, Vertex<T> src) {
        Map<Vertex<T>, Integer> map = Util.initializeSource(graph.getVertices(), src);
        HashSet<Vertex<T>> visited = new HashSet<>();
        // FibonacciHeap is not necessary but changes time complexity from O((E+V)*logV) to O(E+V*logV)
        FibonacciHeap<DijkstraNode<T>> queue = new FibonacciHeap<>(Comparator.comparingInt(n -> n.d));
        Map<Vertex<T>, Vertex<T>> result = new HashMap<>();

        queue.insert(new DijkstraNode<>(src, 0));

        while (!queue.isEmpty()) {
            Vertex<T> v = queue.extractMin().v;
            visited.add(v);
            for (Edge<T> e : graph.getAdjacentEdgesOf(v)) {
                Vertex<T> dest = e.getDest();
                if (!visited.contains(dest)) {
                    if (Util.relax(map, e))
                        result.put(dest, v);
                    queue.insert(new DijkstraNode<>(dest, map.get(dest)));
                }
            }
        }

        return result;
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
