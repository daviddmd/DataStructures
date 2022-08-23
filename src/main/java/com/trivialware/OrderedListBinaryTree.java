package com.trivialware;

import java.util.Iterator;

public class OrderedListBinaryTree<T extends Comparable<T>> implements OrderedListADT<T> {
    private final BinarySearchTreeADT<T> tree;

    public OrderedListBinaryTree() {
        this.tree = new LinkedBinarySearchTree<>();
    }

    private void ensureValidPosition(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(String.format("Invalid Position: %d. Size of List: %d.", index, size()));
        }
    }

    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    @Override
    public boolean contains(T e) {
        return tree.contains(e);
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
        T[] arr = (T[]) new Comparable[(to - from) + 1];
        Iterator<T> iterator = tree.iteratorInOrder();
        int currentIndex = 0;
        int currentPosition = 0;
        T currentElement;
        while (iterator.hasNext()) {
            currentElement = iterator.next();
            if (currentIndex >= from && currentIndex <= to) {
                arr[currentPosition] = currentElement;
                currentPosition += 1;
            }
            currentIndex += 1;
        }
        return arr;
    }

    @Override
    public T remove(int index) {
        if (!isEmpty()) {
            T element = get(index);
            if (remove(element)) {
                return element;
            }
        }
        return null;
    }

    @Override
    public boolean remove(T e) {
        if (!isEmpty() && tree.contains(e)) {
            tree.remove(e);
            return true;
        }
        return false;
    }

    @Override
    public T removeFirst() {
        if (!isEmpty()) {
            return tree.removeMin();
        }
        return null;
    }

    @Override
    public T removeLast() {
        if (!isEmpty()) {
            return tree.removeMax();
        }
        return null;
    }

    @Override
    public boolean clear() {
        tree.makeEmpty();
        return true;
    }

    @Override
    public T get(int index) {
        if (isEmpty()) {
            return null;
        }
        ensureValidPosition(index);
        return toArray()[index];
    }

    @Override
    public T getFirst() {
        return tree.findMin();
    }

    @Override
    public T getLast() {
        return tree.findMax();
    }

    @Override
    public void add(T e) {
        tree.insert(e);
    }

    @Override
    public Iterator<T> iterator() {
        return tree.iteratorInOrder();
    }
}
