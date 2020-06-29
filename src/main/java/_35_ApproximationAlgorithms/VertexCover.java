package _35_ApproximationAlgorithms;

import _22_ElementaryGraphAlgorithms.Graph;

import java.util.LinkedList;
import java.util.List;

import static _22_ElementaryGraphAlgorithms.Graph.Edge;
import static _22_ElementaryGraphAlgorithms.Graph.Vertex;

public final class VertexCover
{
    public static <T> List<Vertex<T>> process(Graph<T> graph) {
        List<Vertex<T>> res = new LinkedList<>();
        List<Edge<T>> util = new LinkedList<>(graph.edges());
        while (!util.isEmpty()) {
            Edge<T> edge = util.remove(0);
            res.add(edge.getSrc());
            res.add(edge.getDest());

            for (Edge<T> e : graph.adjacentEdgesOf(edge.getSrc()))
                util.removeIf(a -> a.equals(e) || (a.getSrc().equals(e.getDest()) && a.getDest().equals(e.getSrc())));
            for (Edge<T> e : graph.adjacentEdgesOf(edge.getDest()))
                util.removeIf(a -> a.equals(e) || (a.getSrc().equals(e.getDest()) && a.getDest().equals(e.getSrc())));
        }
        return res;
    }
}
