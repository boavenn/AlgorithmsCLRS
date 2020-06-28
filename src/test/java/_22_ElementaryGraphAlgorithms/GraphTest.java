package _22_ElementaryGraphAlgorithms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static _22_ElementaryGraphAlgorithms.Util.*;

class GraphTest
{
    private Graph<Integer> init(boolean directed) {
        Graph<Integer> graph = new Graph<>(directed);
        for (Integer i : new Integer[]{1, 2, 3, 4}) {
            graph.addVertex(i);
        }
        return graph;
    }

    @Test
    void addsVerticesCorrectly() {
        Graph<Integer> graph = init(false);
        assertTrue(graph.contains(createVertex(1)));
        assertTrue(graph.contains(createVertex(2)));
        assertTrue(graph.contains(createVertex(3)));
        assertTrue(graph.contains(createVertex(4)));
    }

    @Test
    void directedGraphAddsEdgesCorrectly() {
        Graph<Integer> graph = init(true);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(3, 4);
        assertEquals(3, graph.edges().size());
        assertTrue(graph.contains(createEdge(1, 2)));
        assertTrue(graph.contains(createEdge(1, 3)));
        assertTrue(graph.contains(createEdge(3, 4)));
    }

    @Test
    void undirectedGraphAddsEdgesCorrectly() {
        Graph<Integer> graph = init(false);
        graph.addEdge(3, 4);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        assertEquals(6, graph.edges().size());
        assertTrue(graph.contains(createEdge(3, 4)));
        assertTrue(graph.contains(createEdge(4, 3)));
        assertTrue(graph.contains(createEdge(1, 2)));
        assertTrue(graph.contains(createEdge(2, 1)));
        assertTrue(graph.contains(createEdge(2, 3)));
        assertTrue(graph.contains(createEdge(3, 2)));
    }

    @Test
    void directedGraphRemovesEdgesCorrectly() {
        Graph<Integer> graph = init(true);
        graph.addEdge(3, 4);
        graph.addEdge(1, 3);
        graph.removeEdge(createVertex(3), createVertex(4));
        graph.removeEdge(createVertex(1), createVertex(3));
        assertEquals(0, graph.edges().size());
    }

    @Test
    void undirectedGraphRemovesEdgesCorrectly() {
        Graph<Integer> graph = init(false);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.removeEdge(createVertex(2), createVertex(1));
        assertEquals(2, graph.edges().size());
        graph.removeEdge(createVertex(3), createVertex(2));
        assertEquals(0, graph.edges().size());
    }

    @Test
    void removesVerticesCorrectly() {
        Graph<Integer> graph = init(false);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.removeVertex(createVertex(2));
        assertEquals(0, graph.edges().size());
        assertEquals(3, graph.vertices().size());
    }

    @Test
    void transposingGraphCorrectly() {
        Graph<Integer> graph = init(true);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);
        graph.addEdge(4, 1);

        graph = Graph.transpose(graph);
        assertEquals(createEdge(2, 1), graph.adjacentEdgesOf(createVertex(2)).get(0));
        assertEquals(createEdge(1, 4), graph.adjacentEdgesOf(createVertex(1)).get(0));
        assertEquals(createEdge(4, 3), graph.adjacentEdgesOf(createVertex(4)).get(0));
    }
}