package _35_ApproximationAlgorithms;

import _22_ElementaryGraphAlgorithms.Graph;
import _23_MinimumSpanningTrees.PrimAlgorithm;

import java.util.LinkedList;
import java.util.List;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public final class TSPTour
{
    public static <T> List<Vertex<T>> approxTSPTour(Graph<T> g, Vertex<T> root) {
        List<Vertex<T>> res = new LinkedList<>();
        List<Edge<T>> msp = PrimAlgorithm.findMST(g, root);

        res.add(root);
        for (Edge<T> e : msp)
            res.add(e.getDest());
        res.add(root);

        return res;
    }

    private static class Example
    {
        public static void main(String[] args) {
            Graph<Integer> graph = new Graph<>(false);
            Integer[] V = {1, 2, 3, 4};
            Integer[][] E = {
                    {1, 2, 10}, {1, 4, 20}, {1, 3, 15},
                    {2, 4, 25}, {4, 3, 30}, {2, 3, 35}
            };

            for (Integer i : V)
                graph.addVertex(i);
            for (Integer[] i : E)
                graph.addEdge(i[0], i[1], i[2]);

            System.out.println(approxTSPTour(graph, new Vertex<>(1)));
        }
    }
}
