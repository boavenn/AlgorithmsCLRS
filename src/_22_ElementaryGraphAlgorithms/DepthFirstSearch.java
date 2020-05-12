package _22_ElementaryGraphAlgorithms;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public final class DepthFirstSearch
{
    public static <T> List<Edge<T>> depthFirstSearchPath(Graph<T> graph, Vertex<T> root) {
        List<Edge<T>> result = new LinkedList<>();
        HashSet<Vertex<T>> visited = new HashSet<>();
        // utility <child, parent> map so we can put edges to result list in order in which they were discovered
        Map<Vertex<T>, Vertex<T>> util = new HashMap<>();

        Stack<Vertex<T>> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Vertex<T> vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
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
        Map<Vertex<T>, Vertex<T>> result = new HashMap<>();
        Stack<Vertex<T>> stack = new Stack<>();
        HashSet<Vertex<T>> visited = new HashSet<>();

        stack.push(root);

        while (!stack.isEmpty()) {
            Vertex<T> vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                for (Vertex<T> v : graph.getAdjacentVerticesOf(vertex)) {
                    stack.push(v);
                    result.putIfAbsent(v, vertex);
                }
            }
        }

        return result;
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
            System.out.println("Path: " + DepthFirstSearch.depthFirstSearchPath(graph, new Vertex<>('w')));
            System.out.println("Parent map: " + DepthFirstSearch.depthFirstSearchParentMap(graph, new Vertex<>('w')));

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
            System.out.println("Path: " + DepthFirstSearch.depthFirstSearchPath(graph2, new Vertex<>(0)));
            System.out.println("Parent map: " + DepthFirstSearch.depthFirstSearchParentMap(graph2, new Vertex<>(0)));
        }
    }
}
