package com.trivialware;

public interface HeapADT<T extends Comparable<? super T>> extends BinaryTreeADT<T> {
    void insert(T element);

    T deleteMinimum();

    T findMinimum();

}
