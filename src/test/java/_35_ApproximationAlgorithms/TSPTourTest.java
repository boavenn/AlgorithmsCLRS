package _35_ApproximationAlgorithms;

import _22_ElementaryGraphAlgorithms.Graph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static _22_ElementaryGraphAlgorithms.Util.createVertex;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class TSPTourTest
{
    @Test
    void returnCorrectOutput() {
        Graph<Integer> graph = new Graph<>(false);
        Integer[] V = {1, 2, 3, 4};
        Integer[][] E = {
                {1, 2, 10}, {1, 4, 20}, {1, 3, 15},
                {2, 4, 25}, {4, 3, 30}, {2, 3, 35}
        };

        for (Integer i : V)
            graph.addVertex(i);
        for (Integer[] i : E)
            graph.addEdge(i[0], i[1], i[2]);

        assertIterableEquals(List.of(createVertex(1), createVertex(2), createVertex(3),
                createVertex(4), createVertex(1)), TSPTour.find(graph, createVertex(1)));
    }
}