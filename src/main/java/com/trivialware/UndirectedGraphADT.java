package com.trivialware;

public interface UndirectedGraphADT<T> extends GraphADT<T> {
    ListADT<T> getNeighbours(T vertex);
}
