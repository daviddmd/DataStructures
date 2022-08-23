package com.trivialware;

public interface StackADT<T> extends Iterable<T> {
    boolean empty();

    T peek();

    T pop();

    T push(T e);

    int size();

    boolean clear();

}
