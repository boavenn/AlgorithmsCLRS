package _22_ElementaryGraphAlgorithms;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;

public final class StronglyConnectedComponents<T>
{
    public static <T> List<Graph<T>> stronglyConnectedComponents(Graph<T> graph) {
        if (!graph.isDirected())
            throw new IllegalArgumentException("Graph is not directed");

        Graph<T> transposedGraph = Graph.transpose(graph);
        Map<Vertex<T>, Graph<T>> components = new HashMap<>();
        Stack<Vertex<T>> stack = new Stack<>();
        HashSet<Vertex<T>> visited = new HashSet<>();

        for (Vertex<T> v : graph.getVertices())
            visit(graph, v, visited, stack);

        // will be reused for checking if vertex is already in a component
        visited.clear();

        while (!stack.isEmpty()) {
            Vertex<T> v = stack.pop();
            assign(transposedGraph, v, v, visited, components);
        }

        // can be omitted if you dont care about links in the subgraphs
        for (Vertex<T> src : graph.getVertices()) {
            Graph<T> subgraph = components.get(src);
            for (Vertex<T> dest : graph.getAdjacentVerticesOf(src)) {
                if (subgraph.containsVertex(dest)) {
                    subgraph.addEdge(src.getKey(), dest.getKey());
                }
            }
        }

        return new LinkedList<>(new HashSet<>(components.values()));
    }

    private static <T> void visit(Graph<T> graph, Vertex<T> v, HashSet<Vertex<T>> visited, Stack<Vertex<T>> stack) {
        if (visited.contains(v))
            return;

        visited.add(v);

        for (Vertex<T> u : graph.getAdjacentVerticesOf(v))
            visit(graph, u, visited, stack);

        stack.push(v);
    }

    private static <T> void assign(Graph<T> graph, Vertex<T> v, Vertex<T> root,
                                   HashSet<Vertex<T>> visited, Map<Vertex<T>, Graph<T>> components) {
        if (visited.contains(v))
            return;

        if (!components.containsKey(root))
            components.put(root, new Graph<>(true));
            // adding additional entry to map so we can get every vertex component in O(1) time
            // later, can be omitted if you dont care about links in a subgraph
        else
            components.put(v, components.get(root));

        components.get(root).addVertex(v);
        visited.add(v);

        for (Vertex<T> u : graph.getAdjacentVerticesOf(v))
            assign(graph, u, root, visited, components);
    }

    private static class Example
    {
        public static void main(String[] args) {
            Graph<Character> graph2 = new Graph<>(true);

            Character[] V2 = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
            Character[][] E2 = {
                    {'a', 'b'}, {'b', 'c'}, {'c', 'd'}, {'d', 'c'},
                    {'d', 'h'}, {'h', 'h'}, {'c', 'g'}, {'g', 'h'},
                    {'g', 'f'}, {'f', 'g'}, {'b', 'f'}, {'b', 'e'},
                    {'e', 'f'}, {'e', 'a'}
            };

            for (Character c : V2)
                graph2.addVertex(c);
            for (Character[] c : E2)
                graph2.addEdge(c[0], c[1]);

            System.out.println(graph2);

            List<Graph<Character>> l = StronglyConnectedComponents.stronglyConnectedComponents(graph2);
            System.out.println("Strongly connected components: ");
            for (Graph<Character> g : l)
                System.out.println("******\n" + g);
            System.out.println("******");
        }
    }
}
