package _25_AllPairsShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;
import _24_SingleSourceShortestPaths.BellmanFord;
import _24_SingleSourceShortestPaths.Dijkstra;

import java.util.Map;

import static _22_ElementaryGraphAlgorithms.Graph.Edge;
import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _25_AllPairsShortestPaths.Util.VertexMatrix;

public final class Johnson
{
    public static <T> VertexMatrix<T, Integer> process(Graph<T> graph) {
        Graph<T> graphCopy = copyGraph(graph);
        Vertex<T> newNode = new Vertex<>(null);

        graphCopy.addVertex(newNode);
        for (Vertex<T> v : graph.vertices())
            graphCopy.addEdge(newNode.getKey(), v.getKey(), 0);

        Map<Vertex<T>, Integer> bellmanDistances = BellmanFord.distances(graphCopy, newNode);
        for (Edge<T> e : graphCopy.edges())
            e.setWeight(e.getWeight() + bellmanDistances.get(e.getSrc()) - bellmanDistances.get(e.getDest()));

        VertexMatrix<T, Integer> distance = new VertexMatrix<>(graph.vertices(), 0);
        for (Vertex<T> u : graph.vertices()) {
            Map<Vertex<T>, Integer> dijkstraDistances = Dijkstra.distances(graphCopy, u);
            for (Vertex<T> v : graph.vertices())
                distance.set(u, v, dijkstraDistances.get(v) + bellmanDistances.get(v) - bellmanDistances.get(u));
        }

        return distance;
    }

    private static <T> Graph<T> copyGraph(Graph<T> graph) {
        Graph<T> temp = new Graph<>(graph.isDirected());
        for (Vertex<T> v : graph.vertices())
            temp.addVertex(v.getKey());
        for (Edge<T> e : graph.edges())
            temp.addEdge(e.getSrc().getKey(), e.getDest().getKey(), e.getWeight());
        return temp;
    }
}
