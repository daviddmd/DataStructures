package com.trivialware;

public class OrderedSinglyLinkedList<T extends Comparable<T>> extends SinglyLinkedList<T> implements OrderedListADT<T> {
    @Override
    public void add(T e) {
        SinglyLinkedNode<T> newNode;
        if (isEmpty()) {
            newNode = new SinglyLinkedNode<>(e, getTail());
            getHead().setNext(newNode);
        }
        else {
            SinglyLinkedNode<T> currentNode = getHead();
            while (currentNode != getTail() && currentNode.getNext().getData() != null && e.compareTo(currentNode.getNext().getData()) >= 0) {
                currentNode = currentNode.getNext();
            }
            newNode = new SinglyLinkedNode<>(e,currentNode.getNext());
            currentNode.setNext(newNode);
        }
        setSize(getSize() + 1);
    }
}
