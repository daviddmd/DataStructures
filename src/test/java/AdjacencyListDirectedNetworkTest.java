import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.trivialware.*;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyListDirectedNetworkTest {
    NetworkADT<Integer> network;

    @BeforeEach
    void setUp() {
        network = new AdjacencyListDirectedNetwork<>();
    }

    void addVertices(NetworkADT<Integer> network) {
        for (int i = 1; i <= 6; i++) {
            network.addVertex(i);
        }
    }

    void addEdges(NetworkADT<Integer> network) {
        network.addEdge(1, 2, 2);
        network.addEdge(1, 3, 4);
        network.addEdge(2, 3, 1);
        network.addEdge(2, 4, 7);
        network.addEdge(3, 5, 3);
        network.addEdge(5, 4, 2);
        network.addEdge(5, 6, 5);
        network.addEdge(4, 6, 1);
    }

    @Test
    void addEdge() {
        addVertices(network);
        assertFalse(network.isEmpty());
        assertTrue(network.addEdge(1, 2, 2));
        assertEquals(2, network.getEdgeWeight(1, 2));
        assertFalse(network.addEdge(1, 2, 3));
        assertEquals(2, network.getEdgeWeight(1, 2));
        assertFalse(network.addEdge(-1, 16, 5));
        assertFalse(network.addEdge(16, -1, 5));
        assertFalse(network.addEdge(3, 3, -5));
        assertFalse(network.addEdge(3, 3, 0));
        assertEquals(1, network.getNumberOfEdges());
    }

    @Test
    void setEdgeWeight() {
        addVertices(network);
        addEdges(network);
        assertEquals(4, network.getEdgeWeight(1, 3));
        network.setEdgeWeight(1, 3, 5);
        assertEquals(5, network.getEdgeWeight(1, 3));
        network.setEdgeWeight(1, 3, -5);
        assertEquals(5, network.getEdgeWeight(1, 3));
    }

    @Test
    void getCheapestPath() {
        addVertices(network);
        addEdges(network);
        StackADT<Integer> order;
        int[] expectedOrder;
        int currentIndex;
        order = new LinkedStack<>();
        currentIndex = 0;
        expectedOrder = new int[]{1, 2, 3, 5, 4, 6};
        assertEquals(9, network.getCheapestPath(1, 6, order));
        while (!order.empty()) {
            assertEquals(expectedOrder[currentIndex++], order.pop());
        }
        assertTrue(network.removeEdge(2, 3));
        network.setEdgeWeight(5, 4, 3);
        order = new LinkedStack<>();
        currentIndex = 0;
        expectedOrder = new int[]{1, 2, 4, 6};
        assertEquals(10, network.getCheapestPath(1, 6, order));
        while (!order.empty()) {
            assertEquals(expectedOrder[currentIndex++], order.pop());
        }
    }

    @Test
    void getCheapestPath1() {
        addVertices(network);
        assertTrue(network.addEdge(1, 6, 2));
        assertTrue(network.addEdge(1, 5, 20));
        assertTrue(network.addEdge(1, 2, 5));
        assertTrue(network.addEdge(2, 3, 2));
        assertTrue(network.addEdge(3, 4, 1));
        assertTrue(network.addEdge(4, 5, 10));
        int[] expectedOrder = new int[]{1, 2, 3, 4, 5};
        StackADT<Integer> order = new LinkedStack<>();
        int currentIndex = 0;
        assertEquals(18, network.getCheapestPath(1, 5, order));
        while (!order.empty()) {
            assertEquals(expectedOrder[currentIndex++], order.pop());
        }
    }
}