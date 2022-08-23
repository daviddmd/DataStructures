package com.trivialware;

import java.util.Iterator;

public abstract class LinkedBinaryTree<T> implements BinaryTreeADT<T> {
    //adicionar altura e número de nós
    private BinaryNode<T> root;

    public LinkedBinaryTree() {
    }

    public LinkedBinaryTree(T data) {
        this.root = new BinaryNode<>(data);
    }

    @Override
    public boolean isEmpty() {
        return getRoot() == null;
    }

    @Override
    public int size() {
        if (isEmpty()) {
            return 0;
        }
        return root.getNumberChildren();
    }

    @Override
    public int height() {
        return root.getHeightRec();
    }

    @Override
    public void makeEmpty() {
        this.root = null;
    }


    public void setRoot(BinaryNode<T> root) {
        this.root = root;
    }

    //Esta é a maneira mais básica de fazer isto. Um método mais otimizado encontra-se na classe BST.
    @Override
    public boolean contains(T element) {
        return contains(element, root);
    }

    private boolean contains(T element, BinaryNode<T> node) {
        if (node == null) {
            return false;
        }
        if (node.getData().equals(element)) {
            return true;
        }
        boolean exists = contains(element, node.getLeft());
        if (!exists) {
            exists = contains(element, node.getRight());
        }
        return exists;
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        UnorderedListADT<T> list = new DoublyLinkedList<>();
        iteratePreOrder(this.root, list);
        return list.iterator();
    }

    @Override
    public Iterator<T> iteratorInOrder() {
        UnorderedListADT<T> list = new DoublyLinkedList<>();
        iterateInOrder(this.root, list);
        return list.iterator();
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        UnorderedListADT<T> list = new DoublyLinkedList<>();
        iteratePostOrder(this.root, list);
        return list.iterator();
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        UnorderedListADT<T> list = new DoublyLinkedList<>();
        iterateLevelOrder(this.root, list);
        return list.iterator();
    }

    @Override
    public T getRootElement() {
        if (!isEmpty()) {
            return root.getData();
        }
        return null;
    }

    private void iteratePreOrder(BinaryNode<T> currentNode, UnorderedListADT<T> list) {
        if (currentNode != null) {
            list.addLast(currentNode.getData());
            iteratePreOrder(currentNode.getLeft(), list);
            iteratePreOrder(currentNode.getRight(), list);
        }
    }

    private void iterateInOrder(BinaryNode<T> currentNode, UnorderedListADT<T> list) {
        if (currentNode != null) {
            iterateInOrder(currentNode.getLeft(), list);
            list.addLast(currentNode.getData());
            iterateInOrder(currentNode.getRight(), list);
        }
    }

    private void iteratePostOrder(BinaryNode<T> currentNode, UnorderedListADT<T> list) {
        if (currentNode != null) {
            iteratePostOrder(currentNode.getLeft(), list);
            iteratePostOrder(currentNode.getRight(), list);
            list.addLast(currentNode.getData());
        }
    }

    private void iterateLevelOrder(BinaryNode<T> currentNode, UnorderedListADT<T> list) {
        QueueADT<BinaryNode<T>> nodes = new LinkedQueue<>();
        BinaryNode<T> current;
        nodes.enqueue(currentNode);
        while (!nodes.isEmpty()) {
            current = nodes.dequeue();
            if (current != null) {
                list.addLast(current.getData());
                nodes.enqueue(current.getLeft());
                nodes.enqueue(current.getRight());
            }
        }
    }

    protected BinaryNode<T> getRoot() {
        return root;
    }


    protected static class BinaryNode<T> {
        private T data;
        private BinaryNode<T> left;
        private BinaryNode<T> right;

        private int height;
        private boolean color;

        public BinaryNode(T data, BinaryNode<T> left, BinaryNode<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.height = 0;
        }

        public BinaryNode(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 0;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public BinaryNode<T> getLeft() {
            return left;
        }

        public void setLeft(BinaryNode<T> left) {
            this.left = left;
        }

        public BinaryNode<T> getRight() {
            return right;
        }

        public void setRight(BinaryNode<T> right) {
            this.right = right;
        }

        public int getNumberChildren() {
            return 1 + getNumberChildrenRec(getLeft()) + getNumberChildrenRec(getRight());
        }

        private int getNumberChildrenRec(BinaryNode<T> node) {
            if (node == null) {
                return 0;
            }
            return 1 + getNumberChildrenRec(node.getLeft()) + getNumberChildrenRec(node.getRight());
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public boolean isColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }

        public int getHeightRec() {
            int leftHeight = heightRec(getLeft());
            int rightHeight = heightRec(getRight());
            int height = Math.max(leftHeight, rightHeight);
            this.height = height;
            return height;
        }

        private int heightRec(BinaryNode<T> node) {
            if (node == null) {
                return 0;
            }
            int leftHeight = heightRec(node.getLeft());
            int rightHeight = heightRec(node.getRight());
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }
}
