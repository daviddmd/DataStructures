package com.trivialware;

public class AdjacencyListUndirectedGraph<T> extends AdjacencyListDirectedGraph<T> implements UndirectedGraphADT<T> {
    public AdjacencyListUndirectedGraph() {
    }

    @Override
    public ListADT<T> getNeighbours(T vertex) {
        return getOutNeighbours(vertex);
    }

    @Override
    public boolean addEdge(T originVertex, T destinationVertex) {
        return super.addEdge(originVertex, destinationVertex) && super.addEdge(destinationVertex, originVertex);
    }

    @Override
    public int getNumberOfEdges() {
        return super.getNumberOfEdges() / 2;
    }

    @Override
    public boolean removeEdge(T originVertex, T destinationVertex) {
        return super.removeEdge(originVertex, destinationVertex) && super.removeEdge(destinationVertex, originVertex);
    }
}
