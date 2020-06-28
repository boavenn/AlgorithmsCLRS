package _22_ElementaryGraphAlgorithms;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static _22_ElementaryGraphAlgorithms.Graph.*;

class TopologicalSortTest
{
    private <T> boolean isSortedTopologically(Graph<T> graph, List<Vertex<T>> sortedVertices) {
        for (int i = sortedVertices.size(); i > 0; i--) {
            Vertex<T> v = sortedVertices.get(i - 1);
            for (var itr = sortedVertices.listIterator(i); itr.hasPrevious();) {
                if (graph.contains(new Edge<>(v, itr.previous()))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    void returnCorrectOutput() {
        Graph<Integer> graph = new Graph<>(true);
        Integer[] V1 = {0, 1, 2, 3, 4, 5};
        Integer[][] E1 = {{5, 2}, {5, 0}, {4, 0}, {4, 1}, {3, 1}, {2, 3}};

        for (Integer i : V1)
            graph.addVertex(i);
        for (Integer[] i : E1)
            graph.addEdge(i[0], i[1]);

        assertTrue(isSortedTopologically(graph, TopologicalSort.sort(graph)));
    }
}