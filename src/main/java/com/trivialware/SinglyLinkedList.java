package com.trivialware;

import java.util.Iterator;

//Podia ser uma classe abstract e ter duas classes herdeiras, uma que implementasse UnorderedListADT e outra OrderedListADT. Neste caso esta classe implementaria ListADT
public class SinglyLinkedList<T> implements UnorderedListADT<T> {
    private int size;
    private final SinglyLinkedNode<T> head;
    private final SinglyLinkedNode<T> tail;

    public SinglyLinkedList() {
        this.size = 0;
        this.head = new SinglyLinkedNode<>();
        this.tail = new SinglyLinkedNode<>();
        this.head.setNext(this.tail);
    }

    protected void ensureValidPosition(int index) {
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
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(T e) {
        SinglyLinkedNode<T> ln = head.getNext();
        while (ln != tail) {
            if (ln.getData().equals(e)) {
                return true;
            }
            ln = ln.getNext();
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
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

        //Comparable é um tipo de Object
        @SuppressWarnings({"unchecked"})
        T[] arr = (T[]) new Object[(to - from) + 1];
        int pos = 0;
        int counter = 0;
        SinglyLinkedNode<T> ln = head.getNext();
        while (ln != tail) {
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
        ensureValidPosition(index);
        int counter = 0;
        SinglyLinkedNode<T> ln = head.getNext();
        while (ln != tail) {
            if (counter == index) {
                ln.setData(e);
                return true;
            }
            counter += 1;
            ln = ln.getNext();
        }
        return false;
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
        if (index == 0) {
            addFirst(e);
        }
        else {
            int counter = 0;
            SinglyLinkedNode<T> ln = getHead();
            while (ln.getNext() != getTail() && counter != index) {
                ln = ln.getNext();
                counter += 1;
            }
            SinglyLinkedNode<T> toAdd = new SinglyLinkedNode<>(e);
            toAdd.setNext(ln.getNext());
            ln.setNext(toAdd);
            setSize(getSize() + 1);
        }
    }

    @Override
    public void addFirst(T e) {
        SinglyLinkedNode<T> first = getHead().getNext();
        SinglyLinkedNode<T> toAdd = new SinglyLinkedNode<>(e);
        toAdd.setNext(first);
        getHead().setNext(toAdd);
        setSize(getSize() + 1);
    }

    @Override
    public void addLast(T e) {
        SinglyLinkedNode<T> toAdd = new SinglyLinkedNode<>(e);
        toAdd.setNext(getTail());
        SinglyLinkedNode<T> ln = getHead();
        while (ln.getNext() != getTail()) {
            ln = ln.getNext();
        }
        ln.setNext(toAdd);
        setSize(getSize() + 1);
    }

    @Override
    public boolean remove(T e) {
        SinglyLinkedNode<T> ln = head;
        while (ln.getNext() != tail && !ln.getNext().getData().equals(e)) {
            ln = ln.getNext();
        }
        if (ln.getNext() == tail) {
            return false;
        }
        //T elementToRemove = ln.getNext().getData();
        SinglyLinkedNode<T> linkedNodeAfterToRemove = ln.getNext().getNext();
        ln.setNext(linkedNodeAfterToRemove);
        size -= 1;
        return true;
    }

    @Override
    public T remove(int index) {
        ensureValidPosition(index);
        int counter = 0;
        SinglyLinkedNode<T> ln = head;
        while (ln.getNext() != tail && counter != index) {
            ln = ln.getNext();
            counter += 1;
        }
        T elementToRemove = ln.getNext().getData();
        SinglyLinkedNode<T> linkedNodeAfterToRemove = ln.getNext().getNext();
        ln.setNext(linkedNodeAfterToRemove);
        size -= 1;
        return elementToRemove;
    }

    @Override
    public T removeFirst() {
        return remove(0);
    }

    @Override
    public T removeLast() {
        return remove(size - 1);
    }

    @Override
    public boolean clear() {
        head.setNext(tail);
        size = 0;
        return true;
    }

    @Override
    public T getFirst() {
        return size() == 0 ? null : getHead().getNext().getData();
    }

    @Override
    public T getLast() {
        if (size() == 0) {
            return null;
        }
        SinglyLinkedNode<T> ln = head;
        while (ln.getNext() != tail) {
            ln = ln.getNext();
        }
        return ln.getData();
    }

    @Override
    public T get(int index) {
        ensureValidPosition(index);
        int counter = 0;
        SinglyLinkedNode<T> ln = head;
        while (ln.getNext() != tail && counter != index) {
            ln = ln.getNext();
            counter += 1;
        }
        return ln.getNext().getData();
    }

    protected SinglyLinkedNode<T> getHead() {
        return head;
    }

    protected SinglyLinkedNode<T> getTail() {
        return tail;
    }

    private class LinkedListIterator implements Iterator<T> {
        private SinglyLinkedNode<T> current;

        public LinkedListIterator() {
            this.current = getHead().getNext();
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
    }

    protected static class SinglyLinkedNode<T> {
        private T data;
        private SinglyLinkedNode<T> next;

        public SinglyLinkedNode(T data) {
            this.data = data;
            this.next = null;
        }

        public SinglyLinkedNode() {
            this.data = null;
            this.next = null;
        }

        public SinglyLinkedNode(T data, SinglyLinkedNode<T> next) {
            this.data = data;
            this.next = next;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public SinglyLinkedNode<T> getNext() {
            return next;
        }

        public void setNext(SinglyLinkedNode<T> next) {
            this.next = next;
        }
    }
}
