package _25_AllPairsShortestPaths;

import _22_ElementaryGraphAlgorithms.Graph;
import org.junit.jupiter.api.Test;

import static _22_ElementaryGraphAlgorithms.Graph.Vertex;
import static _25_AllPairsShortestPaths.Util.VertexMatrix;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransitiveClosureTest
{
    @Test
    void returnCorrectOutput() {
        Graph<Integer> graph = new Graph<>(true);
        Integer[] V = {1, 2, 3, 4};
        Integer[][] E = {
                {1, 1}, {2, 2}, {2, 3}, {2, 4}, {3, 2},
                {3, 3}, {4, 1}, {4, 3}, {4, 4},
        };

        for (Integer i : V)
            graph.addVertex(i);
        for (Integer[] integers : E)
            graph.addEdge(integers[0], integers[1]);

        VertexMatrix<Integer, Integer> closure = TransitiveClosure.process(graph);
        Integer[][] expected = {
                {1, 0, 0, 0},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1}
        };

        for (Vertex<Integer> v : closure.getVertices()) {
            for (Vertex<Integer> u : closure.getVertices()) {
                assertEquals(expected[v.getKey() - 1][u.getKey() - 1], closure.get(v, u));
            }
        }
    }
}