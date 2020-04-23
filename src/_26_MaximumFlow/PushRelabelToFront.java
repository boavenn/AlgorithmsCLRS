package _26_MaximumFlow;

import _22_ElementaryGraphAlgorithms.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static _26_MaximumFlow.Util.Node;
import static _26_MaximumFlow.Util.Pipe;
import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _25_AllPairsShortestPaths.Util.VertexMatrix;

public abstract class PushRelabelToFront
{
    public static <T> Integer pushRelabelToFront(Graph<T> graph, Vertex<T> src, Vertex<T> sink) {
        Map<Vertex<T>, Node> nodes = new HashMap<>();
        VertexMatrix<T, Pipe> pipes = new VertexMatrix<>(graph.getVertices(), null);
        Graph<T> resGraph = Util.initializePreflow(graph, src, nodes, pipes);

        ArrayList<Vertex<T>> list = new ArrayList<>(resGraph.getVertices());
        list.remove(src);
        list.remove(sink);

        int idx = 0;
        Vertex<T> u = list.get(idx++);
        while (u != null) {
            int oldHeight = nodes.get(u).height;
            discharge(resGraph, u, nodes, pipes);
            if (nodes.get(u).height > oldHeight) {
                shiftToFront(list, idx - 1);
                idx = 1;
            }
            u = idx >= list.size() ? null : list.get(idx++);
        }

        return nodes.get(sink).excess;
    }

    private static <T> void shiftToFront(ArrayList<Vertex<T>> l, int idx) {
        Vertex<T> u = l.get(idx);
        for (int i = idx; i >= 1; i--)
            l.set(i, l.get(i - 1));
        l.set(0, u);
    }

    private static <T> void discharge(Graph<T> resGraph, Vertex<T> u, Map<Vertex<T>, Node> nodes, VertexMatrix<T, Pipe> pipes) {
        ArrayList<Vertex<T>> adj = new ArrayList<>(resGraph.getAdjacentVerticesOf(u));

        int idx = 0;
        Vertex<T> v = adj.get(idx++);
        while (nodes.get(u).excess > 0) {
            if (v == null) {
                Util.relabel(resGraph, u, nodes, pipes);
                idx = 0;
                v = adj.get(idx++);
            }
            else if (pipes.get(u, v).capacity - pipes.get(u, v).flow > 0 && nodes.get(u).height == nodes.get(v).height + 1)
                Util.push(u, v, nodes, pipes);
            else
                v = idx >= adj.size() ? null : adj.get(idx++);
        }
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
            System.out.println(PushRelabelToFront.pushRelabelToFront(graph, new Vertex<>("s"), new Vertex<>("t")));

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
            System.out.println(PushRelabelToFront.pushRelabelToFront(graph1, new Vertex<>(0), new Vertex<>(7)));
        }
    }
}
