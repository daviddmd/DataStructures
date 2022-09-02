package com.trivialware;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.trivialware.*;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyMatrixUndirectedNetworkTest {
    UndirectedNetworkADT<Integer> network;

    @BeforeEach
    void setUp() {
        network = new AdjacencyMatrixUndirectedNetwork<>();
    }

    void addVertices(UndirectedNetworkADT<Integer> network) {
        for (int i = 1; i <= 6; i++) {
            network.addVertex(i);
        }
    }

    void addEdges(UndirectedNetworkADT<Integer> network) {
        network.addEdge(1, 2, 2);
        network.addEdge(1, 3, 4);
        network.addEdge(2, 3, 1);
        network.addEdge(2, 4, 7);
        network.addEdge(3, 5, 3);
        network.addEdge(5, 4, 2);
        network.addEdge(5, 6, 5);
        network.addEdge(4, 6, 1);
    }

    void addEdges1(UndirectedNetworkADT<Integer> network) {
        network.addEdge(1, 2, 7);
        network.addEdge(1, 6, 14);
        network.addEdge(1, 3, 9);
        network.addEdge(2, 3, 10);
        network.addEdge(2, 4, 15);
        network.addEdge(4, 3, 11);
        network.addEdge(4, 5, 6);
        network.addEdge(5, 6, 9);
        network.addEdge(6, 3, 2);
    }

    @Test
    void addEdge() {
        addVertices(network);
        assertFalse(network.isEmpty());
        assertTrue(network.addEdge(1, 2, 2));
        assertEquals(2, network.getEdgeWeight(1, 2));
        assertEquals(2, network.getEdgeWeight(2, 1));
        assertFalse(network.addEdge(1, 2, 3));
        assertFalse(network.addEdge(2, 1, 3));
        assertEquals(1, network.getNumberOfEdges());
        assertFalse(network.addEdge(-1, 16, 5));
        assertFalse(network.addEdge(16, -1, 5));
        assertFalse(network.addEdge(3, 3, -5));
        assertFalse(network.addEdge(3, 3, 0));
        assertTrue(network.addEdge(1, 3, 3));
        assertFalse(network.addEdge(3, 1, 5));
        assertEquals(3, network.getEdgeWeight(1, 3));
        assertEquals(3, network.getEdgeWeight(3, 1));
        assertEquals(2, network.getNumberOfEdges());
    }

    @Test
    void setEdgeWeight() {
        addVertices(network);
        addEdges(network);
        assertEquals(4, network.getEdgeWeight(1, 3));
        assertEquals(4, network.getEdgeWeight(3, 1));
        network.setEdgeWeight(1, 3, 5);
        assertEquals(5, network.getEdgeWeight(1, 3));
        assertEquals(5, network.getEdgeWeight(3, 1));
        network.setEdgeWeight(1, 3, -5);
        assertEquals(5, network.getEdgeWeight(1, 3));
    }

    @Test
    void removeEdge() {
        addVertices(network);
        assertEquals(0, network.getNumberOfEdges());
        addEdges(network);
        assertEquals(8, network.getNumberOfEdges());
        network.removeVertex(2);
        assertEquals(5, network.getNumberOfEdges());
    }

    @Test
    void getNeighbours() {
        addVertices(network);
        addEdges(network);
        ListADT<Integer> neighbours;
        neighbours = network.getNeighbours(2);
        int[] expectedNeighbours;
        expectedNeighbours = new int[]{1, 3, 4};
        for (int n : expectedNeighbours) {
            assertTrue(neighbours.contains(n));
        }
        assertEquals(3, neighbours.size());
        assertTrue(network.addEdge(2, 5, 2));
        expectedNeighbours = new int[]{1, 3, 4, 5};
        neighbours = network.getNeighbours(2);
        for (int n : expectedNeighbours) {
            assertTrue(neighbours.contains(n));
        }
    }

    @Test
    void minimumSpanningTreePrim() {
        addVertices(network);
        addEdges(network);
        int[] expectedBFSOrder = new int[]{1, 2, 3, 5, 4, 6};
        DirectedNetworkADT<Integer> mst = network.minimumSpanningTreePrim(1);
        QueueADT<Integer> bfs = mst.getBreadthFirstTraversal(1);
        int currentIndex = 0;
        while (!bfs.isEmpty()) {
            assertEquals(expectedBFSOrder[currentIndex++], bfs.dequeue());
        }
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
        currentIndex = 0;
        expectedOrder = new int[]{6, 4, 5, 3, 2, 1};
        order = new LinkedStack<>();
        assertEquals(9, network.getCheapestPath(6, 1, order));
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
        network.clearEdges();
        addEdges1(network);
        currentIndex = 0;
        expectedOrder = new int[]{1, 3, 6, 5};
        order = new LinkedStack<>();
        assertEquals(20, network.getCheapestPath(1, 5, order));
        while (!order.empty()) {
            assertEquals(expectedOrder[currentIndex++], order.pop());
        }

        currentIndex = 0;
        expectedOrder = new int[]{4, 3, 6};
        order = new LinkedStack<>();
        assertEquals(13, network.getCheapestPath(4, 6, order));
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