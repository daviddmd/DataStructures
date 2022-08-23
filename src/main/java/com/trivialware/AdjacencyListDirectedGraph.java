package com.trivialware;

import java.util.Iterator;

public class AdjacencyListDirectedGraph<T> implements DirectedGraphADT<T> {
    private final UnorderedListADT<AdjacencyListVertex<T>> vertices;
    private int numberOfEdges;

    public AdjacencyListDirectedGraph() {
        this.vertices = new DoublyLinkedList<>();
        this.numberOfEdges = 0;
    }

    public AdjacencyListVertex<T> getVertex(T vertex) {
        for (AdjacencyListVertex<T> v : vertices) {
            if (v.getLabel().equals(vertex)) {
                return v;
            }
        }
        return null;
    }

    @Override
    public ListADT<T> getInNeighbours(T vertex) {
        UnorderedListADT<T> neighbours = new DoublyLinkedList<>();
        AdjacencyListVertex<T> v = getVertex(vertex);
        if (v == null) {
            return neighbours;
        }
        for (AdjacencyListVertex<T> alv : vertices) {
            if (!alv.equals(v) && alv.getEdgeOfVertex(v) != null) {
                neighbours.addLast(alv.getLabel());
            }
        }
        return neighbours;
    }

    @Override
    public ListADT<T> getOutNeighbours(T vertex) {
        UnorderedListADT<T> neighbours = new DoublyLinkedList<>();
        AdjacencyListVertex<T> v = getVertex(vertex);
        if (v == null) {
            return neighbours;
        }
        for (AdjacencyListVertex.Edge<T> edge : v.getEdges()) {
            neighbours.addLast(edge.getVertex().getLabel());
        }
        return neighbours;
    }

    @Override
    public boolean addVertex(T vertex) {
        if (getVertex(vertex) == null) {
            vertices.addLast(new AdjacencyListVertex<>(vertex));
            return true;
        }
        return false;
    }

    @Override
    public boolean addEdge(T originVertex, T destinationVertex) {
        AdjacencyListVertex<T> origin = getVertex(originVertex);
        AdjacencyListVertex<T> destination = getVertex(destinationVertex);
        if (origin == null || destination == null || origin.equals(destination)) {
            return false;
        }
        AdjacencyListVertex.Edge<T> edge = origin.getEdgeOfVertex(destination);
        if (edge != null) {
            return false;
        }
        origin.getEdges().addLast(new AdjacencyListVertex.Edge<>(destination, 1));
        numberOfEdges += 1;
        return true;
    }

    @Override
    public boolean removeVertex(T vertex) {
        AdjacencyListVertex<T> v = getVertex(vertex);
        if (v == null) {
            return false;
        }
        vertices.remove(v);
        numberOfEdges -= v.getEdges().size();
        AdjacencyListVertex.Edge<T> vertexIterEdge;
        for (AdjacencyListVertex<T> vertexIter : vertices) {
            vertexIterEdge = vertexIter.getEdgeOfVertex(v);
            if (vertexIterEdge != null) {
                vertexIter.getEdges().remove(vertexIterEdge);
                numberOfEdges -= 1;
            }
        }
        return true;
    }

    @Override
    public boolean removeEdge(T originVertex, T destinationVertex) {
        AdjacencyListVertex<T> origin = getVertex(originVertex);
        AdjacencyListVertex<T> destination = getVertex(destinationVertex);
        if (origin == null || destination == null || origin.equals(destination)) {
            return false;
        }
        AdjacencyListVertex.Edge<T> edge = origin.getEdgeOfVertex(destination);
        if (edge != null) {
            origin.getEdges().remove(edge);
            numberOfEdges -= 1;
            return true;
        }
        return false;
    }

    @Override
    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    @Override
    public int getNumberOfVertices() {
        return vertices.size();
    }

    @Override
    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    @Override
    public boolean isConnected() {
        if (isEmpty()) {
            return false;
        }
        //DFS a começar no primeiro elemento
        QueueADT<T> dfs = getDepthFirstTraversal(vertices.getFirst().getLabel());
        return dfs.size() == getNumberOfVertices();
    }

    @Override
    public void clear() {
        vertices.clear();
        numberOfEdges = 0;
    }

    @Override
    public void clearEdges() {
        for (AdjacencyListVertex<T> v : vertices) {
            v.clearEdges();
        }
    }

    @Override
    public QueueADT<T> getBreadthFirstTraversal(T originVertex) {
        QueueADT<T> traversalOrder = new LinkedQueue<>();
        AdjacencyListVertex<T> vertex = getVertex(originVertex);
        if (vertex == null) {
            return traversalOrder;
        }
        QueueADT<AdjacencyListVertex<T>> vertexQueue = new LinkedQueue<>();
        UnorderedListADT<AdjacencyListVertex<T>> visitedVertices = new DoublyLinkedList<>();
        visitedVertices.addLast(vertex);
        traversalOrder.enqueue(vertex.getLabel());
        vertexQueue.enqueue(vertex);
        AdjacencyListVertex<T> frontVertex;
        while (!vertexQueue.isEmpty()) {
            frontVertex = vertexQueue.dequeue();
            for (AdjacencyListVertex.Edge<T> edge : frontVertex.getEdges()) {
                if (!visitedVertices.contains(edge.getVertex())) {
                    visitedVertices.addLast(edge.getVertex());
                    traversalOrder.enqueue(edge.getVertex().getLabel());
                    vertexQueue.enqueue(edge.getVertex());
                }
            }
        }
        return traversalOrder;
    }

    @Override
    public QueueADT<T> getDepthFirstTraversal(T originVertex) {
        QueueADT<T> traversalOrder = new LinkedQueue<>();
        AdjacencyListVertex<T> vertex = getVertex(originVertex);
        if (vertex == null) {
            return traversalOrder;
        }
        StackADT<AdjacencyListVertex<T>> vertexStack = new ArrayStack<>(getNumberOfVertices());
        UnorderedListADT<AdjacencyListVertex<T>> visitedVertices = new DoublyLinkedList<>();
        visitedVertices.addLast(vertex);
        traversalOrder.enqueue(originVertex);
        vertexStack.push(vertex);
        AdjacencyListVertex<T> topVertex;
        boolean allNeighboursVisited;
        while (!vertexStack.empty()) {
            topVertex = vertexStack.peek();
            allNeighboursVisited = true;
            for (AdjacencyListVertex.Edge<T> edge : topVertex.getEdges()) {
                if (!visitedVertices.contains(edge.getVertex())) {
                    allNeighboursVisited = false;
                    visitedVertices.addLast(edge.getVertex());
                    traversalOrder.enqueue(edge.getVertex().getLabel());
                    vertexStack.push(edge.getVertex());
                    break;
                }
            }
            if (allNeighboursVisited) {
                vertexStack.pop();
            }
        }
        return traversalOrder;
    }

    @Override
    public Iterator<T> breadthFirstTraversalIterator(T originVertex) {
        return getBreadthFirstTraversal(originVertex).iterator();
    }

    @Override
    public Iterator<T> depthFirstTraversalIterator(T originVertex) {
        return getDepthFirstTraversal(originVertex).iterator();
    }

    @Override
    public Iterator<T> getShortestPathIterator(T originVertex, T destinationVertex) {
        StackADT<T> stack = new LinkedStack<>();
        getShortestPath(originVertex, destinationVertex, stack);
        return stack.iterator();
    }

    @Override
    public int getShortestPath(T originVertex, T destinationVertex, StackADT<T> path) {
        boolean done = false;
        AdjacencyListVertex<T> origin = getVertex(originVertex);
        AdjacencyListVertex<T> destination = getVertex(destinationVertex);
        if (origin == null || destination == null || origin.equals(destination)) {
            return -1;
        }
        initializeVerticesForPathAlgorithms();
        origin.setVisited(true);
        origin.setPathCost(0);
        QueueADT<AdjacencyListVertex<T>> vertexQueue = new LinkedQueue<>();
        vertexQueue.enqueue(origin);
        AdjacencyListVertex<T> frontVertex, neighbour;
        while (!done && !vertexQueue.isEmpty()) {
            frontVertex = vertexQueue.dequeue();
            for (AdjacencyListVertex.Edge<T> edge : frontVertex.getEdges()) {
                neighbour = edge.getVertex();
                if (!neighbour.isVisited()) {
                    neighbour.setVisited(true);
                    neighbour.setPathCost(1 + frontVertex.getPathCost());
                    neighbour.setPredecessor(frontVertex);
                    vertexQueue.enqueue(neighbour);
                }
                if (neighbour.equals(destination)) {
                    done = true;
                }
            }
        }
        path.push(destination.getLabel());
        AdjacencyListVertex<T> currentVertex = destination;
        while (currentVertex.getPredecessor() != null) {
            currentVertex = currentVertex.getPredecessor();
            path.push(currentVertex.getLabel());
        }
        return (int) destination.getPathCost();
    }

    protected void initializeVerticesForPathAlgorithms() {
        for (AdjacencyListVertex<T> v : vertices) {
            v.setVisited(false);
            v.setPredecessor(null);
            v.setPathCost(Double.MAX_VALUE);
        }
    }

    protected ListADT<AdjacencyListVertex<T>> getVertices() {
        return vertices;
    }

    public void setNumberOfEdges(int numberOfEdges) {
        this.numberOfEdges = numberOfEdges;
    }

}
