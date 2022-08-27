package com.trivialware;

import java.util.Iterator;

public class LinkedQueue<T> implements QueueADT<T> {
    private final UnorderedListADT<T> list;

    public LinkedQueue() {
        /*
        Para uma Queue, em que as operações principais são inserir no fim (addLast) e remover o início (removeFirst),
        uma DoublyLinkedList é adequada, visto que com os nós sentinela, pode imediatamente aceder ao último elemento
        (para adicionar no fim), ou aceder ao nó sentinela no início, para adicionar no início.
         */
        this.list = new DoublyLinkedList<>();
    }

    @Override
    public boolean enqueue(T e) {
        list.addLast(e);
        return true;
    }

    @Override
    public T dequeue() {
        return list.removeFirst();
    }

    @Override
    public T first() {
        return list.getFirst();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
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
        return new LinkedQueueIterator();
    }

    public static LinkedQueue<Integer> joinTwoQueues(LinkedQueue<Integer> firstQueue, LinkedQueue<Integer> secondQueue) {
        LinkedQueue<Integer> result = new LinkedQueue<>();
        while (!firstQueue.isEmpty() && !secondQueue.isEmpty()) {
            if (firstQueue.first() <= secondQueue.first()) {
                result.enqueue(firstQueue.dequeue());
            }
            else{
                result.enqueue(secondQueue.dequeue());
            }
        }
        while(!firstQueue.isEmpty()){
            result.enqueue(firstQueue.dequeue());
        }
        while(!secondQueue.isEmpty()){
            result.enqueue(secondQueue.dequeue());
        }
        return result;
    }

    private class LinkedQueueIterator implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return size() != 0;
        }

        @Override
        public T next() {
            return list.removeFirst();
        }
    }
}
