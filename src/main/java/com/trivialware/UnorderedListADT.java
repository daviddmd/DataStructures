package com.trivialware;

public interface UnorderedListADT<T> extends ListADT<T> {
    /**
     * Adiciona um novo elemento na posição x, puxando os próximos para a frente
     *
     * @param index
     * @param e
     */
    void add(int index, T e);

    void addFirst(T e);

    void addLast(T e);

    boolean set(int index, T e);

}
