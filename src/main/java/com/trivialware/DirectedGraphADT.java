package com.trivialware;

public interface DirectedGraphADT<T> extends GraphADT<T> {
    ListADT<T> getInNeighbours(T vertex);

    ListADT<T> getOutNeighbours(T vertex);
}
