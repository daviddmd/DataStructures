package com.trivialware;

public class ArrayBinarySearchTree<T extends Comparable<T>> extends ArrayBinaryTree<T> implements BinarySearchTreeADT<T> {
    @Override
    public void insert(T element) {
    }

    @Override
    public void remove(T element) {
    }

    @Override
    public T findMin() {
        int minIndex = 0;
        T[] tree = getTree();
        if (!isEmpty()) {
            while (tree[minIndex] != null && (minIndex * 2 + 1) < tree.length) {
                minIndex = (minIndex * 2) + 1;
            }
            return tree[minIndex];
        }
        return null;
    }

    @Override
    public T findMax() {
        int maxIndex = 0;
        T[] tree = getTree();
        if (!isEmpty()) {
            while (tree[maxIndex] != null && ((maxIndex + 1) * 2) < tree.length) {
                maxIndex = (maxIndex + 1) * 2;
            }
            return tree[maxIndex];
        }
        return null;
    }

    @Override
    public T removeMin() {
        if (!isEmpty()) {
            T min = findMin();
            remove(min);
            return min;
        }
        return null;
    }

    @Override
    public T removeMax() {
        if (!isEmpty()) {
            T max = findMax();
            remove(max);
            return max;
        }
        return null;
    }
}
