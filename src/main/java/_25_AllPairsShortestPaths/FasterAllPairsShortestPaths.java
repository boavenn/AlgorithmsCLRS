package _25_AllPairsShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;

import static _25_AllPairsShortestPaths.Util.VertexMatrix;

public final class FasterAllPairsShortestPaths
{
    public static <T> VertexMatrix<T, Integer> process(Graph<T> graph) {
        VertexMatrix<T, Integer> L = Util.asAdjacencyMatrix(graph, Integer.MAX_VALUE, 0);

        int n = graph.vertices().size();
        int m = 1;
        while (m < n - 1) {
            L = Util.extendShortestPaths(L, L);
            m *= 2;
        }
        return L;
    }
}
