package _24_SingleSourceShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public final class Dijkstra<T>
{
    private static class Node<T>
    {
        Vertex<T> vertex;
        Integer distance;

        public Node(Vertex<T> vertex, Integer distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }

    private Map<Vertex<T>, Integer> distance;
    private Map<Vertex<T>, Vertex<T>> predecessors;

    public void process(Graph<T> graph, Vertex<T> source) {
        distance = Util.initializeSource(graph.vertices(), source);
        predecessors = new HashMap<>();
        HashSet<Vertex<T>> visited = new HashSet<>();
        PriorityQueue<Node<T>> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));

        queue.add(new Node<>(source, 0));

        while (!queue.isEmpty()) {
            Vertex<T> v = queue.poll().vertex;
            visited.add(v);
            for (Edge<T> edge : graph.adjacentEdgesOf(v)) {
                Vertex<T> dest = edge.getDest();
                if (!visited.contains(dest)) {
                    if (Util.relaxEdge(distance, edge)) {
                        predecessors.put(dest, v);
                    }
                    queue.add(new Node<>(dest, distance.get(dest)));
                }
            }
        }
    }

    public static <T> Map<Vertex<T>, Integer> distances(Graph<T> graph, Vertex<T> source) {
        Dijkstra<T> dijkstra = new Dijkstra<>();
        dijkstra.process(graph, source);
        return dijkstra.distance;
    }

    public static <T> Map<Vertex<T>, Vertex<T>> predecessors(Graph<T> graph, Vertex<T> source) {
        Dijkstra<T> dijkstra = new Dijkstra<>();
        dijkstra.process(graph, source);
        return dijkstra.predecessors;
    }

    public Map<Vertex<T>, Integer> getDistance() {
        return distance;
    }

    public Map<Vertex<T>, Vertex<T>> getPredecessors() {
        return predecessors;
    }
}
