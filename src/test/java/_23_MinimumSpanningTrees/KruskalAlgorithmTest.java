package _23_MinimumSpanningTrees;

import _22_ElementaryGraphAlgorithms.Graph;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static _22_ElementaryGraphAlgorithms.Graph.*;

class KruskalAlgorithmTest
{
    @Test
    void returnCorrectOutput() {
        Graph<Character> graph = new Graph<>(false);

        Character[] V = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};
        Character[][] E = {
                {'a', 'b'}, {'a', 'h'}, {'b', 'h'}, {'h', 'i'}, {'h', 'g'},
                {'g', 'i'}, {'i', 'c'}, {'c', 'b'}, {'c', 'd'}, {'c', 'f'},
                {'f', 'g'}, {'f', 'd'}, {'d', 'e'}, {'e', 'f'},
        };
        Integer[] W = {4, 8, 11, 7, 1, 6, 2, 8, 7, 4, 2, 14, 9, 10};

        for (Character c : V)
            graph.addVertex(c);
        for (int i = 0; i < E.length; i++)
            graph.addEdge(E[i][0], E[i][1], W[i]);

        List<Character> mst = KruskalAlgorithm.findMST(graph).stream()
                .map(edge -> List.of(edge.getSrc(), edge.getDest()))
                .flatMap(Collection::stream)
                .map(Vertex::getKey)
                .collect(Collectors.toList());

        assertIterableEquals(List.of('g', 'h', 'c', 'i', 'f', 'g', 'a', 'b', 'c', 'f', 'c', 'd', 'a', 'h', 'd', 'e'), mst);
    }
}