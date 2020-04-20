package _24_SingleSourceShortestPaths;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public abstract class Util
{
    public static <T> Map<Vertex<T>, Integer> initializeSource(List<Vertex<T>> vertices, Vertex<T> src) {
        Map<Vertex<T>, Integer> temp = new HashMap<>();
        for (Vertex<T> v : vertices)
            temp.put(v, Integer.MAX_VALUE);
        temp.replace(src, 0);
        return temp;
    }

    public static <T> boolean relax(Map<Vertex<T>, Integer> map, Edge<T> edge) {
        Vertex<T> u = edge.getSrc();
        Vertex<T> v = edge.getDest();
        if (map.get(u) != Integer.MAX_VALUE && map.get(v) > map.get(u) + edge.getWeight()) {
            map.replace(v, map.get(u) + edge.getWeight());
            return true;
        }
        return false;
    }

    public static <T> List<Vertex<T>> getShortestPath(Map<Vertex<T>, Vertex<T>> paths, Vertex<T> src, Vertex<T> i) {
        List<Vertex<T>> path = new LinkedList<>();
        Stack<Vertex<T>> stack = new Stack<>();
        Vertex<T> v = i;

        while (!v.equals(src)) {
            stack.push(v);
            v = paths.get(v);
        }
        path.add(src);

        while (!stack.isEmpty())
            path.add(stack.pop());

        return path;
    }
}
