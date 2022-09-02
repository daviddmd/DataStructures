package com.trivialware;

import java.util.Iterator;

public class ArrayHeap<T extends Comparable<T>> implements HeapADT<T> {
    private T[] heap;
    private int numberElements;
    private int DEFAULT_CAPACITY = 10;

    /*
    Para esta implementação da Heap, usa-se o array na posição 0 para fins temporários
     */
    public ArrayHeap() {
        this.numberElements = 0;
        resizeHeap(DEFAULT_CAPACITY + 1);
    }

    public ArrayHeap(int defaultCapacity) {
        this.DEFAULT_CAPACITY = defaultCapacity;
        this.numberElements = 0;
        resizeHeap(DEFAULT_CAPACITY + 1);
    }

    /*
    Para HeapSort. Alternativa com melhor desempenho a inserir cada um dos elementos do array, o que seria uma operação de O(n*log(n)).
    Esta operação tem desempenho O(n) para construir a heap, e é garantido que a mesma estará ordenada.
     */
    public ArrayHeap(T[] items) {
        this.numberElements = 0;
        //Criar um Array para armazenar itens que recebemos e itens futuros
        resizeHeap(DEFAULT_CAPACITY + items.length + 1);
        this.numberElements = items.length;
        int index = 1;
        for (T item : items) {
            heap[index] = item;
            index += 1;
        }
        for (int i = numberElements / 2; i > 0; i--) {
            percolateDown(i);
        }


    }

    protected void resizeHeap(int newSize) {
        @SuppressWarnings({"unchecked"})
        T[] tmp = (T[]) new Comparable[newSize];
        if (!isEmpty()) {
            for (int i = 0; i < heap.length; i++) {
                tmp[i] = heap[i];
            }
        }
        this.heap = tmp;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return numberElements;
    }

    private static double log2(double value) {
        return Math.log(value) / Math.log(2);
    }

    public static int expectedCompleteBinaryTreeHeight(int numberElements) {
        return ((int) Math.ceil(log2(numberElements + 1))) - 1;
    }

    //Um heap ou priority queue é sempre uma árvore binária completa -> logo altura = log2(n+1)-1
    @Override
    public int height() {
        return size() == 0 ? 0 : expectedCompleteBinaryTreeHeight(numberElements);
    }

    @Override
    public void makeEmpty() {
        this.numberElements = 0;
        resizeHeap(DEFAULT_CAPACITY + 1);
    }

    @Override
    public boolean contains(T element) {
        for (int i = 1; i <= numberElements; i++) {
            if (element.equals(heap[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        UnorderedListADT<T> list = new DoublyLinkedList<>();
        iteratePreOrder(1, list);
        return list.iterator();
    }

    @Override
    public Iterator<T> iteratorInOrder() {
        UnorderedListADT<T> list = new DoublyLinkedList<>();
        iterateInOrder(1, list);
        return list.iterator();
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        UnorderedListADT<T> list = new DoublyLinkedList<>();
        iteratePostOrder(1, list);
        return list.iterator();
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        UnorderedListADT<T> list = new DoublyLinkedList<>();
        iterateLevelOrder(list);
        return list.iterator();
    }

    private void iteratePreOrder(int nodeIndex, UnorderedListADT<T> list) {
        if (nodeIndex <= size()) {
            list.addLast(this.heap[nodeIndex]);
            iteratePreOrder((nodeIndex * 2), list);
            iteratePreOrder(((nodeIndex * 2) + 1), list);
        }
    }

    private void iterateInOrder(int nodeIndex, UnorderedListADT<T> list) {
        if (nodeIndex <= size()) {
            iterateInOrder((nodeIndex * 2), list);
            list.addLast(this.heap[nodeIndex]);
            iterateInOrder(((nodeIndex * 2) + 1), list);
        }
    }

    private void iteratePostOrder(int nodeIndex, UnorderedListADT<T> list) {
        if (nodeIndex <= size()) {
            iteratePostOrder((nodeIndex * 2), list);
            iteratePostOrder(((nodeIndex * 2) + 1), list);
            list.addLast(this.heap[nodeIndex]);
        }
    }

    private void iterateLevelOrder(UnorderedListADT<T> list) {
        for (int i = 1; i <= size(); i++) {
            list.addLast(heap[i]);
        }
    }

    @Override
    public T getRootElement() {
        if (!isEmpty()) {
            return heap[1];
        }
        return null;
    }

    @Override
    public void insert(T element) {
        //reservar espaço para primeiro elemento
        if (numberElements == this.heap.length - 1) {
            resizeHeap(this.heap.length * 2 + 1);
        }
        /*
        Aqui está-se a usar o método Percolate Up.
        Pode-se pensar que o buraco será criado na primeira posição disponível na árvore binária completa, e o elemento
        a adicionar será colocado nesse buraco. Posteriormente, um ciclo será iniciado, em que até ao pai do nó atual
        deixar de ser maior que o elemento a adicionar, os elementos serão trocados, até encontrar o nó pai final, em
        que se irá inserir o nó em questão nesse local
         */
        numberElements += 1;
        int hole = numberElements;
        heap[0] = element;
        while (element.compareTo(heap[hole / 2]) < 0) {
            heap[hole] = heap[hole / 2];
            hole /= 2;
        }
        heap[hole] = element;
    }

    @Override
    public T deleteMinimum() {
        T minimum = findMinimum();
        if (minimum == null) {
            return null;
        }
        /*
        Aqui está-se a usar o método Percolate Down.
        Cria-se um "buraco" na raiz com o valor do último elemento da árvore binária completa, e pretende-se que este
        elemento seja puxado para o final da árvore. Para este efeito, na raiz da árvore, escolhe-se o menor dos filhos
        e troca-se este novo elemento com o menor dos filhos, e repete-se o processo até o nó trocado não ter filhos.
         */
        heap[1] = heap[numberElements];
        numberElements -= 1;
        percolateDown(1);
        return minimum;
    }

    private void percolateDown(int hole) {
        //Cópia do valor do último elemento
        T tmp = heap[hole];
        int child;
        //Verifica se o nó atual é uma folha
        while (hole * 2 <= numberElements) {
            //Elemento à esquerda
            child = hole * 2;
            //Verificar se elemento à direita é menor do que o da esquerda
            if (child != numberElements && heap[child + 1].compareTo(heap[child]) < 0) {
                child += 1;
            }
            //Atribuir o valor do elemento à direita ou esquerda com o atual (pai dos mesmos)
            if (heap[child].compareTo(tmp) < 0) {
                heap[hole] = heap[child];
            }
            else {
                //Encontrou-se o nó final, atribuir o valor do último nó da árvore a este
                break;
            }
            hole = child;
        }
        //Estamos no elemento da árvore que não tem filhos, cujo pai é finalmente inferior ao mesmo
        heap[hole] = tmp;
    }

    @Override
    public T findMinimum() {
        return getRootElement();
    }
}
