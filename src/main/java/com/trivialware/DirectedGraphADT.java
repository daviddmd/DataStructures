package com.trivialware;

import java.util.Iterator;

public interface DirectedGraphADT<T> extends GraphADT<T> {
    ListADT<T> getInNeighbours(T vertex);

    ListADT<T> getOutNeighbours(T vertex);

    QueueADT<T> getTopologicalOrder();

    Iterator<T> getTopologicalOrderIterator();
}
