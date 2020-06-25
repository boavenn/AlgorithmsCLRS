package _24_SingleSourceShortestPaths;

import java.util.*;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public final class Util
{
    public static <T> Map<Vertex<T>, Integer> initializeSource(List<Vertex<T>> vertices, Vertex<T> src) {
        Map<Vertex<T>, Integer> temp = new HashMap<>();
        for (Vertex<T> v : vertices)
            temp.put(v, Integer.MAX_VALUE);
        temp.replace(src, 0);
        return temp;
    }

    public static <T> boolean relax(Map<Vertex<T>, Integer> map, Edge<T> edge) {
        Vertex<T> src = edge.getSrc();
        Vertex<T> dest = edge.getDest();
        if (map.get(src) != Integer.MAX_VALUE && map.get(dest) > map.get(src) + edge.getWeight()) {
            map.replace(dest, map.get(src) + edge.getWeight());
            return true;
        }
        return false;
    }

    public static <T> List<Vertex<T>> getShortestPath(Map<Vertex<T>, Vertex<T>> paths, Vertex<T> src, Vertex<T> dest) {
        List<Vertex<T>> path = new LinkedList<>();
        Stack<Vertex<T>> stack = new Stack<>();

        while (!dest.equals(src)) {
            stack.push(dest);
            dest = paths.get(dest);
        }
        path.add(src);

        while (!stack.isEmpty())
            path.add(stack.pop());

        return path;
    }
}
