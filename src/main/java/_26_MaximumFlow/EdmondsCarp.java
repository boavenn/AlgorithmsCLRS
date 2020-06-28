package _26_MaximumFlow;

import _22_ElementaryGraphAlgorithms.Graph;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _25_AllPairsShortestPaths.Util.VertexMatrix;
import static _25_AllPairsShortestPaths.Util.*;

public final class EdmondsCarp
{
    public static <T> Integer edmondsCarp(Graph<T> graph, Vertex<T> src, Vertex<T> sink) {
        Graph<T> resGraph = Util.createResidualGraph(graph);
        // Edge weights matrix for faster weight searching
        VertexMatrix<T, Integer> weights = asAdjacencyMatrix(resGraph, Integer.MAX_VALUE, 0);
        int maxFlow = 0;
        Map<Vertex<T>, Vertex<T>> parentMap = augmentedBfs(resGraph, weights, src);

        while (parentMap.containsKey(sink)) {
            int pathFlow = Integer.MAX_VALUE;
            Vertex<T> v = sink;
            Vertex<T> u;

            while (!v.equals(src)) {
                u = parentMap.get(v);
                pathFlow = Math.min(pathFlow, weights.get(u, v));
                v = u;
            }

            v = sink;
            while (!v.equals(src)) {
                u = parentMap.get(v);
                weights.set(u, v, weights.get(u, v) - pathFlow);
                weights.set(v, u, weights.get(v, u) + pathFlow);
                v = u;
            }
            maxFlow += pathFlow;
            parentMap = augmentedBfs(resGraph, weights, src);
        }

        return maxFlow;
    }

    private static <T> Map<Vertex<T>, Vertex<T>> augmentedBfs(Graph<T> graph, VertexMatrix<T, Integer> weights, Vertex<T> root) {
        Queue<Vertex<T>> queue = new LinkedList<>();
        HashSet<Vertex<T>> visited = new HashSet<>();
        Map<Vertex<T>, Vertex<T>> util = new HashMap<>();

        queue.add(root);
        visited.add(root);

        while (!queue.isEmpty()) {
            Vertex<T> vertex = queue.poll();
            for (Vertex<T> v : graph.adjacentVerticesOf(vertex)) {
                if (!visited.contains(v) && weights.get(vertex, v) > 0) {
                    visited.add(v);
                    queue.add(v);
                    util.putIfAbsent(v, vertex);
                }
            }
        }

        return util;
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
            System.out.println(EdmondsCarp.edmondsCarp(graph, new Vertex<>("s"), new Vertex<>("t")));

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
            System.out.println(EdmondsCarp.edmondsCarp(graph1, new Vertex<>(0), new Vertex<>(7)));
        }
    }
}
