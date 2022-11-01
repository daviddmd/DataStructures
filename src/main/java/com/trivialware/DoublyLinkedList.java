package com.trivialware;

import java.util.Iterator;

public class DoublyLinkedList<T> implements UnorderedListADT<T> {
    private int size;
    private final DoublyLinkedNode<T> head;
    private final DoublyLinkedNode<T> tail;

    public DoublyLinkedList() {
        this.size = 0;
        this.head = new DoublyLinkedNode<>();
        this.tail = new DoublyLinkedNode<>();
        this.head.setNext(this.tail);
        this.tail.setPrevious(this.head);
    }

    protected DoublyLinkedNode<T> getHead() {
        return head;
    }

    protected DoublyLinkedNode<T> getTail() {
        return tail;
    }

    private void ensureValidPosition(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(String.format("Invalid Position: %d. Size of List: %d.", index, size()));
        }
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(T e) {
        return getNode(e) != null;
    }

    @Override
    public T[] toArray() {
        return toArrayBetween(0, size - 1);
    }

    @Override
    public T[] toArrayUntil(int pos) {
        return toArrayBetween(0, pos);
    }

    @Override
    public T[] toArrayAfter(int pos) {
        return toArrayBetween(pos, size - 1);
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
        int pos = 0;
        int counter = 0;
        DoublyLinkedNode<T> ln = getHead().getNext();
        while (ln != getTail()) {
            if (counter >= from && counter <= to) {
                arr[pos] = ln.getData();
                pos += 1;
            }
            counter += 1;
            ln = ln.getNext();
        }
        return arr;
    }

    @Override
    public boolean set(int index, T e) {
        DoublyLinkedNode<T> node = getNode(index);
        node.setData(e);
        return true;
    }

    @Override
    public void add(int index, T e) {
        DoublyLinkedNode<T> nodeAtIndex = getNode(index);
        DoublyLinkedNode<T> nodeToAdd = new DoublyLinkedNode<>(e, nodeAtIndex.getPrevious(), nodeAtIndex);
        nodeAtIndex.getPrevious().setNext(nodeToAdd);
        nodeAtIndex.setPrevious(nodeToAdd);
        setSize(getSize() + 1);
    }

    @Override
    public void addFirst(T e) {
        DoublyLinkedNode<T> previousFirst = getHead().getNext();
        DoublyLinkedNode<T> nodeToAdd = new DoublyLinkedNode<>(e, getHead(), previousFirst);
        previousFirst.setPrevious(nodeToAdd);
        getHead().setNext(nodeToAdd);
        setSize(getSize() + 1);
    }

    @Override
    public void addLast(T e) {
        DoublyLinkedNode<T> previousLast = getTail().getPrevious();
        DoublyLinkedNode<T> nodeToAdd = new DoublyLinkedNode<>(e, previousLast, getTail());
        previousLast.setNext(nodeToAdd);
        getTail().setPrevious(nodeToAdd);
        setSize(getSize() + 1);
    }


    @Override
    public T remove(int index) {
        return removeNode(getNode(index));
    }

    @Override
    public boolean remove(T e) {
        DoublyLinkedNode<T> node = getNode(e);
        if (node == null) {
            return false;
        }
        removeNode(node);
        return true;
    }

    private T removeNode(DoublyLinkedNode<T> node) {
        node.getNext().setPrevious(node.getPrevious());
        node.getPrevious().setNext(node.getNext());
        this.size -= 1;
        return node.getData();
    }

    @Override
    public T removeFirst() {
        if (size() == 0) {
            return null;
        }
        T data = this.head.getNext().getData();
        DoublyLinkedNode<T> newNext = this.head.getNext().getNext();
        this.head.setNext(newNext);
        newNext.setPrevious(this.head);
        this.size -= 1;
        return data;
    }

    @Override
    public T removeLast() {
        if (size() == 0) {
            return null;
        }
        T data = this.tail.getPrevious().getData();
        DoublyLinkedNode<T> newPrevious = this.tail.getPrevious().getPrevious();
        this.tail.setPrevious(newPrevious);
        newPrevious.setNext(this.tail);
        this.size -= 1;
        return data;
    }

    @Override
    public boolean clear() {
        this.head.setNext(this.tail);
        this.tail.setPrevious(this.head);
        this.size = 0;
        return true;
    }

    protected DoublyLinkedNode<T> getNode(int index) {
        ensureValidPosition(index);
        DoublyLinkedNode<T> ln;
        if (index < size() / 2) {
            ln = this.head.getNext();
            for (int i = 0; i < index; i++) {
                ln = ln.getNext();
            }

        }
        else {
            ln = this.tail;
            for (int i = size(); i > index; i--) {
                ln = ln.getPrevious();
            }
        }
        return ln;
    }

    protected DoublyLinkedNode<T> getNode(T e) {
        DoublyLinkedNode<T> ln = this.head;
        //Condição para a tail, não se pode usar equals em null
        while (ln.getNext() != getTail() && !ln.getNext().getData().equals(e)) {
            ln = ln.getNext();
        }
        return ln.getNext() != getTail() ? ln.getNext() : null;
    }

    @Override
    public T get(int index) {
        return getNode(index).getData();
    }

    @Override
    public T getFirst() {
        return size() == 0 ? null : getHead().getNext().getData();
    }

    @Override
    public T getLast() {
        return size() == 0 ? null : getTail().getPrevious().getData();
    }

    @Override
    public Iterator<T> iterator() {
        return new DoublyLinkedListIterator();
    }

    private class DoublyLinkedListIterator implements Iterator<T> {
        private DoublyLinkedNode<T> current;

        public DoublyLinkedListIterator() {
            this.current = head.getNext();
        }

        @Override
        public boolean hasNext() {
            return current != getTail();
        }

        @Override
        public T next() {
            T currentData = current.getData();
            current = current.getNext();
            return currentData;
        }

        @Override
        public void remove() {
            removeNode(current.getPrevious());
        }
    }

    protected static class DoublyLinkedNode<T> {
        private T data;
        private DoublyLinkedNode<T> previous;
        private DoublyLinkedNode<T> next;

        public DoublyLinkedNode(T data) {
            this.data = data;
            this.previous = null;
            this.next = null;
        }

        public DoublyLinkedNode() {
            this.data = null;
            this.previous = null;
            this.next = null;
        }

        public DoublyLinkedNode(T data, DoublyLinkedNode<T> previous, DoublyLinkedNode<T> next) {
            this.data = data;
            this.previous = previous;
            this.next = next;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public DoublyLinkedNode<T> getPrevious() {
            return previous;
        }

        public void setPrevious(DoublyLinkedNode<T> previous) {
            this.previous = previous;
        }

        public DoublyLinkedNode<T> getNext() {
            return next;
        }

        public void setNext(DoublyLinkedNode<T> next) {
            this.next = next;
        }
    }
}
