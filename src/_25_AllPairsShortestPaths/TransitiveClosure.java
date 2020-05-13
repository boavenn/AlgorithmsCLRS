package _25_AllPairsShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;

import static _25_AllPairsShortestPaths.Util.VertexMatrix;
import static _22_ElementaryGraphAlgorithms.Graph.Vertex;

public final class TransitiveClosure<T>
{
    public static <T> VertexMatrix<T, Integer> transitiveClosure(Graph<T> graph) {
        VertexMatrix<T, Integer> T1 = new VertexMatrix<>(graph.getVertices(), 0);
        for (Vertex<T> i : graph.getVertices()) {
            for (Vertex<T> j : graph.getVertices()) {
                if (i.equals(j) || graph.getAdjacentEdgesOf(i).stream().anyMatch(e -> e.getDest().equals(j)))
                    T1.set(i, j, 1);
            }
        }

        for (Vertex<T> k : graph.getVertices()) {
            VertexMatrix<T, Integer> T2 = new VertexMatrix<>(graph.getVertices(), 0);
            for (Vertex<T> i : graph.getVertices()) {
                for (Vertex<T> j : graph.getVertices()) {
                    Integer ik = T1.get(i, k);
                    Integer kj = T1.get(k, j);
                    Integer ij = T1.get(i, j);
                    T2.set(i, j, Math.max(ij, Math.min(ik, kj))); // v = ij || (ik && kj)
                }
            }
            T1 = T2;
        }

        return T1;
    }

    private static class Example
    {
        public static void main(String[] args) {
            Graph<Integer> graph = new Graph<>(true);
            Integer[] V = {1, 2, 3, 4};
            Integer[][] E = {
                    {1, 1}, {2, 2}, {2, 3}, {2, 4}, {3, 2},
                    {3, 3}, {4, 1}, {4, 3}, {4, 4},
            };

            for (Integer i : V)
                graph.addVertex(i);
            for (Integer[] integers : E)
                graph.addEdge(integers[0], integers[1]);

            System.out.println(TransitiveClosure.transitiveClosure(graph));
        }
    }
}
