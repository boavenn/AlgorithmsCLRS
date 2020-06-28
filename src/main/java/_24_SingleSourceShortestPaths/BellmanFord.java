package _24_SingleSourceShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;

import java.util.HashMap;
import java.util.Map;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public final class BellmanFord<T>
{
    private Map<Vertex<T>, Vertex<T>> predecessors;
    private Map<Vertex<T>, Integer> distance;

    public void process(Graph<T> graph, Vertex<T> source) {
        distance = Util.initializeSource(graph.vertices(), source);
        predecessors = new HashMap<>();

        int numOfVertices = distance.size();
        for (int i = 1; i < numOfVertices; i++) {
            for (Edge<T> edge : graph.edges()) {
                if (Util.relaxEdge(distance, edge))
                    predecessors.put(edge.getDest(), edge.getSrc());
            }
        }
        validateGraph(graph);
    }

    private void validateGraph(Graph<T> graph) {
        for (Edge<T> e : graph.edges()) {
            Vertex<T> src = e.getSrc();
            Vertex<T> dest = e.getDest();
            if (distance.get(src) != Integer.MAX_VALUE && distance.get(dest) > distance.get(src) + e.getWeight())
                throw new IllegalArgumentException("No shortest paths found in the given graph");
        }
    }

    public static <T> Map<Vertex<T>, Integer> distances(Graph<T> graph, Vertex<T> source) {
        BellmanFord<T> bf = new BellmanFord<>();
        bf.process(graph, source);
        return bf.distance;
    }

    public static <T> Map<Vertex<T>, Vertex<T>> predecessors(Graph<T> graph, Vertex<T> source) {
        BellmanFord<T> bf = new BellmanFord<>();
        bf.process(graph, source);
        return bf.predecessors;
    }

    public Map<Vertex<T>, Vertex<T>> getPredecessors() {
        return predecessors;
    }

    public Map<Vertex<T>, Integer> getDistance() {
        return distance;
    }
}
