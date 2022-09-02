package com.trivialware;

import com.trivialware.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyMatrixDirectedGraphTest {
    DirectedGraphADT<Integer> graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyMatrixDirectedGraph<>(5);
    }

    void addVertices(DirectedGraphADT<Integer> graph) {
        for (int i = 1; i <= 5; i++) {
            graph.addVertex(i);
        }
    }

    void addEdges(DirectedGraphADT<Integer> graph) {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 1);
        graph.addEdge(2, 4);
        graph.addEdge(2, 5);
    }

    @Test
    void addVertex() {
        assertTrue(graph.isEmpty());
        for (int i = 1; i <= 10; i++) {
            assertTrue(graph.addVertex(i));
        }
        assertEquals(10, graph.getNumberOfVertices());
        assertEquals(0, graph.getNumberOfEdges());
        for (int i = 1; i <= 10; i++) {
            assertFalse(graph.addVertex(i));
        }
        assertEquals(10, graph.getNumberOfVertices());
        assertEquals(0, graph.getNumberOfEdges());
        //Forçar duas expansões
        for (int i = 20; i < 65; i++) {
            assertTrue(graph.addVertex(i));
        }
        assertEquals(55, graph.getNumberOfVertices());
        assertEquals(0, graph.getNumberOfEdges());
        assertFalse(graph.isEmpty());
        graph.clear();
        assertTrue(graph.isEmpty());
        assertEquals(0, graph.getNumberOfVertices());
        addVertices(graph);
        assertFalse(graph.isEmpty());
        assertEquals(5, graph.getNumberOfVertices());
    }

    @Test
    void addEdge() {
        addVertices(graph);
        assertFalse(graph.isEmpty());
        assertTrue(graph.addEdge(1, 2));
        assertTrue(graph.addEdge(2, 1));
        assertFalse(graph.addEdge(2, 1));
        assertFalse(graph.addEdge(2, 2));
        assertFalse(graph.addEdge(0, 2));
        assertTrue(graph.addEdge(2, 3));
        assertFalse(graph.addEdge(2, 3));
    }

    @Test
    void removeVertex() {
        ListADT<Integer> inNeighbours;
        ListADT<Integer> outNeighbours;
        addVertices(graph);
        addEdges(graph);
        assertFalse(graph.removeVertex(6));
        assertFalse(graph.removeVertex(0));
        assertFalse(graph.removeVertex(7));
        inNeighbours = graph.getInNeighbours(5);
        outNeighbours = graph.getOutNeighbours(5);
        assertTrue(inNeighbours.contains(2));
        assertTrue(inNeighbours.contains(4));
        assertEquals(2, inNeighbours.size());
        assertTrue(outNeighbours.contains(1));
        assertEquals(1, outNeighbours.size());
        inNeighbours = graph.getInNeighbours(2);
        outNeighbours = graph.getOutNeighbours(2);
        assertEquals(1, inNeighbours.size());
        assertEquals(3, outNeighbours.size());
        assertTrue(inNeighbours.contains(1));
        assertTrue(outNeighbours.contains(3));
        assertTrue(outNeighbours.contains(4));
        assertTrue(outNeighbours.contains(5));
        for (int i = 10; i <= 20; i++) {
            graph.addVertex(i);
        }
        assertTrue(graph.removeVertex(1));
        assertFalse(graph.removeVertex(1));
        for (int i = 21; i <= 50; i++) {
            graph.addVertex(i);
        }
        inNeighbours = graph.getInNeighbours(5);
        outNeighbours = graph.getOutNeighbours(5);
        assertEquals(2, inNeighbours.size());
        assertTrue(inNeighbours.contains(2));
        assertTrue(inNeighbours.contains(4));
        assertEquals(0, outNeighbours.size());
        inNeighbours = graph.getInNeighbours(2);
        outNeighbours = graph.getOutNeighbours(2);
        assertEquals(0, inNeighbours.size());
        assertEquals(3, outNeighbours.size());
        assertTrue(outNeighbours.contains(3));
        assertTrue(outNeighbours.contains(4));
        assertTrue(outNeighbours.contains(5));
        inNeighbours = graph.getInNeighbours(3);
        outNeighbours = graph.getOutNeighbours(3);
        assertTrue(inNeighbours.contains(2));
        assertTrue(outNeighbours.contains(4));
        assertEquals(1, inNeighbours.size());
        assertEquals(1, outNeighbours.size());
        assertTrue(graph.removeVertex(2));
        assertFalse(graph.removeVertex(2));
        for (int i = 60; i <= 160; i++) {
            graph.addVertex(i);
        }
        inNeighbours = graph.getInNeighbours(5);
        outNeighbours = graph.getOutNeighbours(5);
        assertEquals(1, inNeighbours.size());
        assertTrue(inNeighbours.contains(4));
        assertEquals(0, outNeighbours.size());
        inNeighbours = graph.getInNeighbours(3);
        outNeighbours = graph.getOutNeighbours(3);
        assertEquals(0, inNeighbours.size());
        assertEquals(1, outNeighbours.size());
        assertTrue(outNeighbours.contains(4));
        inNeighbours = graph.getInNeighbours(4);
        outNeighbours = graph.getOutNeighbours(4);
        assertEquals(1, inNeighbours.size());
        assertEquals(1, outNeighbours.size());
        assertTrue(inNeighbours.contains(3));
        assertTrue(outNeighbours.contains(5));
    }

    @Test
    void removeEdge() {
        ListADT<Integer> inNeighbours;
        ListADT<Integer> outNeighbours;
        addVertices(graph);
        assertEquals(0, graph.getNumberOfEdges());
        addEdges(graph);
        assertEquals(7, graph.getNumberOfEdges());
        graph.removeVertex(2);
        assertEquals(3, graph.getNumberOfEdges());
        graph.removeVertex(4);
        assertEquals(1, graph.getNumberOfEdges());
        graph.clear();
        addVertices(graph);
        addEdges(graph);
        assertEquals(7, graph.getNumberOfEdges());
        assertTrue(graph.removeEdge(1, 2));
        assertFalse(graph.removeEdge(2, 1));
        assertFalse(graph.removeEdge(1, 2));
        assertEquals(6, graph.getNumberOfEdges());
        inNeighbours = graph.getInNeighbours(4);
        outNeighbours = graph.getOutNeighbours(4);
        assertTrue(inNeighbours.contains(2));
        assertTrue(inNeighbours.contains(3));
        assertEquals(2, inNeighbours.size());
        assertEquals(1, outNeighbours.size());
        assertTrue(outNeighbours.contains(5));
        assertTrue(graph.removeEdge(2, 4));
        inNeighbours = graph.getInNeighbours(4);
        assertFalse(inNeighbours.contains(2));
        assertTrue(inNeighbours.contains(3));
    }

    @Test
    void getNeighbours() {
        addVertices(graph);
        addEdges(graph);
        ListADT<Integer> inNeighbours;
        ListADT<Integer> outNeighbours;
        inNeighbours = graph.getInNeighbours(4);
        outNeighbours = graph.getOutNeighbours(4);
        assertEquals(2, inNeighbours.size());
        assertEquals(1, outNeighbours.size());
        assertTrue(inNeighbours.contains(3));
        assertTrue(inNeighbours.contains(2));
        assertTrue(outNeighbours.contains(5));
        assertTrue(graph.addVertex(6));
        assertTrue(graph.addEdge(4, 6));
        assertTrue(graph.addEdge(6, 4));
        inNeighbours = graph.getInNeighbours(4);
        outNeighbours = graph.getOutNeighbours(4);
        assertEquals(3, inNeighbours.size());
        assertEquals(2, outNeighbours.size());
        assertTrue(inNeighbours.contains(3));
        assertTrue(inNeighbours.contains(2));
        assertTrue(inNeighbours.contains(6));
        assertTrue(outNeighbours.contains(5));
        assertTrue(outNeighbours.contains(6));
    }

    @Test
    void getNumberOfEdges() {
        assertEquals(0, graph.getNumberOfEdges());
        addVertices(graph);
        addEdges(graph);
        assertEquals(7, graph.getNumberOfEdges());
        assertTrue(graph.addEdge(1, 5));
        assertEquals(8, graph.getNumberOfEdges());
        assertTrue(graph.removeEdge(2, 5));
        assertEquals(7, graph.getNumberOfEdges());
        assertTrue(graph.removeVertex(2));
        assertEquals(4, graph.getNumberOfEdges());
        assertTrue(graph.addEdge(1, 3));
        assertEquals(5, graph.getNumberOfEdges());
    }

    @Test
    void getNumberOfVertices() {
        assertEquals(0, graph.getNumberOfVertices());
        addVertices(graph);
        assertEquals(5, graph.getNumberOfVertices());
        assertTrue(graph.addVertex(6));
        assertFalse(graph.addVertex(4));
        assertEquals(6, graph.getNumberOfVertices());
        assertTrue(graph.removeVertex(1));
        assertTrue(graph.removeVertex(6));
        assertTrue(graph.removeVertex(3));
        assertEquals(3, graph.getNumberOfVertices());
    }

    @Test
    void isEmpty() {
        assertTrue(graph.isEmpty());
        addVertices(graph);
        addEdges(graph);
        assertFalse(graph.isEmpty());
        graph.clear();
        assertTrue(graph.isEmpty());
    }

    @Test
    void isConnected() {
        addVertices(graph);
        addEdges(graph);
        assertTrue(graph.isConnected());
        assertTrue(graph.removeVertex(2));
        assertFalse(graph.isConnected());
        assertTrue(graph.addEdge(1, 3));
        assertTrue(graph.isConnected());

    }

    @Test
    void getBreadthFirstTraversal() {
        int[] expectedOrder;
        int currentIndex;
        QueueADT<Integer> traversal;
        addVertices(graph);
        assertTrue(graph.addVertex(6));
        assertTrue(graph.addVertex(7));
        assertTrue(graph.addEdge(1, 2));
        assertTrue(graph.addEdge(2, 3));
        assertTrue(graph.addEdge(3, 4));
        assertTrue(graph.addEdge(4, 6));
        assertTrue(graph.addEdge(3, 5));
        assertTrue(graph.addEdge(4, 5));
        assertTrue(graph.addEdge(5, 7));
        assertTrue(graph.addEdge(5, 1));
        traversal = graph.getBreadthFirstTraversal(1);
        expectedOrder = new int[]{1, 2, 3, 4, 5, 6, 7};
        currentIndex = 0;
        while (!traversal.isEmpty()) {
            assertEquals(expectedOrder[currentIndex++], traversal.dequeue());
        }
        assertTrue(graph.removeEdge(3, 5));
        assertTrue(graph.addEdge(2, 5));
        traversal = graph.getBreadthFirstTraversal(1);
        expectedOrder = new int[]{1, 2, 3, 5, 4, 7, 6};
        currentIndex = 0;
        while (!traversal.isEmpty()) {
            assertEquals(expectedOrder[currentIndex++], traversal.dequeue());
        }
    }

    @Test
    void getDepthFirstTraversal() {
        int[] expectedOrder;
        int currentIndex;
        QueueADT<Integer> traversal;
        addVertices(graph);
        assertTrue(graph.addVertex(6));
        assertTrue(graph.addVertex(7));
        graph.addEdge(1, 2);
        graph.addEdge(1, 6);
        graph.addEdge(2, 3);
        graph.addEdge(2, 7);
        graph.addEdge(6, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 3);
        graph.addEdge(7, 4);
        graph.addEdge(6, 7);
        traversal = graph.getDepthFirstTraversal(1);
        expectedOrder = new int[]{1, 2, 3, 7, 4, 5, 6};
        currentIndex = 0;
        while (!traversal.isEmpty()) {
            assertEquals(expectedOrder[currentIndex++], traversal.dequeue());
        }
    }

    @Test
    void getShortestPath() {
        int[] expectedOrder;
        int currentIndex;
        QueueADT<Integer> traversal;
        StackADT<Integer> orderStack;
        int shortestPath;
        addVertices(graph);
        assertTrue(graph.addVertex(6));
        assertTrue(graph.addVertex(7));
        assertTrue(graph.addEdge(1, 2));
        assertTrue(graph.addEdge(2, 3));
        assertTrue(graph.addEdge(3, 5));
        assertTrue(graph.addEdge(5, 4));
        assertTrue(graph.addEdge(2, 7));
        assertTrue(graph.addEdge(7, 4));
        assertTrue(graph.addEdge(1, 6));
        assertTrue(graph.addEdge(6, 4));
        orderStack = new LinkedStack<>();
        shortestPath = graph.getShortestPath(1, 4, orderStack);
        assertEquals(2, shortestPath);
        currentIndex = 0;
        expectedOrder = new int[]{1,6,4};
        while (!orderStack.empty()) {
            assertEquals(expectedOrder[currentIndex++], orderStack.pop());
        }
        assertTrue(graph.addEdge(4, 5));
        assertTrue(graph.removeEdge(1, 6));
        orderStack = new LinkedStack<>();
        shortestPath = graph.getShortestPath(1, 5, orderStack);
        assertEquals(3, shortestPath);
        expectedOrder = new int[]{1,2,3,5};
        currentIndex = 0;
        while (!orderStack.empty()) {
            assertEquals(expectedOrder[currentIndex++], orderStack.pop());
        }
    }
}