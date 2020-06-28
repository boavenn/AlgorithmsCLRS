package _25_AllPairsShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Edge;
import static _22_ElementaryGraphAlgorithms.Graph.Vertex;

public final class Util
{
    public static class VertexMatrix<T, V>
    {
        private Map<Vertex<T>, Map<Vertex<T>, V>> matrix = new HashMap<>();

        public VertexMatrix(Collection<Vertex<T>> vertices, V defaultValue) {
            for (Vertex<T> v : vertices) {
                Map<Vertex<T>, V> map = new HashMap<>();
                for (Vertex<T> u : vertices) {
                    map.put(u, defaultValue);
                }
                matrix.put(v, map);
            }
        }

        public V get(Vertex<T> i, Vertex<T> j) {
            return matrix.get(i).get(j);
        }

        public void set(Vertex<T> i, Vertex<T> j, V value) {
            matrix.get(i).put(j, value);
        }

        public List<Vertex<T>> getVertices() {
            return new ArrayList<>(matrix.keySet());
        }

        @Override
        public String toString() {
            StringBuilder str = new StringBuilder();
            Formatter fmt = new Formatter();
            List<Vertex<T>> rows = getVertices();
            fmt.format("%10s", "");
            for (Vertex<T> v : rows)
                fmt.format("%-6s", v);
            for (Vertex<T> v : rows) {
                fmt.format("%n%6s:", v);
                for (Vertex<T> u : rows) {
                    fmt.format("%6s", matrix.get(v).get(u));
                }
            }
            str.append(fmt);
            return str.toString();
        }
    }

    public static <T> VertexMatrix<T, Integer> asAdjacencyMatrix(Graph<T> graph,
                                                                 Integer noEdgeValue, Integer sameVertexValue) {
        VertexMatrix<T, Integer> matrix = new VertexMatrix<>(graph.vertices(), noEdgeValue);

        for (Vertex<T> v : graph.vertices())
            matrix.set(v, v, sameVertexValue);

        for (Edge<T> e : graph.edges())
            matrix.set(e.getSrc(), e.getDest(), e.getWeight());

        return matrix;
    }

    public static <T> VertexMatrix<T, Integer> extendShortestPaths(VertexMatrix<T, Integer> nextStepAdjMatrix,
                                                                   VertexMatrix<T, Integer> initialAdjMatrix) {
        VertexMatrix<T, Integer> result = new VertexMatrix<>(nextStepAdjMatrix.getVertices(), Integer.MAX_VALUE);

        for (Vertex<T> i : nextStepAdjMatrix.getVertices()) {
            for (Vertex<T> j : nextStepAdjMatrix.getVertices()) {
                for (Vertex<T> k : initialAdjMatrix.getVertices()) {
                    Integer ik = nextStepAdjMatrix.get(i, k);
                    Integer kj = initialAdjMatrix.get(k, j);
                    if (!sumOverflow(ik, kj)) {
                        result.set(i, j, Math.min(result.get(i, j), ik + kj));
                    }
                }
            }
        }

        return result;
    }

    public static <T> List<Vertex<T>> getAllPairsShortestPath(VertexMatrix<T, Vertex<T>> predecessorsMatrix,
                                                              Vertex<T> source, Vertex<T> dest) {
        List<Vertex<T>> path = new LinkedList<>();
        Deque<Vertex<T>> stack = new ArrayDeque<>();

        while (true) {
            if (source.equals(dest)) {
                stack.add(source);
                break;
            }
            if (predecessorsMatrix.get(source, dest) == null)
                break;
            else {
                stack.push(dest);
                dest = predecessorsMatrix.get(source, dest);
            }
        }

        while (!stack.isEmpty())
            path.add(stack.pop());

        return path;
    }

    public static boolean sumOverflow(int x, int y) {
        long s = (long) x + (long) y;
        return s < Integer.MIN_VALUE || s > Integer.MAX_VALUE;
    }
}
