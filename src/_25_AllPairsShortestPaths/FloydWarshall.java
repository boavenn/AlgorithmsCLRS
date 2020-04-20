package _25_AllPairsShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;

import static _25_AllPairsShortestPaths.Util.VertexMatrix;
import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public abstract class FloydWarshall<T>
{
    public static <T> VertexMatrix<T, Integer> floydWarshallDistances(Graph<T> graph) {
        VertexMatrix<T, Integer> M1 = Util.asMatrix(graph, Integer.MAX_VALUE, 0);

        for (Vertex<T> k : graph.getVertices()) {
            VertexMatrix<T, Integer> M2 = new VertexMatrix<>(graph.getVertices(), Integer.MAX_VALUE);
            for (Vertex<T> i : graph.getVertices()) {
                for (Vertex<T> j : graph.getVertices()) {
                    Integer ik = M1.get(i, k);
                    Integer kj = M1.get(k, j);
                    Integer ij = M1.get(i, j);
                    if (!Util.sumOverflow(ik, kj))
                        M2.set(i, j, Math.min(ij, ik + kj));
                    else
                        M2.set(i, j, ij);
                }
            }
            M1 = M2;
        }

        return M1;
    }

    public static <T> VertexMatrix<T, Vertex<T>> floydWarshallPaths(Graph<T> graph) {
        VertexMatrix<T, Integer> M1 = Util.asMatrix(graph, Integer.MAX_VALUE, 0);
        VertexMatrix<T, Vertex<T>> paths = new VertexMatrix<>(graph.getVertices(), null);

        for (Edge<T> e : graph.getEdges())
            paths.set(e.getSrc(), e.getDest(), e.getSrc());
        for (Vertex<T> v : graph.getVertices())
            paths.set(v, v, null);

        for (Vertex<T> k : graph.getVertices()) {
            VertexMatrix<T, Integer> M2 = new VertexMatrix<>(graph.getVertices(), Integer.MAX_VALUE);
            for (Vertex<T> i : graph.getVertices()) {
                for (Vertex<T> j : graph.getVertices()) {
                    Integer ik = M1.get(i, k);
                    Integer kj = M1.get(k, j);
                    Integer ij = M1.get(i, j);
                    if (!Util.sumOverflow(ik, kj)) {
                        M2.set(i, j, Math.min(ij, ik + kj));
                        if (ik + kj >= ij)
                            paths.set(i, j, paths.get(i, j));
                        else
                            paths.set(i, j, paths.get(k, j));
                    }
                    else {
                        M2.set(i, j, ij);
                        paths.set(i, j, paths.get(i, j));
                    }
                }
            }
            M1 = M2;
        }

        return paths;
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

            System.out.println("Shortest distances: ");
            System.out.println(FloydWarshall.floydWarshallDistances(graph));

            System.out.println("\nShortest paths: ");
            VertexMatrix<Integer, Vertex<Integer>> paths = FloydWarshall.floydWarshallPaths(graph);
            System.out.println(paths);

            System.out.println("\nPath from vertex 3 to 5");
            System.out.println(Util.getAllPairsShortestPath(paths, new Vertex<>(3), new Vertex<>(5)));
        }
    }
}
