package com.trivialware;

public interface QueueADT<T> extends Iterable<T> {
    boolean enqueue(T e);
    T dequeue();
    T first();
    boolean isEmpty();
    int size();
    boolean clear();
}
