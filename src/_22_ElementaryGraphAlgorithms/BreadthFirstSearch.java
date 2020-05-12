package _22_ElementaryGraphAlgorithms;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public final class BreadthFirstSearch
{
    public static <T> List<Edge<T>> breadthFirstSearchPath(Graph<T> graph, Vertex<T> root) {
        List<Edge<T>> result = new LinkedList<>();
        HashSet<Vertex<T>> visited = new HashSet<>();

        Queue<Vertex<T>> queue = new LinkedList<>();
        queue.add(root);
        visited.add(root);

        while (!queue.isEmpty()) {
            Vertex<T> vertex = queue.poll();
            for (Vertex<T> v : graph.getAdjacentVerticesOf(vertex)) {
                if (!visited.contains(v)) {
                    visited.add(v);
                    queue.add(v);
                    result.add(new Edge<>(vertex, v));
                }
            }
        }

        return result;
    }

    public static <T> Map<Vertex<T>, Vertex<T>> breadthFirstSearchParentMap(Graph<T> graph, Vertex<T> root) {
        Map<Vertex<T>, Vertex<T>> result = new HashMap<>();
        HashSet<Vertex<T>> visited = new HashSet<>();

        Queue<Vertex<T>> queue = new LinkedList<>();
        queue.add(root);
        visited.add(root);

        while (!queue.isEmpty()) {
            Vertex<T> vertex = queue.poll();
            for (Vertex<T> v : graph.getAdjacentVerticesOf(vertex)) {
                if (!visited.contains(v)) {
                    visited.add(v);
                    queue.add(v);
                    result.putIfAbsent(v, vertex);
                }
            }
        }

        return result;
    }

    private static class Example
    {
        public static void main(String[] args) {
            Graph<Character> graph = new Graph<>(false);
            Character[] V = {'v', 'r', 's', 'w', 't', 'x', 'u', 'y'};
            Character[][] E = {
                    {'r', 'v'}, {'r', 's'}, {'s', 'w'}, {'w', 'x'}, {'u', 'y'},
                    {'w', 't'}, {'t', 'u'}, {'x', 't'}, {'x', 'u'}, {'x', 'y'}
            };

            for (Character c : V)
                graph.addVertex(c);
            for (Character[] c : E)
                graph.addEdge(c[0], c[1]);

            System.out.println(graph);
            System.out.println("Breadth first traversal, root: w");
            System.out.println("Path: " + BreadthFirstSearch.breadthFirstSearchPath(graph, new Vertex<>('w')));
            System.out.println("Parent map: " + BreadthFirstSearch.breadthFirstSearchParentMap(graph, new Vertex<>('w')));
        }
    }
}
