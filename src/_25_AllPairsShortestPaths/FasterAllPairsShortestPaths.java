package _25_AllPairsShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;

import static _25_AllPairsShortestPaths.Util.VertexMatrix;

public abstract class FasterAllPairsShortestPaths<T>
{
    public static <T> VertexMatrix<T, Integer> fasterAllPairsShortestPaths(Graph<T> graph) {
        VertexMatrix<T, Integer> L = Util.asMatrix(graph, Integer.MAX_VALUE, 0);
        int n = graph.getVertices().size();
        int m = 1;
        while (m < n - 1) {
            L = Util.extendShortestPaths(L, L);
            m *= 2;
        }
        return L;
    }

    private static class Example
    {
        public static void main(String[] args) {
            Graph<Integer> graph = new Graph<>(true);
            Integer[] V = {1, 2, 3, 4, 5};
            Integer[][] E = {
                    {1, 2}, {1, 3}, {1, 5}, {2, 4}, {2, 5},
                    {3, 2}, {4, 1}, {4, 3}, {5, 4}
            };
            Integer[] W = {3, 8, -4, 1, 7, 4, 2, -5, 6};

            for (Integer i : V)
                graph.addVertex(i);
            for (int i = 0; i < E.length; i++)
                graph.addEdge(E[i][0], E[i][1], W[i]);

            System.out.println(FasterAllPairsShortestPaths.fasterAllPairsShortestPaths(graph));
        }
    }
}
