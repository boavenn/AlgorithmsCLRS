package _24_SingleSourceShortestPaths;

import java.util.*;
import java.util.stream.Collectors;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public final class Util
{
    public static <T> Map<Vertex<T>, Integer> initializeSource(List<Vertex<T>> vertices, Vertex<T> src) {
        Map<Vertex<T>, Integer> temp = vertices.stream()
                .collect(Collectors.toMap(v -> v, v -> Integer.MAX_VALUE));
        temp.replace(src, 0);
        return temp;
    }

    public static <T> boolean relaxEdge(Map<Vertex<T>, Integer> map, Edge<T> edge) {
        Vertex<T> src = edge.getSrc();
        Vertex<T> dest = edge.getDest();
        if (map.get(src) != Integer.MAX_VALUE && map.get(dest) > map.get(src) + edge.getWeight()) {
            map.replace(dest, map.get(src) + edge.getWeight());
            return true;
        }
        return false;
    }

    public static <T> List<Vertex<T>> shortestPath(Map<Vertex<T>, Vertex<T>> predecessorMap, Vertex<T> source, Vertex<T> dest) {
        List<Vertex<T>> path = new LinkedList<>();
        while (!dest.equals(source)) {
            path.add(dest);
            dest = predecessorMap.get(dest);
        }
        path.add(dest);
        Collections.reverse(path);
        return path;
    }
}
