package _25_AllPairsShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _25_AllPairsShortestPaths.Util.VertexMatrix;

public final class TransitiveClosure
{
    public static <T> VertexMatrix<T, Integer> process(Graph<T> graph) {
        VertexMatrix<T, Integer> matrix = new VertexMatrix<>(graph.vertices(), 0);
        for (Vertex<T> i : graph.vertices()) {
            for (Vertex<T> j : graph.vertices()) {
                if (i.equals(j) || graph.adjacentEdgesOf(i).stream().anyMatch(e -> e.getDest().equals(j)))
                    matrix.set(i, j, 1);
            }
        }

        for (Vertex<T> k : graph.vertices()) {
            VertexMatrix<T, Integer> temp = new VertexMatrix<>(graph.vertices(), 0);
            for (Vertex<T> i : graph.vertices()) {
                for (Vertex<T> j : graph.vertices()) {
                    Integer ik = matrix.get(i, k);
                    Integer kj = matrix.get(k, j);
                    Integer ij = matrix.get(i, j);
                    temp.set(i, j, Math.max(ij, Math.min(ik, kj))); // temp[i, j] = ij || (ik && kj)
                }
            }
            matrix = temp;
        }

        return matrix;
    }
}
