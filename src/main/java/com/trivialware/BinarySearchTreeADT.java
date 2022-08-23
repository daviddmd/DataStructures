package com.trivialware;

public interface BinarySearchTreeADT<T extends Comparable<T>> extends BinaryTreeADT<T> {

    void insert(T element);
    void remove(T element);
    T findMin();
    T findMax();
    T removeMin();
    T removeMax();
}
