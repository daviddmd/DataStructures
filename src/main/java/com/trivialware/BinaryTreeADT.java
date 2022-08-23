package com.trivialware;

import java.util.Iterator;

public interface BinaryTreeADT<T> {
    boolean isEmpty();

    int size();

    int height();

    void makeEmpty();

    boolean contains(T element);

    Iterator<T> iteratorPreOrder();

    Iterator<T> iteratorInOrder();

    Iterator<T> iteratorPostOrder();

    Iterator<T> iteratorLevelOrder();

    T getRootElement();

}
