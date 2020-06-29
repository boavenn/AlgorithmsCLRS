package _35_ApproximationAlgorithms;

import _22_ElementaryGraphAlgorithms.Graph;
import _23_MinimumSpanningTrees.PrimAlgorithm;

import java.util.LinkedList;
import java.util.List;

import static _22_ElementaryGraphAlgorithms.Graph.Edge;
import static _22_ElementaryGraphAlgorithms.Graph.Vertex;

public final class TSPTour
{
    public static <T> List<Vertex<T>> find(Graph<T> g, Vertex<T> root) {
        List<Vertex<T>> res = new LinkedList<>();
        List<Edge<T>> msp = PrimAlgorithm.findMST(g, root);

        res.add(root);
        for (Edge<T> e : msp)
            res.add(e.getDest());
        res.add(root);

        return res;
    }
}
