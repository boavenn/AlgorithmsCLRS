package _25_AllPairsShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;

import static _25_AllPairsShortestPaths.Util.VertexMatrix;

public final class SlowAllPairsShortestPaths
{
    public static <T> VertexMatrix<T, Integer> process(Graph<T> graph) {
        VertexMatrix<T, Integer> W = Util.asAdjacencyMatrix(graph, Integer.MAX_VALUE, 0);
        VertexMatrix<T, Integer> L = Util.asAdjacencyMatrix(graph, Integer.MAX_VALUE, 0);

        int n = graph.vertices().size();
        for (int m = 2; m < n; m++)
            L = Util.extendShortestPaths(L, W);

        return L;
    }
}
