package _23_MinimumSpanningTrees;

import _21_DataStructuresForDisjointSets.WeightedUnionFind;
import _22_ElementaryGraphAlgorithms.Graph;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public final class KruskalAlgorithm<T>
{
    public static <T> List<Edge<T>> findMST(Graph<T> graph) {
        if (graph.isDirected())
            throw new IllegalArgumentException("Graph is directed");

        List<Edge<T>> result = new LinkedList<>();
        WeightedUnionFind<T> sets = new WeightedUnionFind<>();
        List<Edge<T>> edges = graph.edges();
        edges.sort(Comparator.comparingInt(Edge::getWeight));

        for (Vertex<T> v : graph.vertices())
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
}
