package _22_ElementaryGraphAlgorithms;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;

public final class TopologicalSort<T>
{
    private Map<Vertex<T>, Boolean> visited; // true - permanent mark, false - temporary mark
    private Deque<Vertex<T>> stack;

    private List<Vertex<T>> process(Graph<T> graph) {
        if (!graph.isDirected())
            throw new IllegalArgumentException("Graph is not directed");

        visited = new HashMap<>();
        stack = new ArrayDeque<>();

        for (Vertex<T> v : graph.vertices()) {
            if (!visited.containsKey(v))
                visit(graph, v);
        }

        List<Vertex<T>> temp = new LinkedList<>();
        while (!stack.isEmpty())
            temp.add(stack.pop());
        return temp;
    }

    private void visit(Graph<T> graph, Vertex<T> vertex) {
        if (visited.containsKey(vertex) && visited.get(vertex)) // permanent mark
            return;
        if (visited.containsKey(vertex) && !visited.get(vertex)) // temporary mark
            throw new IllegalArgumentException("Graph is not a DAG");

        // temporary mark
        visited.put(vertex, false);

        for (Vertex<T> v : graph.adjacentVerticesOf(vertex))
            visit(graph, v);

        // permanent mark
        visited.put(vertex, true);
        stack.push(vertex);
    }

    public static <T> List<Vertex<T>> sort(Graph<T> graph) {
        TopologicalSort<T> ts = new TopologicalSort<>();
        return ts.process(graph);
    }
}
