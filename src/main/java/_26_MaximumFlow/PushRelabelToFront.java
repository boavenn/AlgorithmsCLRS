package _26_MaximumFlow;

import _22_ElementaryGraphAlgorithms.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _25_AllPairsShortestPaths.Util.VertexMatrix;
import static _26_MaximumFlow.Util.Node;
import static _26_MaximumFlow.Util.Pipe;

public final class PushRelabelToFront
{
    public static <T> Integer process(Graph<T> graph, Vertex<T> src, Vertex<T> sink) {
        Map<Vertex<T>, Node> nodes = new HashMap<>();
        VertexMatrix<T, Pipe> pipes = new VertexMatrix<>(graph.vertices(), null);
        Graph<T> resGraph = Util.initializePreflow(graph, src, nodes, pipes);

        ArrayList<Vertex<T>> vertices = new ArrayList<>(resGraph.vertices());
        vertices.remove(src);
        vertices.remove(sink);

        int idx = 0;
        Vertex<T> u = vertices.get(idx++);
        while (u != null) {
            int oldHeight = nodes.get(u).getHeight();
            discharge(resGraph, u, nodes, pipes);
            if (nodes.get(u).getHeight() > oldHeight) {
                shiftVertexToFront(vertices, idx - 1);
                idx = 1;
            }
            u = idx >= vertices.size() ? null : vertices.get(idx++);
        }

        return nodes.get(sink).getExcess();
    }

    private static <T> void shiftVertexToFront(ArrayList<Vertex<T>> vertices, int idx) {
        Vertex<T> u = vertices.get(idx);
        for (int i = idx; i >= 1; i--)
            vertices.set(i, vertices.get(i - 1));
        vertices.set(0, u);
    }

    private static <T> void discharge(Graph<T> resGraph, Vertex<T> vertex,
                                      Map<Vertex<T>, Node> nodes, VertexMatrix<T, Pipe> pipes) {
        ArrayList<Vertex<T>> adjacentVertices = new ArrayList<>(resGraph.adjacentVerticesOf(vertex));

        int idx = 0;
        Vertex<T> v = adjacentVertices.get(idx++);
        while (nodes.get(vertex).getExcess() > 0) {
            if (v == null) {
                Util.relabel(resGraph, vertex, nodes, pipes);
                idx = 0;
                v = adjacentVertices.get(idx++);
            } else if (pipes.get(vertex, v).getCapacity() - pipes.get(vertex, v).getFlow() > 0 &&
                    nodes.get(vertex).getHeight() == nodes.get(v).getHeight() + 1)
                Util.push(vertex, v, nodes, pipes);
            else
                v = idx >= adjacentVertices.size() ? null : adjacentVertices.get(idx++);
        }
    }
}
