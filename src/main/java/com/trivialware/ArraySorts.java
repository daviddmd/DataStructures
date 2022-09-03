package com.trivialware;

public class ArraySorts {
    public static <T extends Comparable<T>> void swap(T[] arr, int from, int to) {
        if (from != to) {
            T temp = arr[from];
            arr[from] = arr[to];
            arr[to] = temp;
        }
    }

    /*
    Selection sort é um algoritmo que em cada iteração encontra o menor elemento no array e coloca-o na menor posição
    possível, repetindo este processo n vezes para que os elementos fiquem ordenados por ordem crescente.
    i vai de 0 até ao penúltimo elemento, porque se assume que na última iteração o maior elemento está no final
    do array, e é mantido uma variável de menor índice atual que toma como valor a posição do elemento do array que
    (de momento) tem o seu menor valor, que por defeito é a posição atual f vai de i até ao final da lista, e se encontrar
    algum valor menor que o valor da lista na posição índice atual com menor valor, este índice irá assumir esta posição.
    No final da sub-iteração de f, irá-se trocar o valor da lista na posição i com o valor de posição do índice atual com
    menor valor. O desempenho deste algoritmo é (n-1*n) que é aproximadamente O(n^2).
     */
    public static <T extends Comparable<T>> void selectionSort(T[] arr) {
        int currentMinimumIndex;
        for (int i = 0; i < arr.length - 1; i++) {
            currentMinimumIndex = i;
            for (int f = i + 1; f < arr.length; f++) {
                if (arr[f].compareTo(arr[currentMinimumIndex]) < 0) {
                    currentMinimumIndex = f;
                }
            }
            if (i != currentMinimumIndex) {
                swap(arr, i, currentMinimumIndex);
            }
        }
    }

    public static <T extends Comparable<T>> void selectionSortRecursive(T[] arr) {
        selectionSortRec(arr, 0, arr.length);
    }

    private static <T extends Comparable<T>> void selectionSortRec(T[] arr, int start, int end) {
        if (start < end) {
            int currentMinimumIndex = start;
            for (int i = start + 1; i < end; i++) {
                if (arr[i].compareTo(arr[currentMinimumIndex]) < 0) {
                    currentMinimumIndex = i;
                }
            }
            if (start != currentMinimumIndex) {
                swap(arr, start, currentMinimumIndex);
            }
            selectionSortRec(arr, start + 1, end);
        }
    }

    /*
    Insertion sort funciona através de puxar os menores elementos para o início.
    O i começa em 1, assume-se que o primeiro elemento é o menor da lista. Posteriormente, verifica-se se o elemento
    atual é menor que quaisquer uns que o precedem, e se for, puxam-se estes elementos para a frente, até encontrar um
    menor ou igual ao mesmo. Quando se encontra este elemento, pára-se a iteração e substitui-se o elemento na posição
    atual pelo elemento que incorreu a subsituição prévia.
    O desempenho deste algoritmo é O(n^2) com melhor caso de O(N) caso o array esteja perto de organizado
     */
    public static <T extends Comparable<T>> void insertionSort(T[] arr) {
        T data;
        int index;
        for (int i = 1; i < arr.length; i++) {
            data = arr[i];
            index = i;
            while (index > 0 && arr[index - 1].compareTo(data) > 0) {
                arr[index] = arr[index - 1];
                index -= 1;
            }
            arr[index] = data;
        }
    }

    /*
    Bubble-Sort funciona através de puxar os maiores elementos para a frente do array.
    O valor de i começa na última posição e vai para o início, e o valor de f começa em 0 e vai até i.
    Isto garante que comparações entre elementos depois dos que são os verdadeiramente máximos não ocorrem.
    Em cada iteração, o máximo vai sendo puxado para a frente, e no final das iterações é garantido que a lista irá
    ter o seguinte formato: max1 < max2 < max3 < max4 < max5... sendo max5 o "maior dos máximos" e max1 o "menor dos máximos"
    O desempenho deste algoritmo é O(n^2).
     */
    public static <T extends Comparable<T>> void bubbleSort(T[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int f = 0; f < i; f++) {
                if (arr[f].compareTo(arr[f + 1]) > 0) {
                    swap(arr, f, f + 1);
                }
            }
        }
    }

    public static <T extends Comparable<T>> void quickSort(T[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    /*
    Há várias estratégias para escolher o pivô do quickSort. Optei por escolher o último, apesar que normalmente se
    escolhe um valor mediano e se troca com o último para facilitar operações, trocando a posição deste com o último
    no final, sendo esta posição no final da partição o pivô em que se irá dividir a lista com, de 0 até pivô e de pivô+1
    até final. O objetivo será colocar todos os elementos inferiores a pivô à esquerda do mesmo e todos superiores à direita
    do mesmo, retornando a posição do meio (em que todos os elementos inferiores estão à esquerda do mesmo e superiores à direita)
    e organizando as sub-listas à esquerda e à direita recursivamente. Em cada iteração percorre a lista n vezes, e como
    irá executar n vezes, pior caso = O(n^2). Melhor caso é O(n*log(n)) porque em cada iteração percorre a lista n vezes
    (ou cada sub-lista), porém só irá realizar esta operação log(n) vezes (Com um array com 6 elementos, irá realizar em
    média log(6) vezes
     */
    private static <T extends Comparable<T>> void quickSort(T[] arr, int min, int max) {
        int pivot;
        if (max > min) {
            pivot = partition(arr, min, max);
            quickSort(arr, min, pivot - 1);
            quickSort(arr, pivot + 1, max);
        }
    }

    /*
    A posição do pivô começa em min-1, ou seja, atrás do primeiro elemento.
    Se um elemento estiver à esquerda do valor do elemento pivô (neste caso o último), então incrementa-se esta posição por
    1 e troca-se o atual com o elemento na posição deste índice de pivô. A consequência disto é que os elementos que estejam
    à direita da posição do pivô irão ser puxados para a esquerda, e o elemento central (pivô) irá ter à sua esquerda items inferiores
    a si, e à direita superiores. Pior caso será O(n^2) caso a lista já esteja ordenada, em que o elemento a particionar será sempre
    o máximo, ex, numa lista com 5 elementos, pivô será igual a 4,3,2,1,0...
    No melhor caso (teórico), em que o pivô original é a mediana dos elementos, visto que irá dividir a lista em duas sub-listas com tamanhos
    iguais, o desempenho será O(n*log(n)). Tendo o exemplo de uma lista com 7 elementos, o quicksort levou 3 iterações, o que se aproxima
    a log2(7)~2.8.
    Na prática, deve-se escolher para a posição do pivô, o primeiro ou último elementos (para simplicidade), um aleatório para evitar o pior caso,
    ou a mediana-de-3 (primeiro, no meio do subarray, último), trocando com o último e realizando a partição como normal.
     */
    private static <T extends Comparable<T>> int partition(T[] arr, int min, int max) {
        T pivot = arr[max];
        int pivotIndex = min - 1;
        for (int i = min; i < max; i++) {
            if (arr[i].compareTo(pivot) <= 0) {
                pivotIndex += 1;
                swap(arr, pivotIndex, i);
            }
        }
        pivotIndex = pivotIndex + 1;
        swap(arr, pivotIndex, max);
        return pivotIndex;
    }

    public static <T extends Comparable<T>> void mergeSort(T[] arr) {
        //Criar um novo array em cada chamada recursiva incorre numa grande penalidade em termos de desempenho
        @SuppressWarnings({"unchecked"})
        T[] tmp = (T[]) new Comparable[arr.length];
        mergeSort(arr, tmp, 0, arr.length - 1);
    }

    /*
    Em cada iteração recursiva do mergeSort, são criadas como se árvore binárias. Irão haver geralmente log(n) níveis,
    ou iterações necessárias para unir as sub-listas e em cada iteração, percorre-se num nível aproximadamente n elementos.
    Portanto o desempenho é O(n*log(n)).
    O pior caso é também O(n*log(n)).
     */
    public static <T extends Comparable<T>> void mergeSort(T[] arr, T[] tmp, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            int mid = (leftIndex + rightIndex) / 2;
            mergeSort(arr, tmp, leftIndex, mid);
            mergeSort(arr, tmp, mid + 1, rightIndex);
            //Array a ser unido será de leftIndex até mid+1 e de mid+1 até rightIndex
            merge(arr, tmp, leftIndex, mid + 1, rightIndex);

        }
    }

    /*
        Array temporário tem o mesmo comprimento que o real, serve para as operações temporárias da junção dos 2 sub-arrays
        É inicializado com todos os valores em null, na operação de merge as únicas posições que irão ser modificadas
        e acedidas serão aquelas entre a posição esquerda e direita dos 2 subarrays, irá ser preenchido em ordem consoante
        os 2 subarrays. No final da operação, esta sequência ordenada será copiada para o array original a partir dos 2
        subarrays do fim para o início (porque o leftStart foi sobrescrito) a partir do número de elementos, para o
        próximo passo recursivo.
     */
    private static <T extends Comparable<T>> void merge(T[] arr, T[] tmp, int leftStart, int rightStart, int rightEnd) {
        int leftEnd = rightStart - 1;
        int tmpIndex = leftStart;
        int numberElements = (rightEnd - leftStart) + 1;
        int originalStart = leftStart;
        while (leftStart <= leftEnd && rightStart <= rightEnd) {
            if (arr[leftStart].compareTo(arr[rightStart]) <= 0) {
                tmp[tmpIndex++] = arr[leftStart++];
            }
            else {
                tmp[tmpIndex++] = arr[rightStart++];
            }
        }
        while (leftStart <= leftEnd) {
            tmp[tmpIndex++] = arr[leftStart++];
        }
        while (rightStart <= rightEnd) {
            tmp[tmpIndex++] = arr[rightStart++];
        }
        //Não se pode usar leftStart porque foi modificado para as iterações, atribui-se de rightEnd para trás
        //Também se pode usar este original start em vez do rightEnd
        for (int i = 0; i < numberElements; i++) {
            //arr[rightEnd] = tmp[rightEnd];
            //rightEnd -= 1;
            arr[originalStart + i] = tmp[originalStart + i];
        }
    }

    public static <T extends Comparable<T>> void heapSort(T[] arr) {
        HeapADT<T> heap = new ArrayHeap<>();
        for (int i = 0; i < arr.length; i++) {
            heap.insert(arr[i]);
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = heap.deleteMinimum();
        }
    }
}
