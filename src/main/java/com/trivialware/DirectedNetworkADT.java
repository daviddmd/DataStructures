package com.trivialware;

import java.util.Iterator;

public interface DirectedNetworkADT<T> extends GraphADT<T> {
    boolean addEdge(T originVertex, T destinationVertex, double weight);

    void setEdgeWeight(T originVertex, T destinationVertex, double weight);

    double getEdgeWeight(T originVertex, T destinationVertex);

    Iterator<T> getCheapestPathIterator(T originVertex, T destinationVertex);

    /*
    Como é uma stack, e no final do caminho mais barato se itera desde o último para o primeiro, usa-se uma stack
    para o primeiro elemento a ser removido (pop()) seja o início do caminho, e o último o destino
    (se aplicável)
     */
    double getCheapestPath(T originVertex, T destinationVertex, StackADT<T> path);

}
