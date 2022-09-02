package com.trivialware;

public class LinkedBinarySearchTree<T extends Comparable<T>> extends LinkedBinaryTree<T> implements BinarySearchTreeADT<T> {
    public LinkedBinarySearchTree() {
    }

    public LinkedBinarySearchTree(T data) {
        super(data);
    }

    @Override
    public void insert(T element) {
        setRoot(insert(element, getRoot()));
    }

    /*
    A implementação desta BST não permite duplicados. Caso seja necessário permitir, edita-se o
    else if (compareResult > 0) para else if (compareResult >= 0) ou else simplesmente.
     */
    protected BinaryNode<T> insert(T element, BinaryNode<T> node) {
        if (node == null) {
            return new BinaryNode<>(element);
        }
        int compareResult = element.compareTo(node.getData());
        if (compareResult < 0) {
            node.setLeft(insert(element, node.getLeft()));
        }
        else if (compareResult > 0) {
            node.setRight(insert(element, node.getRight()));
        }
        return node;
    }

    @Override
    public void remove(T element) {
        if (!isEmpty()) {
            setRoot(remove(element, getRoot()));
        }
    }

    protected BinaryNode<T> remove(T element, BinaryNode<T> node) {
        if (node == null) {
            return null;
        }
        int compareResult = element.compareTo(node.getData());
        if (compareResult < 0) {
            node.setLeft(remove(element, node.getLeft()));
        }
        else if (compareResult > 0) {
            node.setRight(remove(element, node.getRight()));
        }
        else {
            if (node.getLeft() != null && node.getRight() != null) {
                node.setData(findMin(node.getRight()).getData());
                /*
                Para eliminar o nó que foi atribuído ao nó prévio (o sucessor imediato). Nesta chamada, se o nó
                que foi escolhido para substituição for uma folha, o mesmo vai ser atribuído como nulo, caso contrário
                será atribuído o seu sucessor imediato, e o processo vai-se repetir até chegar a uma folha.
                 */
                node.setRight(remove(node.getData(), node.getRight()));
            }
            else {
                node = (node.getLeft() == null) ? node.getRight() : node.getLeft();
            }
        }
        return node;
    }

    @Override
    public T findMin() {
        return isEmpty() ? null : findMin(getRoot()).getData();
    }

    private BinaryNode<T> findMin(BinaryNode<T> currentNode) {
        if (currentNode == null) {
            return null;
        }
        else if (currentNode.getLeft() == null) {
            return currentNode;
        }
        return findMin(currentNode.getLeft());
    }

    @Override
    public T findMax() {
        return isEmpty() ? null : findMax(getRoot()).getData();
    }

    private BinaryNode<T> findMax(BinaryNode<T> currentNode) {
        if (currentNode == null) {
            return null;
        }
        else if (currentNode.getRight() == null) {
            return currentNode;
        }
        return findMax(currentNode.getRight());
    }

    @Override
    public T removeMin() {
        if (!isEmpty()) {
            T minimumValue = findMin();
            remove(minimumValue);
            return minimumValue;
        }
        return null;
    }

    @Override
    public T removeMax() {
        if (!isEmpty()) {
            T maximumValue = findMax();
            remove(maximumValue);
            return maximumValue;
        }
        return null;
    }

    @Override
    public boolean contains(T element) {
        return contains(element, getRoot());
    }

    private boolean contains(T element, BinaryNode<T> node) {
        if (node == null) {
            return false;
        }
        int compareResult = element.compareTo(node.getData());
        if (compareResult < 0) {
            return contains(element, node.getLeft());
        }
        else if (compareResult > 0) {
            return contains(element, node.getRight());
        }
        else {
            return true;
        }
    }
}
