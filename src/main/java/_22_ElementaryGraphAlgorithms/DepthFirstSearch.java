package _22_ElementaryGraphAlgorithms;

import java.util.*;
import java.util.stream.Collectors;

import static _22_ElementaryGraphAlgorithms.Graph.Edge;
import static _22_ElementaryGraphAlgorithms.Graph.Vertex;

public final class DepthFirstSearch<T>
{
    private List<Edge<T>> path;
    private Map<Vertex<T>, Vertex<T>> predecessors;

    public void process(Graph<T> graph, Vertex<T> source) {
        path = new LinkedList<>();
        predecessors = new HashMap<>();
        HashSet<Vertex<T>> visited = new HashSet<>();

        Deque<Vertex<T>> stack = new ArrayDeque<>();
        stack.push(source);
        visited.add(source);

        while (!stack.isEmpty()) {
            Vertex<T> vertex = stack.pop();
            if (predecessors.containsKey(vertex)) {
                path.add(new Edge<>(predecessors.get(vertex), vertex));
            }
            for (Vertex<T> v : graph.adjacentVerticesOf(vertex)) {
                if (!visited.contains(v)) {
                    visited.add(v);
                    stack.push(v);
                    predecessors.putIfAbsent(v, vertex);
                }
            }
        }
    }

    public static <T> List<Edge<T>> path(Graph<T> graph, Vertex<T> source) {
        DepthFirstSearch<T> dfs = new DepthFirstSearch<>();
        dfs.process(graph, source);
        return dfs.path;
    }

    public static <T> Map<Vertex<T>, Vertex<T>> predecessors(Graph<T> graph, Vertex<T> source) {
        DepthFirstSearch<T> dfs = new DepthFirstSearch<>();
        dfs.process(graph, source);
        return dfs.predecessors;
    }

    public static <T> List<Vertex<T>> vertexOrder(Graph<T> graph, Vertex<T> source) {
        List<Vertex<T>> vertices = path(graph, source).stream().map(Edge::getDest).collect(Collectors.toList());
        vertices.add(0, source);
        return vertices;
    }

    public List<Edge<T>> getPath() {
        return path;
    }

    public Map<Vertex<T>, Vertex<T>> getPredecessors() {
        return predecessors;
    }

    public List<Vertex<T>> getVertexOrder() {
        if (path == null)
            throw new IllegalStateException("No graph has been processed yet");
        List<Vertex<T>> vertices = path.stream().map(Edge::getDest).collect(Collectors.toList());
        vertices.add(0, path.get(0).getSrc());
        return vertices;
    }
}
