package com.trivialware;

public class AdjacencyMatrixUndirectedGraph<T> extends AdjacencyMatrixDirectedGraph<T> implements UndirectedGraphADT<T> {
    public AdjacencyMatrixUndirectedGraph() {
    }

    public AdjacencyMatrixUndirectedGraph(int defaultNumberVertices) {
        super(defaultNumberVertices);
    }

    @Override
    public boolean addEdge(T originVertex, T destinationVertex) {
        return super.addEdge(originVertex, destinationVertex) && super.addEdge(destinationVertex, originVertex);
    }

    @Override
    public int getNumberOfEdges() {
        return super.getNumberOfEdges()/2;
    }

    @Override
    public boolean removeEdge(T originVertex, T destinationVertex) {
        return super.removeEdge(originVertex, destinationVertex) && super.removeEdge(destinationVertex, originVertex);
    }

    /*
    Não há distinção entre In-Neighbours e Out-Neighbours em Grafos Não-Dirigidos
     */
    @Override
    public ListADT<T> getNeighbours(T vertex) {
        return super.getInNeighbours(vertex);
    }
}
