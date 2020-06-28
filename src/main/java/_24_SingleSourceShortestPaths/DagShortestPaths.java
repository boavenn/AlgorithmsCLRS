package _24_SingleSourceShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;
import _22_ElementaryGraphAlgorithms.TopologicalSort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public final class DagShortestPaths<T>
{
    private Map<Vertex<T>, Vertex<T>> predecessors;
    private Map<Vertex<T>, Integer> distance;

    public void process(Graph<T> graph, Vertex<T> root) {
        distance = Util.initializeSource(graph.vertices(), root);
        predecessors = new HashMap<>();
        List<Vertex<T>> vertices = TopologicalSort.sort(graph);

        for (Vertex<T> vertex : vertices) {
            for (Edge<T> edge : graph.adjacentEdgesOf(vertex)) {
                if (Util.relaxEdge(distance, edge))
                    predecessors.put(edge.getDest(), edge.getSrc());
            }
        }
    }

    public static <T> Map<Vertex<T>, Integer> distances(Graph<T> graph, Vertex<T> root) {
        DagShortestPaths<T> dsp = new DagShortestPaths<>();
        dsp.process(graph, root);
        return dsp.distance;
    }

    public static <T> Map<Vertex<T>, Vertex<T>> predecessors(Graph<T> graph, Vertex<T> root) {
        DagShortestPaths<T> dsp = new DagShortestPaths<>();
        dsp.process(graph, root);
        return dsp.predecessors;
    }

    public Map<Vertex<T>, Vertex<T>> getPredecessors() {
        return predecessors;
    }

    public Map<Vertex<T>, Integer> getDistance() {
        return distance;
    }
}
