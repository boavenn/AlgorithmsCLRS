package _26_MaximumFlow;

import _22_ElementaryGraphAlgorithms.Graph;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _25_AllPairsShortestPaths.Util.VertexMatrix;
import static _25_AllPairsShortestPaths.Util.asAdjacencyMatrix;

public final class EdmondsCarp
{
    public static <T> int process(Graph<T> graph, Vertex<T> source, Vertex<T> sink) {
        Graph<T> resGraph = Util.createResidualGraph(graph);
        // Edge weights matrix for faster edge weight access
        VertexMatrix<T, Integer> edgeWeights = asAdjacencyMatrix(resGraph, Integer.MAX_VALUE, 0);
        Map<Vertex<T>, Vertex<T>> predecessors = augmentedBfs(resGraph, edgeWeights, source);
        int maxFlow = 0;

        while (predecessors.containsKey(sink)) {
            int pathFlow = Integer.MAX_VALUE;

            // calculate maximum flow of shortest path from sink to source
            Vertex<T> v = sink;
            while (!v.equals(source)) {
                Vertex<T> u = predecessors.get(v);
                pathFlow = Math.min(pathFlow, edgeWeights.get(u, v));
                v = u;
            }

            // apply that flow to edges on the path
            v = sink;
            while (!v.equals(source)) {
                Vertex<T> u = predecessors.get(v);
                edgeWeights.set(u, v, edgeWeights.get(u, v) - pathFlow);
                edgeWeights.set(v, u, edgeWeights.get(v, u) + pathFlow);
                v = u;
            }
            maxFlow += pathFlow;
            predecessors = augmentedBfs(resGraph, edgeWeights, source);
        }

        return maxFlow;
    }

    private static <T> Map<Vertex<T>, Vertex<T>> augmentedBfs(Graph<T> graph, VertexMatrix<T, Integer> weights, Vertex<T> root) {
        Queue<Vertex<T>> queue = new LinkedList<>();
        HashSet<Vertex<T>> visited = new HashSet<>();
        Map<Vertex<T>, Vertex<T>> predecessors = new HashMap<>();

        queue.add(root);
        visited.add(root);

        while (!queue.isEmpty()) {
            Vertex<T> vertex = queue.poll();
            for (Vertex<T> v : graph.adjacentVerticesOf(vertex)) {
                if (!visited.contains(v) && weights.get(vertex, v) > 0) {
                    visited.add(v);
                    queue.add(v);
                    predecessors.putIfAbsent(v, vertex);
                }
            }
        }

        return predecessors;
    }
}
