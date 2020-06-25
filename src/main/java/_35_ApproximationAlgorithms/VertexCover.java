package _35_ApproximationAlgorithms;

import _22_ElementaryGraphAlgorithms.Graph;

import java.util.LinkedList;
import java.util.List;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _22_ElementaryGraphAlgorithms.Graph.Edge;

public final class VertexCover
{
    public static <T> List<Vertex<T>> approxVertexCover(Graph<T> graph) {
        List<Vertex<T>> res = new LinkedList<>();
        List<Edge<T>> util = new LinkedList<>(graph.getEdges());
        while(!util.isEmpty()) {
            Edge<T> edge = util.remove(0);
            res.add(edge.getSrc());
            res.add(edge.getDest());

            for (Edge<T> e : graph.getAdjacentEdgesOf(edge.getSrc()))
                util.removeIf(a -> a.equals(e) || (a.getSrc().equals(e.getDest()) && a.getDest().equals(e.getSrc())));
            for (Edge<T> e : graph.getAdjacentEdgesOf(edge.getDest()))
                util.removeIf(a -> a.equals(e) || (a.getSrc().equals(e.getDest()) && a.getDest().equals(e.getSrc())));
        }
        return res;
    }

    private static class Example
    {
        public static void main(String[] args) {
            Graph<Character> graph = new Graph<>(false);
            Character[] V = {'a', 'b', 'c', 'd', 'e', 'f', 'g'};
            Character[][] E = {
                    {'a', 'b'}, {'b', 'c'}, {'c', 'e'}, {'c', 'd'},
                    {'e', 'd'}, {'e', 'f'}, {'f', 'd'}, {'d', 'g'}
            };

            for (Character c : V)
                graph.addVertex(c);
            for (Character[] c : E)
                graph.addEdge(c[0], c[1]);

            System.out.println(approxVertexCover(graph));
        }
    }
}
