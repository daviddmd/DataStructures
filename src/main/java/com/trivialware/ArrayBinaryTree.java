package com.trivialware;

import java.util.Iterator;

public abstract class ArrayBinaryTree<T> implements BinaryTreeADT<T> {
    private int numberElements;
    private T[] tree;
    private int DEFAULT_CAPACITY = 50;

    public ArrayBinaryTree() {
        @SuppressWarnings({"unchecked"})
        T[] tmp = (T[]) new Object[DEFAULT_CAPACITY];
        this.tree = tmp;
        this.numberElements = 0;
    }

    public ArrayBinaryTree(T element) {
        @SuppressWarnings({"unchecked"})
        T[] tmp = (T[]) new Object[DEFAULT_CAPACITY];
        this.tree = tmp;
        this.tree[0] = element;
        this.numberElements = 1;
    }

    public ArrayBinaryTree(int defaultCapacity) {
        this.DEFAULT_CAPACITY = defaultCapacity;
        @SuppressWarnings({"unchecked"})
        T[] tmp = (T[]) new Object[DEFAULT_CAPACITY];
        this.tree = tmp;
        this.numberElements = 0;
    }

    public ArrayBinaryTree(int defaultCapacity, T element) {
        this.DEFAULT_CAPACITY = defaultCapacity;
        @SuppressWarnings({"unchecked"})
        T[] tmp = (T[]) new Object[DEFAULT_CAPACITY];
        this.tree = tmp;
        this.tree[0] = element;
        this.numberElements = 1;
    }

    @Override
    public boolean isEmpty() {
        return numberElements == 0;
    }

    @Override
    public int size() {
        return numberElements;
    }

    @Override
    public int height() {
        return 0;
    }

    @Override
    public void makeEmpty() {
        @SuppressWarnings({"unchecked"})
        T[] tmp = (T[]) new Object[DEFAULT_CAPACITY];
        this.tree = tmp;
        this.numberElements = 0;
    }

    @Override
    public boolean contains(T element) {
        for (int i = 0; i < numberElements; i++) {
            if (element.equals(tree[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        UnorderedListADT<T> list = new DoublyLinkedList<>();
        iteratePreOrder(0, list);
        return list.iterator();
    }

    @Override
    public Iterator<T> iteratorInOrder() {
        UnorderedListADT<T> list = new DoublyLinkedList<>();
        iterateInOrder(0, list);
        return list.iterator();
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        UnorderedListADT<T> list = new DoublyLinkedList<>();
        iteratePostOrder(0, list);
        return list.iterator();
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        UnorderedListADT<T> list = new DoublyLinkedList<>();
        iterateLevelOrder(list);
        return list.iterator();

    }

    private void iteratePreOrder(int nodeIndex, UnorderedListADT<T> list) {
        if (nodeIndex < this.tree.length) {
            if (this.tree[nodeIndex] != null) {
                list.addLast(this.tree[nodeIndex]);
                iteratePreOrder(((nodeIndex * 2) + 1), list);
                iteratePreOrder(((nodeIndex + 1) * 2), list);
            }
        }
    }

    private void iterateInOrder(int nodeIndex, UnorderedListADT<T> list) {
        if (nodeIndex < this.tree.length) {
            if (this.tree[nodeIndex] != null) {
                iterateInOrder(((nodeIndex * 2) + 1), list);
                list.addLast(this.tree[nodeIndex]);
                iterateInOrder(((nodeIndex + 1) * 2), list);
            }
        }
    }

    private void iteratePostOrder(int nodeIndex, UnorderedListADT<T> list) {
        if (nodeIndex < this.tree.length) {
            if (this.tree[nodeIndex] != null) {
                iteratePostOrder(((nodeIndex * 2) + 1), list);
                iteratePostOrder(((nodeIndex + 1) * 2), list);
                list.addLast(this.tree[nodeIndex]);
            }
        }
    }

    private void iterateLevelOrder(UnorderedListADT<T> list) {
        for (int i = 0; i < tree.length; i++) {
            if (tree[i] != null) {
                list.addLast(tree[i]);
            }
        }
    }

    @Override
    public T getRootElement() {
        if (!isEmpty()) {
            return tree[0];
        }
        return null;
    }

    protected void expandTree() {
        @SuppressWarnings({"unchecked"})
        T[] tmp = (T[]) new Object[this.tree.length * 2];
        for (int i = 0; i < this.tree.length; i++) {
            tmp[i] = tree[i];
        }
        this.tree = tmp;
    }

    protected T[] getTree() {
        return tree;
    }

}
