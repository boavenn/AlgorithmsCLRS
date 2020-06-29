package _35_ApproximationAlgorithms;

import _22_ElementaryGraphAlgorithms.Graph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static _22_ElementaryGraphAlgorithms.Util.createVertex;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class VertexCoverTest
{
    @Test
    void returnCorrectOutput() {
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

        assertIterableEquals(List.of(createVertex('a'), createVertex('b'), createVertex('c'),
                createVertex('e'), createVertex('d'), createVertex('f')), VertexCover.process(graph));
    }
}