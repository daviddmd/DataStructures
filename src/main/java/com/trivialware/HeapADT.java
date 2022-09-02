package com.trivialware;

public interface HeapADT<T extends Comparable<? super T>> extends BinaryTreeADT<T> {
    public void insert(T element);

    public T deleteMinimum();

    public T findMinimum();

}
