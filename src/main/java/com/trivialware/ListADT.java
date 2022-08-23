package com.trivialware;

public interface ListADT<T> extends Iterable<T> {
    int size();

    boolean isEmpty();

    boolean contains(T e);

    //Iterator<T> iterator();

    T[] toArray();

    T[] toArrayUntil(int pos);

    T[] toArrayAfter(int pos);

    T[] toArrayBetween(int from, int to);

    //primeiro
    T remove(int index);

    boolean remove(T e);

    T removeFirst();

    T removeLast();

    boolean clear();

    T get(int index);

    T getFirst();

    T getLast();
}
