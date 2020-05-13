package _25_AllPairsShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public final class Util<T>
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

    public static <T> VertexMatrix<T, Integer> asMatrix(Graph<T> graph, Integer defaultValue, Integer sameVertexDefaultValue) {
        VertexMatrix<T, Integer> matrix = new VertexMatrix<>(graph.getVertices(), defaultValue);

        for (Vertex<T> v : graph.getVertices())
            matrix.set(v, v, sameVertexDefaultValue);

        for (Edge<T> e : graph.getEdges())
            matrix.set(e.getSrc(), e.getDest(), e.getWeight());

        return matrix;
    }

    public static <T> VertexMatrix<T, Integer> extendShortestPaths(VertexMatrix<T, Integer> L, VertexMatrix<T, Integer> W) {
        VertexMatrix<T, Integer> res = new VertexMatrix<>(L.getVertices(), Integer.MAX_VALUE);

        for (Vertex<T> i : L.getVertices()) {
            for (Vertex<T> j : L.getVertices()) {
                for (Vertex<T> k : W.getVertices()) {
                    Integer ik = L.get(i, k);
                    Integer kj = W.get(k, j);
                    if (!sumOverflow(ik, kj)) {
                        Integer value = Math.min(res.get(i, j), ik + kj);
                        res.set(i, j, value);
                    }
                }
            }
        }

        return res;
    }

    public static <T> List<Vertex<T>> getAllPairsShortestPath(VertexMatrix<T, Vertex<T>> paths, Vertex<T> i, Vertex<T> j) {
        List<Vertex<T>> path = new LinkedList<>();
        Stack<Vertex<T>> stack = new Stack<>();

        while (true) {
            if (i.equals(j)) {
                stack.add(i);
                break;
            }
            if (paths.get(i, j) == null)
                break;
            else {
                stack.push(j);
                j = paths.get(i, j);
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
