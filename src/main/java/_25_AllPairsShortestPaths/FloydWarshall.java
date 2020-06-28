package _25_AllPairsShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;

import static _22_ElementaryGraphAlgorithms.Graph.Edge;
import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _25_AllPairsShortestPaths.Util.VertexMatrix;

public final class FloydWarshall<T>
{
    private VertexMatrix<T, Integer> distance;
    private VertexMatrix<T, Vertex<T>> predecessorsMatrix;

    public void process(Graph<T> graph) {
        distance = Util.asAdjacencyMatrix(graph, Integer.MAX_VALUE, 0);
        predecessorsMatrix = new VertexMatrix<>(graph.vertices(), null);

        for (Edge<T> e : graph.edges())
            predecessorsMatrix.set(e.getSrc(), e.getDest(), e.getSrc());
        for (Vertex<T> v : graph.vertices())
            predecessorsMatrix.set(v, v, null);

        for (Vertex<T> k : graph.vertices()) {
            VertexMatrix<T, Integer> temp = new VertexMatrix<>(graph.vertices(), Integer.MAX_VALUE);
            for (Vertex<T> i : graph.vertices()) {
                for (Vertex<T> j : graph.vertices()) {
                    Integer ik = distance.get(i, k);
                    Integer kj = distance.get(k, j);
                    Integer ij = distance.get(i, j);
                    if (!Util.sumOverflow(ik, kj)) {
                        temp.set(i, j, Math.min(ij, ik + kj));
                        if (ik + kj >= ij)
                            predecessorsMatrix.set(i, j, predecessorsMatrix.get(i, j));
                        else
                            predecessorsMatrix.set(i, j, predecessorsMatrix.get(k, j));
                    } else {
                        temp.set(i, j, ij);
                        predecessorsMatrix.set(i, j, predecessorsMatrix.get(i, j));
                    }
                }
            }
            distance = temp;
        }
    }

    public static <T> VertexMatrix<T, Integer> distances(Graph<T> graph) {
        FloydWarshall<T> fw = new FloydWarshall<>();
        fw.process(graph);
        return fw.distance;
    }

    public static <T> VertexMatrix<T, Vertex<T>> predecessorsMatrix(Graph<T> graph) {
        FloydWarshall<T> fw = new FloydWarshall<>();
        fw.process(graph);
        return fw.predecessorsMatrix;
    }

    public VertexMatrix<T, Integer> getDistance() {
        return distance;
    }

    public VertexMatrix<T, Vertex<T>> getPredecessorsMatrix() {
        return predecessorsMatrix;
    }
}
