package com.trivialware;

import java.util.Iterator;

public interface GraphADT<T> {
    boolean addVertex(T vertex);

    boolean addEdge(T originVertex, T destinationVertex);

    boolean removeVertex(T vertex);

    boolean removeEdge(T originVertex, T destinationVertex);

    int getNumberOfEdges();

    int getNumberOfVertices();

    boolean isEmpty();

    boolean isConnected();

    void clear();

    void clearEdges();

    QueueADT<T> getBreadthFirstTraversal(T originVertex);

    QueueADT<T> getDepthFirstTraversal(T originVertex);

    Iterator<T> breadthFirstTraversalIterator(T originVertex);

    Iterator<T> depthFirstTraversalIterator(T originVertex);

    Iterator<T> getShortestPathIterator(T originVertex, T destinationVertex);

    int getShortestPath(T originVertex, T destinationVertex, StackADT<T> path);

}
