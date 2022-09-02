package com.trivialware;

public interface UnorderedListADT<T> extends ListADT<T> {
    void add(int index, T e);

    void addFirst(T e);

    void addLast(T e);

    boolean set(int index, T e);

}
