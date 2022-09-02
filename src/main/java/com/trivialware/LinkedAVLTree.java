package com.trivialware;

public class LinkedAVLTree<T extends Comparable<T>> extends LinkedBinarySearchTree<T> implements BinarySearchTreeADT<T> {
    public LinkedAVLTree() {
    }

    public LinkedAVLTree(T data) {
        super(data);
    }

    private static final int ALLOWED_IMBALANCE = 1;

    private int height(BinaryNode<T> node) {
        return node != null ? node.getHeight() : -1;
    }

    private void updateHeight(BinaryNode<T> node) {
        int leftChildHeight = height(node.getLeft());
        int rightChildHeight = height(node.getRight());
        node.setHeight(Math.max(leftChildHeight, rightChildHeight) + 1);
    }

    private int balanceFactor(BinaryNode<T> node) {
        return height(node.getRight()) - height(node.getLeft());
    }

    private BinaryNode<T> rotateLeft(BinaryNode<T> node) {
        BinaryNode<T> rightChild = node.getRight();
        node.setRight(rightChild.getLeft());
        rightChild.setLeft(node);
        updateHeight(node);
        updateHeight(rightChild);
        return rightChild;
    }

    private BinaryNode<T> rotateRight(BinaryNode<T> node) {
        BinaryNode<T> leftChild = node.getLeft();
        node.setLeft(leftChild.getRight());
        leftChild.setRight(node);
        updateHeight(node);
        updateHeight(leftChild);
        return leftChild;
    }

    private BinaryNode<T> balance(BinaryNode<T> node) {
        if (node == null) {
            return null;
        }
        int balanceFactor = balanceFactor(node);

        if (balanceFactor < -1 * ALLOWED_IMBALANCE) {
            //Pesada à Esquerda
            if (balanceFactor(node.getLeft()) <= 0) {
                //Rotação RR
                node = rotateRight(node);
            }
            else {
                //Rotação LR
                node.setLeft(rotateLeft(node.getLeft()));
                node = rotateRight(node);
            }
        }

        //Pesada à direita
        if (balanceFactor > ALLOWED_IMBALANCE) {
            if (balanceFactor(node.getRight()) >= 0) {
                //Rotação LL
                node = rotateLeft(node);
            }
            else {
                //Rotação RL
                node.setRight(rotateRight(node.getRight()));
                node = rotateLeft(node);
            }
        }
        updateHeight(node);
        return node;
    }

    @Override
    protected BinaryNode<T> insert(T element, BinaryNode<T> node) {
        BinaryNode<T> n = super.insert(element, node);
        return balance(n);

    }

    @Override
    protected BinaryNode<T> remove(T element, BinaryNode<T> node) {
        BinaryNode<T> n = super.remove(element, node);
        return balance(n);
    }
}
