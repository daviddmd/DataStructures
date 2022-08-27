package com.trivialware;

public interface UndirectedNetworkADT<T> extends DirectedNetworkADT<T>, UndirectedGraphADT<T> {
    DirectedNetworkADT<T> minimumSpanningTreePrim();

    DirectedNetworkADT<T> minimumSpanningTreePrim(T startingVertex);

}
