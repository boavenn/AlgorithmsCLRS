package _22_ElementaryGraphAlgorithms;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public abstract class DepthFirstSearch
{
    public static <T> List<Edge<T>> depthFirstSearchPath(Graph<T> graph, Vertex<T> root) {
        List<Edge<T>> result = new LinkedList<>();
        Stack<Vertex<T>> stack = new Stack<>();
        // to check whether a vertex was already visited or not
        Map<Vertex<T>, Boolean> visited = new HashMap<>();
        // utility map so we can put edges to result in order in which they were discovered
        Map<Vertex<T>, Vertex<T>> util = new HashMap<>();

        stack.push(root);

        while (!stack.isEmpty()) {
            Vertex<T> vertex = stack.pop();
            if (!visited.containsKey(vertex)) {
                visited.put(vertex, true);
                if (util.containsKey(vertex))
                    result.add(new Edge<>(util.get(vertex), vertex));
                for (Vertex<T> v : graph.getAdjacentVerticesOf(vertex)) {
                    stack.push(v);
                    util.putIfAbsent(v, vertex);
                }
            }
        }

        return result;
    }

    public static <T> Map<Vertex<T>, Vertex<T>> depthFirstSearchParentMap(Graph<T> graph, Vertex<T> root) {
        Stack<Vertex<T>> stack = new Stack<>();
        // to check whether a vertex was already visited or not
        Map<Vertex<T>, Boolean> visited = new HashMap<>();
        Map<Vertex<T>, Vertex<T>> util = new HashMap<>();

        stack.push(root);

        while (!stack.isEmpty()) {
            Vertex<T> vertex = stack.pop();
            if (!visited.containsKey(vertex)) {
                visited.put(vertex, true);
                for (Vertex<T> v : graph.getAdjacentVerticesOf(vertex)) {
                    stack.push(v);
                    util.putIfAbsent(v, vertex);
                }
            }
        }

        return util;
    }

    private static class Example
    {
        public static void main(String[] args) {
            Graph<Character> graph = new Graph<>(true);
            Character[] V = {'u', 'x', 'v', 'w', 'y', 'z'};
            Character[][] E = {
                    {'u', 'x'}, {'x', 'v'}, {'u', 'v'}, {'v', 'y'},
                    {'y', 'x'}, {'w', 'y'}, {'w', 'z'}, {'z', 'z'}
            };

            for (Character c : V)
                graph.addVertex(c);
            for (Character[] c : E)
                graph.addEdge(c[0], c[1]);

            System.out.println(graph);
            System.out.println("Depth first traversal, root: w");
            System.out.println(DepthFirstSearch.depthFirstSearchPath(graph, new Vertex<>('w')));
            System.out.println(DepthFirstSearch.depthFirstSearchParentMap(graph, new Vertex<>('w')));

            Graph<Integer> graph2 = new Graph<>(true);
            Integer[] V2 = {0, 1, 2, 3, 4, 5, 6, 7};
            Integer[][] E2 = {
                    {0, 1}, {0, 2}, {0, 4}, {1, 3}, {1, 5}, {2, 5}, {2, 6},
                    {2, 4}, {5, 7}, {6, 7}, {7, 2}
            };

            for (Integer i : V2)
                graph2.addVertex(i);
            for (Integer[] i : E2)
                graph2.addEdge(i[0], i[1]);

            System.out.println("\n" + graph2);
            System.out.println("Depth first traversal, root: 0");
            System.out.println(DepthFirstSearch.depthFirstSearchPath(graph2, new Vertex<>(0)));
            System.out.println(DepthFirstSearch.depthFirstSearchParentMap(graph2, new Vertex<>(0)));
        }
    }
}
