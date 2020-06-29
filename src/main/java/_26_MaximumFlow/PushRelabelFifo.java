package _26_MaximumFlow;

import _22_ElementaryGraphAlgorithms.Graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _25_AllPairsShortestPaths.Util.VertexMatrix;
import static _26_MaximumFlow.Util.Node;
import static _26_MaximumFlow.Util.Pipe;

public final class PushRelabelFifo
{
    public static <T> Integer process(Graph<T> graph, Vertex<T> source, Vertex<T> sink) {
        Map<Vertex<T>, Node> nodes = new HashMap<>();
        VertexMatrix<T, Pipe> pipes = new VertexMatrix<>(graph.vertices(), null);
        Queue<Vertex<T>> queue = new LinkedList<>();
        Graph<T> resGraph = Util.initializePreflow(graph, source, nodes, pipes);

        for (Vertex<T> v : resGraph.adjacentVerticesOf(source)) {
            if (!v.equals(sink))
                queue.add(v);
        }

        while (!queue.isEmpty()) {
            Vertex<T> u = queue.poll();
            Vertex<T> v = getPushLegalNode(resGraph, u, nodes, pipes);
            // push
            while (nodes.get(u).getExcess() > 0 && v != null) {
                Util.push(u, v, nodes, pipes);
                if (!v.equals(source) && !v.equals(sink))
                    queue.add(v);
                v = getPushLegalNode(resGraph, u, nodes, pipes);
            }
            // relabel
            if (nodes.get(u).getExcess() > 0) {
                Util.relabel(resGraph, u, nodes, pipes);
                queue.add(u);
            }
        }

        return nodes.get(sink).getExcess();
    }

    private static <T> Vertex<T> getPushLegalNode(Graph<T> resGraph, Vertex<T> vertex,
                                                  Map<Vertex<T>, Node> nodes, VertexMatrix<T, Pipe> pipes) {
        for (Vertex<T> v : resGraph.adjacentVerticesOf(vertex)) {
            Pipe puv = pipes.get(vertex, v);
            if (nodes.get(vertex).getHeight() == nodes.get(v).getHeight() + 1 && puv.getCapacity() - puv.getFlow() > 0)
                return v;
        }
        return null;
    }
}
