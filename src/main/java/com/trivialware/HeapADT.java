package com.trivialware;

public interface HeapADT<T extends Comparable<T>> extends BinaryTreeADT<T> {
    public void insert(T element);

    public T deleteMinimum();

    public T findMinimum();

}
