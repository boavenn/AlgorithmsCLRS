package _22_ElementaryGraphAlgorithms;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public class Util
{
    public static <T> Vertex<T> createVertex(T key) {
        return new Vertex<>(key);
    }

    public static <T> Edge<T> createEdge(T a, T b, Integer weight) {
        return new Edge<>(createVertex(a), createVertex(b), weight);
    }

    public static <T> Edge<T> createEdge(T a, T b) {
        return createEdge(a, b, null);
    }
}
