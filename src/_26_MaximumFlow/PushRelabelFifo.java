package _26_MaximumFlow;

import _22_ElementaryGraphAlgorithms.Graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static _26_MaximumFlow.Util.Node;
import static _26_MaximumFlow.Util.Pipe;
import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _25_AllPairsShortestPaths.Util.VertexMatrix;

public abstract class PushRelabelFifo
{
    public static <T> Integer pushRelabelFifo(Graph<T> graph, Vertex<T> src, Vertex<T> sink) {
        Map<Vertex<T>, Node> nodes = new HashMap<>();
        VertexMatrix<T, Pipe> pipes = new VertexMatrix<>(graph.getVertices(), null);
        Queue<Vertex<T>> queue = new LinkedList<>();
        Graph<T> resGraph = Util.initializePreflow(graph, src, nodes, pipes);

        for (Vertex<T> v : resGraph.getAdjacentVerticesOf(src)) {
            if (!v.equals(sink))
                queue.add(v);
        }

        while (!queue.isEmpty()) {
            Vertex<T> u = queue.poll();
            Vertex<T> v = getPushLegalNode(resGraph, u, nodes, pipes);
            // push
            while (nodes.get(u).excess > 0 && v != null) {
                Util.push(u, v, nodes, pipes);
                if (!v.equals(src) && !v.equals(sink))
                    queue.add(v);
                v = getPushLegalNode(resGraph, u, nodes, pipes);
            }
            // relabel
            if (nodes.get(u).excess > 0) {
                Util.relabel(resGraph, u, nodes, pipes);
                queue.add(u);
            }
        }

        return nodes.get(sink).excess;
    }

    private static <T> Vertex<T> getPushLegalNode(Graph<T> resGraph, Vertex<T> u, Map<Vertex<T>, Node> nodes, VertexMatrix<T, Pipe> pipes) {
        for (Vertex<T> v : resGraph.getAdjacentVerticesOf(u)) {
            Node nu = nodes.get(u);
            Node nv = nodes.get(v);
            Pipe puv = pipes.get(u, v);
            if (nu.height == nv.height + 1 && puv.capacity - puv.flow > 0)
                return v;
        }
        return null;
    }

    public static class Example
    {
        public static void main(String[] args) {
            Graph<String> graph = new Graph<>(true);
            String[] V = {"s", "v1", "v2", "v3", "v4", "t"};
            String[][] E = {
                    {"s", "v1"}, {"v1", "v3"}, {"v3", "t"},
                    {"s", "v2"}, {"v2", "v4"}, {"v4", "t"},
                    {"v2", "v1"}, {"v3", "v2"}, {"v4", "v3"},
            };
            Integer[] W = {16, 12, 20, 13, 14, 4, 4, 9, 7};

            for (String s : V)
                graph.addVertex(s);
            for (int i = 0; i < E.length; i++)
                graph.addEdge(E[i][0], E[i][1], W[i]);

            System.out.print("Maximum flow: ");
            System.out.println(PushRelabelFifo.pushRelabelFifo(graph, new Vertex<>("s"), new Vertex<>("t")));

            Graph<Integer> graph1 = new Graph<>(true);
            Integer[] V1 = {0, 1, 2, 3, 4, 5, 6, 7};
            Integer[][] E1 = {
                    {3, 5}, {3, 4}, {4, 5}, {2, 3}, {1, 4}, {1, 3}, {0, 2}, {0, 1},
                    {1, 2}, {4, 7}, {5, 7}, {3, 7}, {6, 7}, {3, 6}, {0, 6}, {4, 2}
            };
            Integer[] W1 = {8, 20, 1, 26, 13, 10, 1, 38, 8, 7, 7, 1, 27, 24, 2, 2};

            for (Integer i : V1)
                graph1.addVertex(i);
            for (int i = 0; i < E1.length; i++)
                graph1.addEdge(E1[i][0], E1[i][1], W1[i]);

            System.out.print("Maximum flow: ");
            System.out.println(PushRelabelFifo.pushRelabelFifo(graph1, new Vertex<>(0), new Vertex<>(7)));
        }
    }
}
