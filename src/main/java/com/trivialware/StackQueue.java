package com.trivialware;

import java.util.Iterator;

public class StackQueue<T> implements QueueADT<T> {
    private final StackADT<T> inbox;
    private final StackADT<T> outbox;

    public StackQueue() {
        this.inbox = new LinkedStack<>();
        this.outbox = new LinkedStack<>();
    }

    @Override
    public boolean enqueue(T e) {
        inbox.push(e);
        return true;
    }

    @Override
    public T dequeue() {
        if (outbox.empty()) {
            while (!inbox.empty()) {
                outbox.push(inbox.pop());
            }
        }
        return outbox.pop();
    }

    @Override
    public T first() {
        if (outbox.empty()) {
            while (!inbox.empty()) {
                outbox.push(inbox.pop());
            }
        }
        return outbox.peek();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return inbox.size() + outbox.size();
    }

    @Override
    public boolean clear() {
        return inbox.clear();
    }

    @Override
    public Iterator<T> iterator() {
        return new StackQueueIterator();
    }

    private class StackQueueIterator implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return !isEmpty();
        }

        @Override
        public T next() {
            if (outbox.empty()) {
                while (!inbox.empty()) {
                    outbox.push(inbox.pop());
                }
            }
            return outbox.pop();
        }
    }
}
