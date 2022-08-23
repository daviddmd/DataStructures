package com.trivialware;

import javax.swing.*;
import java.util.Iterator;

public class ArrayStack<T> implements StackADT<T> {
    private int DEFAULT_CAPACITY = 20;
    private T[] stack;
    private int numberElements;

    public ArrayStack() {
        this.numberElements = 0;
        resizeArray(DEFAULT_CAPACITY);
    }

    public ArrayStack(int defaultNumberElements) {
        this.DEFAULT_CAPACITY = defaultNumberElements;
        this.numberElements = 0;
        resizeArray(DEFAULT_CAPACITY);

    }

    private void resizeArray(int newCapacity) {
        if (newCapacity < size()) {
            return;
        }
        @SuppressWarnings({"unchecked"})
        T[] newArray = (T[]) new Object[newCapacity];
        for (int i = 0; i < size(); i++) {
            newArray[i] = stack[i];
        }
        this.stack = newArray;
    }

    @Override
    public boolean empty() {
        return size() == 0;
    }

    @Override
    public T peek() {
        if (numberElements == 0) {
            return null;
        }
        return stack[numberElements - 1];
    }

    @Override
    public T pop() {
        if (numberElements != 0) {
            T element = peek();
            numberElements -= 1;
            return element;
        }
        return null;
    }

    @Override
    public T push(T e) {
        if (numberElements == stack.length) {
            resizeArray(stack.length * 2);
        }
        stack[numberElements] = e;
        numberElements += 1;
        return e;
    }

    @Override
    public int size() {
        return numberElements;
    }

    @Override
    public boolean clear() {
        this.numberElements = 0;
        resizeArray(DEFAULT_CAPACITY);
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayStackIterator();
    }

    private class ArrayStackIterator implements Iterator<T> {
        @Override
        public boolean hasNext() {
            return !empty();
        }

        @Override
        public T next() {
            return pop();
        }
    }
    /*
    UnorderedListADT<T> list;

    public ArrayStack() {
        this.list = new ArrayList<>();
    }

    @Override
    public boolean empty() {
        return list.isEmpty();
    }

    @Override
    public T peek() {
        return list.getLast();
    }

    @Override
    public T pop() {
        return list.removeLast();
    }

    @Override
    public T push(T e) {
        //Como está no fim, o tempo é O(1) e não O(N). Mesmo com remoção
        list.addLast(e);
        return e;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean clear() {
        return list.clear();
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayStackIterator();
    }

    private class ArrayStackIterator implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return size() == 0;
        }

        @Override
        public T next() {
            return pop();
        }
    }
     */
}
