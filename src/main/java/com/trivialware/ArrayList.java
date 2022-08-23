package com.trivialware;

import java.util.Iterator;

public class ArrayList<T> implements UnorderedListADT<T> {
    private int DEFAULT_CAPACITY = 20;
    private int numberItems;
    private T[] items;

    public ArrayList() {
        this.numberItems = 0;
        resizeArray(DEFAULT_CAPACITY);
    }

    public ArrayList(int defaultCapacity) {
        this.DEFAULT_CAPACITY = defaultCapacity;
        this.numberItems = 0;
        resizeArray(DEFAULT_CAPACITY);
    }

    private void ensureValidPosition(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException(String.format("Invalid Position: %d. Size of List: %d.", index, size()));
        }
    }

    private void resizeArray(int newCapacity) {
        if (newCapacity < size()) {
            return;
        }
        @SuppressWarnings({"unchecked"})
        T[] newArray = (T[]) new Object[newCapacity];
        for (int i = 0; i < size(); i++) {
            newArray[i] = items[i];
        }
        this.items = newArray;
    }

    @Override
    public int size() {
        return numberItems;
    }

    @Override
    public boolean isEmpty() {
        return numberItems == 0;
    }

    @Override
    public boolean contains(T e) {
        for (int i = 0; i < size(); i++) {
            if (items[i].equals(e)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public T[] toArray() {
        return toArrayBetween(0, size() - 1);
    }

    @Override
    public T[] toArrayUntil(int pos) {
        return toArrayBetween(0, pos);
    }

    @Override
    public T[] toArrayAfter(int pos) {
        return toArrayBetween(pos, size() - 1);
    }

    @Override
    public T[] toArrayBetween(int from, int to) {
        ensureValidPosition(from);
        ensureValidPosition(to);
        if (from > to) {
            throw new IndexOutOfBoundsException(String.format("Invalid From and To Positions: From: %d Can't be Higher than To: %d. Size of List: %d.", from, to, size()));
        }
        @SuppressWarnings({"unchecked"})
        T[] arr = (T[]) new Object[(to - from) + 1];
        for (int i = from; i <= to; i++) {
            arr[i - from] = items[i];
        }
        return arr;
    }

    @Override
    public boolean set(int index, T e) {
        ensureValidPosition(index);
        items[index] = e;
        return true;
    }
    /*
    @Override
    public void add(T e) {
        addLast(e);
    }
     */

    @Override
    public void add(int index, T e) {
        ensureValidPosition(index);
        if (items.length == numberItems) {
            resizeArray(numberItems * 2 + 1);
        }
        for (int i = size(); i > index; i--) {
            items[i] = items[i - 1];
        }
        items[index] = e;
        numberItems += 1;
    }

    @Override
    public void addFirst(T e) {
        add(0, e);

    }

    @Override
    public void addLast(T e) {
        add(size(), e);
    }

    @Override
    public T remove(int index) {
        ensureValidPosition(index);
        T data = items[index];
        for (int i = index; i < size(); i++) {
            items[i] = items[i + 1];
        }
        this.numberItems -= 1;
        return data;
    }

    @Override
    public boolean remove(T e) {
        int itemIndex = getIndex(e);
        if (itemIndex == -1) {
            return false;
        }
        remove(itemIndex);
        return true;
    }

    @Override
    public T removeFirst() {
        return remove(0);
    }

    @Override
    public T removeLast() {
        return remove(size() - 1);
    }

    @Override
    public boolean clear() {
        this.numberItems = 0;
        resizeArray(DEFAULT_CAPACITY);
        return true;
    }

    public int getIndex(T data) {
        for (int i = 0; i < size(); i++) {
            if (items[i].equals(data)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public T get(int index) {
        ensureValidPosition(index);
        return items[index];
    }

    @Override
    public T getFirst() {
        if (size() == 0) {
            return null;
        }
        return items[0];
    }

    @Override
    public T getLast() {
        if (size() == 0) {
            return null;
        }
        return items[size() - 1];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<T> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size();
        }

        @Override
        public T next() {
            T data = items[currentIndex];
            currentIndex += 1;
            return data;
        }

        @Override
        public void remove() {
            currentIndex -= 1;
            ArrayList.this.remove(currentIndex);
        }
    }
}
