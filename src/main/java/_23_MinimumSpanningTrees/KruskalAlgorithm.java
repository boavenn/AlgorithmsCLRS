package _23_MinimumSpanningTrees;

import _21_DataStructuresForDisjointSets.WeightedUnionFind;
import _22_ElementaryGraphAlgorithms.Graph;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public final class KruskalAlgorithm<T>
{
    public static <T> List<Edge<T>> calc(Graph<T> graph) {
        if (graph.isDirected())
            throw new IllegalArgumentException("Graph is directed");

        List<Vertex<T>> vertices = graph.vertices();
        List<Edge<T>> result = new LinkedList<>();
        // disjoint sets made from graph vertices
        WeightedUnionFind<T> sets = new WeightedUnionFind<>();
        // edges sorted by weight
        List<Edge<T>> edges = graph.edges();
        edges.sort(Comparator.comparingInt(Edge::getWeight));

        for (Vertex<T> v : vertices)
            sets.makeSet(v.getKey());

        for (Edge<T> e : edges) {
            T src = e.getSrc().getKey();
            T dest = e.getDest().getKey();
            if (!sets.connected(src, dest)) {
                result.add(e);
                sets.union(src, dest);
            }
        }

        return result;
    }

    private static class Example
    {
        public static void main(String[] args) {
            Graph<Character> graph = new Graph<>(false);

            Character[] V = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};
            Character[][] E = {
                    {'a', 'b'}, {'a', 'h'}, {'b', 'h'}, {'h', 'i'}, {'h', 'g'},
                    {'g', 'i'}, {'i', 'c'}, {'c', 'b'}, {'c', 'd'}, {'c', 'f'},
                    {'f', 'g'}, {'f', 'd'}, {'d', 'e'}, {'e', 'f'},
            };
            Integer[] W = {4, 8, 11, 7, 1, 6, 2, 8, 7, 4, 2, 14, 9, 10};

            for (Character c : V)
                graph.addVertex(c);
            for (int i = 0; i < E.length; i++)
                graph.addEdge(E[i][0], E[i][1], W[i]);

            System.out.println(graph);
            System.out.println(KruskalAlgorithm.calc(graph));
        }
    }
}
