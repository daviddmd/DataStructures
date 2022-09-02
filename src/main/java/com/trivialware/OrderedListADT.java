package com.trivialware;

public interface OrderedListADT<T extends Comparable<? super T>> extends ListADT<T> {
    /**
     * É sempre assumido que a lista está sempre ordenada, portanto no momento da adição, a lista é percorrida em O(N) até encontrar
     * um elemento que é superior ao elemento a adicionar. Adicionar o elemento a adicionar entre o anterior e o atual (maior ou igual que o atual)
     *
     * @param e Elemento a Adicionar
     */
    void add(T e);
}
