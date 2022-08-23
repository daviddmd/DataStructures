package com.trivialware;

public class OrderedDoublyLinkedList<T extends Comparable<T>> extends DoublyLinkedList<T> implements OrderedListADT<T> {
    @Override
    public void add(T e) {
        DoublyLinkedNode<T> newNode;
        if (isEmpty()) {
            newNode = new DoublyLinkedNode<>(e, getHead(), getTail());
            getHead().setNext(newNode);
            getTail().setPrevious(newNode);
        }
        else {
            DoublyLinkedNode<T> current = getHead().getNext();
            while (current != getTail() && e.compareTo(current.getData()) > 0) {
                current = current.getNext();
            }
            newNode = new DoublyLinkedNode<>(e, current.getPrevious(), current);
            current.getPrevious().setNext(newNode);
            current.setPrevious(newNode);
        }
        setSize(getSize() + 1);

    }
}
