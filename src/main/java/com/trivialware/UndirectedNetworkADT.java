package com.trivialware;

public interface UndirectedNetworkADT<T> extends NetworkADT<T>, UndirectedGraphADT<T> {
    NetworkADT<T> minimumSpanningTreePrim();

    NetworkADT<T> minimumSpanningTreePrim(T startingVertex);

}
