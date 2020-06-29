package _26_MaximumFlow;

import _22_ElementaryGraphAlgorithms.Graph;
import org.junit.jupiter.api.Test;

import static _22_ElementaryGraphAlgorithms.Util.createVertex;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PushRelabelToFrontTest
{
    @Test
    void returnCorrectOutputForSmallGraph() {
        Graph<String> graph = new Graph<>(true);
        String[] V = {"s", "v1", "v2", "v3", "v4", "t"};
        String[][] E = {
                {"s", "v1"}, {"v1", "v3"}, {"v3", "t"},
                {"s", "v2"}, {"v2", "v4"}, {"v4", "t"},
                {"v2", "v1"}, {"v3", "v2"}, {"v4", "v3"},
        };
        Integer[] W = {16, 12, 20, 13, 14, 4, 4, 9, 7};

        for (String s : V)
            graph.addVertex(s);
        for (int i = 0; i < E.length; i++)
            graph.addEdge(E[i][0], E[i][1], W[i]);

        assertEquals(23, PushRelabelToFront.process(graph, createVertex("s"), createVertex("t")));
    }

    @Test
    void returnCorrectOutputForMediumGraph() {
        Graph<Integer> graph = new Graph<>(true);
        Integer[] V1 = {0, 1, 2, 3, 4, 5, 6, 7};
        Integer[][] E1 = {
                {3, 5}, {3, 4}, {4, 5}, {2, 3}, {1, 4}, {1, 3}, {0, 2}, {0, 1},
                {1, 2}, {4, 7}, {5, 7}, {3, 7}, {6, 7}, {3, 6}, {0, 6}, {4, 2}
        };
        Integer[] W1 = {8, 20, 1, 26, 13, 10, 1, 38, 8, 7, 7, 1, 27, 24, 2, 2};

        for (Integer i : V1)
            graph.addVertex(i);
        for (int i = 0; i < E1.length; i++)
            graph.addEdge(E1[i][0], E1[i][1], W1[i]);

        assertEquals(31, PushRelabelToFront.process(graph, createVertex(0), createVertex(7)));
    }
}