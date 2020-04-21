package _22_ElementaryGraphAlgorithms;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public abstract class BreadthFirstSearch
{
    public static <T> List<Edge<T>> breadthFirstSearchPath(Graph<T> graph, Vertex<T> root) {
        List<Edge<T>> result = new LinkedList<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        // to check whether a vertex was already visited or not
        Map<Vertex<T>, Boolean> visited = new HashMap<>();

        queue.add(root);
        visited.put(root, true);

        while (!queue.isEmpty()) {
            Vertex<T> vertex = queue.poll();
            for (Vertex<T> v : graph.getAdjacentVerticesOf(vertex)) {
                if (!visited.containsKey(v)) {
                    visited.put(v, true);
                    queue.add(v);
                    result.add(new Edge<>(vertex, v));
                }
            }
        }

        return result;
    }

    public static <T> Map<Vertex<T>, Vertex<T>> breadthFirstSearchParentMap(Graph<T> graph, Vertex<T> root) {
        Queue<Vertex<T>> queue = new LinkedList<>();
        // to check whether a vertex was already visited or not
        Map<Vertex<T>, Boolean> visited = new HashMap<>();
        Map<Vertex<T>, Vertex<T>> util = new HashMap<>();

        queue.add(root);
        visited.put(root, true);

        while (!queue.isEmpty()) {
            Vertex<T> vertex = queue.poll();
            for (Vertex<T> v : graph.getAdjacentVerticesOf(vertex)) {
                if (!visited.containsKey(v)) {
                    visited.put(v, true);
                    queue.add(v);
                    util.putIfAbsent(v, vertex);
                }
            }
        }

        return util;
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
            System.out.println(BreadthFirstSearch.breadthFirstSearchPath(graph, new Vertex<>('w')));
            System.out.println(BreadthFirstSearch.breadthFirstSearchParentMap(graph, new Vertex<>('w')));
        }
    }
}
