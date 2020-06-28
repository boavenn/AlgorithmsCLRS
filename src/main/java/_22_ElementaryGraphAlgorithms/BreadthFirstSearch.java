package _22_ElementaryGraphAlgorithms;

import java.util.*;
import java.util.stream.Collectors;

import static _22_ElementaryGraphAlgorithms.Graph.Edge;
import static _22_ElementaryGraphAlgorithms.Graph.Vertex;

public final class BreadthFirstSearch<T>
{
    private List<Edge<T>> path;
    private Map<Vertex<T>, Vertex<T>> predecessors;

    public void process(Graph<T> graph, Vertex<T> source) {
        path = new LinkedList<>();
        predecessors = new HashMap<>();
        HashSet<Vertex<T>> visited = new HashSet<>();

        Queue<Vertex<T>> queue = new LinkedList<>();
        queue.add(source);
        visited.add(source);

        while (!queue.isEmpty()) {
            Vertex<T> vertex = queue.poll();
            for (Vertex<T> v : graph.adjacentVerticesOf(vertex)) {
                if (!visited.contains(v)) {
                    visited.add(v);
                    queue.add(v);
                    path.add(new Edge<>(vertex, v));
                    predecessors.putIfAbsent(v, vertex);
                }
            }
        }
    }

    public static <T> Map<Vertex<T>, Vertex<T>> predecessors(Graph<T> graph, Vertex<T> source) {
        BreadthFirstSearch<T> bfs = new BreadthFirstSearch<>();
        bfs.process(graph, source);
        return bfs.predecessors;
    }

    public static <T> List<Edge<T>> path(Graph<T> graph, Vertex<T> source) {
        BreadthFirstSearch<T> bfs = new BreadthFirstSearch<>();
        bfs.process(graph, source);
        return bfs.path;
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
