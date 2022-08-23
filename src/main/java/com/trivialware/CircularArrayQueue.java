package com.trivialware;

import java.util.Iterator;

public class CircularArrayQueue<T> implements QueueADT<T> {
    private int DEFAULT_CAPACITY = 10;
    private T[] items;
    private int numberOfElements;
    private int headIndex;
    private int tailIndex;

    public CircularArrayQueue() {
        clear();
    }

    public CircularArrayQueue(int defaultCapacity) {
        this.DEFAULT_CAPACITY = defaultCapacity;
        clear();
    }

    private void resizeArray(int newCapacity) {
        if (newCapacity < size()) {
            return;
        }
        @SuppressWarnings({"unchecked"})
        T[] newArray = (T[]) new Object[newCapacity];
        if (numberOfElements > 0) {
            /*
            if (headIndex > tailIndex) {
                for (int i = tailIndex; i <= headIndex; i++) {
                    newArray[i] = items[i];
                }
            }
            else {
                for (int i = tailIndex; i < items.length; i++) {
                    newArray[i - tailIndex] = items[i];
                }
                for (int i = 0; i < headIndex; i++) {
                    newArray[items.length - tailIndex] = items[i];
                }
                this.headIndex = 0;
                this.tailIndex = items.length - (tailIndex - headIndex);
            }
             */
            for (int i = 0; i < size(); i++) {
                newArray[i] = items[this.headIndex];
                this.headIndex = (this.headIndex + 1) % this.items.length;
            }
        }
        this.headIndex = 0;
        this.tailIndex = size();
        this.items = newArray;
    }

    @Override
    public boolean enqueue(T e) {
        if (size() == items.length) {
            resizeArray(items.length * 2);
        }
        items[tailIndex] = e;
        /*
        tailIndex += 1;
        if (tailIndex == items.length) {
            tailIndex = 0;
        }
         */
        this.tailIndex = (tailIndex + 1) % items.length;
        numberOfElements += 1;
        return true;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T firstElement = items[headIndex];
        /*
        headIndex += 1;
        if (headIndex == items.length) {
            headIndex = 0;
        }
         */
        this.headIndex = (this.headIndex + 1) % items.length;
        numberOfElements -= 1;
        return firstElement;
    }

    @Override
    public T first() {
        return isEmpty() ? null : items[headIndex];
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return numberOfElements;
    }

    @Override
    public boolean clear() {
        this.numberOfElements = 0;
        this.headIndex = 0;
        this.tailIndex = 0;
        resizeArray(DEFAULT_CAPACITY);
        /*
        @SuppressWarnings({"unchecked"})
        T[] newArray = (T[]) new Comparable[DEFAULT_CAPACITY];
        this.items = newArray;
         */
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new CircularArrayQueueIterator();
    }

    private class CircularArrayQueueIterator implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return !isEmpty();
        }

        @Override
        public T next() {
            return dequeue();
        }
    }
}
