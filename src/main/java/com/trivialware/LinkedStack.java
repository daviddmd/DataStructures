package com.trivialware;

import java.util.Iterator;

public class LinkedStack<T> implements StackADT<T> {
    private final UnorderedListADT<T> list;

    public LinkedStack() {
        this.list = new DoublyLinkedList<>();
    }

    @Override
    public boolean empty() {
        return size() == 0;
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
        /*
        se fosse uma singly linked list em que não tivesse acesso ao membro anterior (previous), a melhor opção seria colocar na cabeça
        porém isto é uma DLL, logo não faz diferença em termos de desempenho
        */
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
        return new LinkedStackIterator();
    }

    private class LinkedStackIterator implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return size() == 0;
        }

        @Override
        public T next() {
            return pop();
        }
    }
}
