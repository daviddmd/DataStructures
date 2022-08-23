package com.trivialware;

import java.util.Iterator;

public class AdjacencyMatrixDirectedGraph<T> implements DirectedGraphADT<T> {
    private int numberOfVertices;
    private int numberOfEdges;
    private double[][] adjacencyMatrix;
    private int DEFAULT_NUMBER_VERTICES = 20;
    private T[] vertices;

    public AdjacencyMatrixDirectedGraph() {
        this.numberOfEdges = 0;
        this.numberOfVertices = 0;
        expandAdjacencyMatrix(DEFAULT_NUMBER_VERTICES);
    }

    public AdjacencyMatrixDirectedGraph(int defaultNumberVertices) {
        this.DEFAULT_NUMBER_VERTICES = defaultNumberVertices;
        this.numberOfEdges = 0;
        this.numberOfVertices = 0;
        expandAdjacencyMatrix(DEFAULT_NUMBER_VERTICES);
    }

    @Override
    public boolean addVertex(T vertex) {
        if (getPositionOfVertex(vertex) != -1) {
            return false;
        }
        if (numberOfVertices == vertices.length) {
            expandAdjacencyMatrix(vertices.length * 2);
        }
        vertices[numberOfVertices] = vertex;
        numberOfVertices += 1;
        return true;
    }

    @Override
    public boolean addEdge(T originVertex, T destinationVertex) {
        int originVertexPosition = getPositionOfVertex(originVertex);
        int destinationVertexPosition = getPositionOfVertex(destinationVertex);
        if (originVertexPosition == -1 || destinationVertexPosition == -1 || originVertexPosition == destinationVertexPosition) {
            return false;
        }
        if (adjacencyMatrix[originVertexPosition][destinationVertexPosition] != 0) {
            return false;
        }
        adjacencyMatrix[originVertexPosition][destinationVertexPosition] = 1;
        numberOfEdges += 1;
        return true;
    }

    protected int getPositionOfVertex(T vertex) {
        for (int i = 0; i < numberOfVertices; i++) {
            if (vertices[i].equals(vertex)) {
                return i;
            }
        }
        return -1;
    }

    private void expandAdjacencyMatrix(int newSize) {
        double[][] tmpAdjacencyMatrix = new double[newSize][newSize];
        @SuppressWarnings({"unchecked"})
        T[] tmpVertices = (T[]) new Object[newSize];
        for (int i = 0; i < numberOfVertices; i++) {
            tmpVertices[i] = vertices[i];
        }
        for (int i = 0; i < numberOfVertices; i++) {
            for (int f = 0; f < numberOfVertices; f++) {
                tmpAdjacencyMatrix[i][f] = adjacencyMatrix[i][f];
            }
        }
        for (int i = numberOfVertices; i < newSize; i++) {
            for (int f = 0; f < newSize; f++) {
                tmpAdjacencyMatrix[i][f] = 0;
            }
        }
        this.vertices = tmpVertices;
        this.adjacencyMatrix = tmpAdjacencyMatrix;
    }

    @Override
    public boolean removeVertex(T vertex) {
        int vertexPosition = getPositionOfVertex(vertex);
        if (vertexPosition == -1) {
            return false;
        }
        for (int i = 0; i < numberOfVertices; i++) {
            if (adjacencyMatrix[i][vertexPosition] != 0) {
                adjacencyMatrix[i][vertexPosition] = 0;
                numberOfEdges -= 1;
            }
            if (adjacencyMatrix[vertexPosition][i] != 0) {
                adjacencyMatrix[vertexPosition][i] = 0;
                numberOfEdges -= 1;
            }
        }
        //Não se vai dar shift das linhas ANTES da linha a eliminar, apenas se movem as colunas para a esquerda
        for (int i = 0; i < vertexPosition; i++) {
            for (int f = vertexPosition; f < numberOfVertices - 1; f++) {
                adjacencyMatrix[i][f] = adjacencyMatrix[i][f + 1];
            }
            adjacencyMatrix[i][numberOfVertices - 1] = 0;
        }
        for (int i = vertexPosition; i < numberOfVertices - 1; i++) {
            //Copiar conteúdos da linha seguinte para a linha atual, efetivamente removendo a linha do vértice a eliminar
            for (int f = 0; f < numberOfVertices; f++) {
                adjacencyMatrix[i][f] = adjacencyMatrix[i + 1][f];
            }
            //Mover colunas para a esquerda
            for (int f = vertexPosition; f < numberOfVertices - 1; f++) {
                adjacencyMatrix[i][f] = adjacencyMatrix[i][f + 1];
            }
            adjacencyMatrix[i][numberOfVertices - 1] = 0;
        }
        //Limpar última linha para o próximo elemento
        for (int i = 0; i < numberOfVertices; i++) {
            adjacencyMatrix[numberOfVertices - 1][i] = 0;
        }
        for (int i = vertexPosition; i < numberOfVertices - 1; i++) {
            vertices[i] = vertices[i + 1];
        }
        vertices[numberOfVertices - 1] = null;
        numberOfVertices -= 1;
        return true;
    }

    @Override
    public boolean removeEdge(T originVertex, T destinationVertex) {
        int originVertexPosition = getPositionOfVertex(originVertex);
        int destinationVertexPosition = getPositionOfVertex(destinationVertex);
        if (originVertexPosition == -1 || destinationVertexPosition == -1 || originVertexPosition == destinationVertexPosition) {
            return false;
        }
        if (adjacencyMatrix[originVertexPosition][destinationVertexPosition] == 0) {
            return false;
        }
        adjacencyMatrix[originVertexPosition][destinationVertexPosition] = 0;
        numberOfEdges -= 1;
        return true;
    }

    @Override
    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    @Override
    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    @Override
    public boolean isEmpty() {
        return numberOfVertices == 0;
    }

    @Override
    public boolean isConnected() {
        if (isEmpty()) {
            return false;
        }
        //DFS a começar no primeiro elemento
        QueueADT<T> dfs = getDepthFirstTraversal(vertices[0]);
        /*
        boolean[] visited = new boolean[numberOfVertices];
        int vertexPosition;
        while (!dfs.isEmpty()) {
            vertexPosition = getPositionOfVertex(dfs.dequeue());
            visited[vertexPosition] = true;
        }
        //Verificar se todos os nós estão presentes no resultado do DFS. Se o grafo não estiver conectado, irão faltar alguns
        for (int i = 0; i < numberOfVertices; i++) {
            if (!visited[i]) {
                return false;
            }
        }

         */
        return dfs.size() == numberOfVertices;
    }

    @Override
    public void clear() {
        this.numberOfEdges = 0;
        this.numberOfVertices = 0;
        expandAdjacencyMatrix(DEFAULT_NUMBER_VERTICES);
    }

    @Override
    public void clearEdges() {
        for (int i = 0; i < numberOfVertices; i++) {
            for (int f = 0; f < numberOfVertices; f++) {
                adjacencyMatrix[i][f] = 0;
            }
        }
        numberOfEdges = 0;
    }

    @Override
    public QueueADT<T> getBreadthFirstTraversal(T originVertex) {
        QueueADT<T> traversalOrder = new LinkedQueue<>();
        if (getPositionOfVertex(originVertex) == -1) {
            return traversalOrder;
        }
        QueueADT<T> vertexQueue = new LinkedQueue<>();
        UnorderedListADT<T> visitedVertices = new DoublyLinkedList<>();
        visitedVertices.addLast(originVertex);
        traversalOrder.enqueue(originVertex);
        vertexQueue.enqueue(originVertex);
        T frontVertex;
        while (!vertexQueue.isEmpty()) {
            frontVertex = vertexQueue.dequeue();
            for (T neighbour : getOutNeighbours(frontVertex)) {
                if (!visitedVertices.contains(neighbour)) {
                    visitedVertices.addLast(neighbour);
                    traversalOrder.enqueue(neighbour);
                    vertexQueue.enqueue(neighbour);
                }
            }
        }
        return traversalOrder;
    }

    @Override
    public QueueADT<T> getDepthFirstTraversal(T originVertex) {
        QueueADT<T> traversalOrder = new LinkedQueue<>();
        if (getPositionOfVertex(originVertex) == -1) {
            return traversalOrder;
        }
        //A pilha nunca irá ter mais elementos do que o número de vértices. Funcionaria também com DLL
        StackADT<T> vertexStack = new ArrayStack<>(numberOfVertices);
        UnorderedListADT<T> visitedVertices = new DoublyLinkedList<>();
        visitedVertices.addLast(originVertex);
        traversalOrder.enqueue(originVertex);
        vertexStack.push(originVertex);
        T topVertex;
        boolean allNeighboursVisited;
        while (!vertexStack.empty()) {
            topVertex = vertexStack.peek();
            allNeighboursVisited = true;
            for (T neighbour : getOutNeighbours(topVertex)) {
                if (!visitedVertices.contains(neighbour)) {
                    allNeighboursVisited = false;
                    visitedVertices.addLast(neighbour);
                    traversalOrder.enqueue(neighbour);
                    vertexStack.push(neighbour);
                    break;
                }
            }
            if (allNeighboursVisited) {
                vertexStack.pop();
            }
        }
        return traversalOrder;
    }

    @Override
    public Iterator<T> breadthFirstTraversalIterator(T originVertex) {
        return getBreadthFirstTraversal(originVertex).iterator();
    }

    @Override
    public Iterator<T> depthFirstTraversalIterator(T originVertex) {
        return getDepthFirstTraversal(originVertex).iterator();
    }

    @Override
    public Iterator<T> getShortestPathIterator(T originVertex, T destinationVertex) {
        StackADT<T> stack = new LinkedStack<>();
        getShortestPath(originVertex, destinationVertex, stack);
        return stack.iterator();
    }

    @Override
    public int getShortestPath(T originVertex, T destinationVertex, StackADT<T> path) {
        int originVertexIndex = getPositionOfVertex(originVertex);
        int destinationVertexIndex = getPositionOfVertex(destinationVertex);
        if (originVertexIndex == -1 || destinationVertexIndex == -1 || originVertexIndex == destinationVertexIndex) {
            return -1;
        }
        int[] pathLength = new int[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            pathLength[i] = Integer.MAX_VALUE;
        }
        pathLength[originVertexIndex] = 0;
        int[] predecessors = new int[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            predecessors[i] = -1;
        }
        boolean done = false;
        QueueADT<T> vertexQueue = new LinkedQueue<>();
        UnorderedListADT<T> visitedVertices = new DoublyLinkedList<>();
        visitedVertices.addLast(originVertex);
        vertexQueue.enqueue(originVertex);
        T frontVertex;
        int frontVertexPosition, neighbourVertexPosition;
        while (!done && !vertexQueue.isEmpty()) {
            frontVertex = vertexQueue.dequeue();
            frontVertexPosition = getPositionOfVertex(frontVertex);
            for (T neighbour : getOutNeighbours(frontVertex)) {
                if (!visitedVertices.contains(neighbour)) {
                    neighbourVertexPosition = getPositionOfVertex(neighbour);
                    visitedVertices.addLast(neighbour);
                    pathLength[neighbourVertexPosition] = 1 + pathLength[frontVertexPosition];
                    predecessors[neighbourVertexPosition] = frontVertexPosition;
                    vertexQueue.enqueue(neighbour);
                }
                if (neighbour.equals(destinationVertex)) {
                    done = true;
                    break;
                }
            }
        }
        int currentVertexIndex = destinationVertexIndex;
        path.push(vertices[currentVertexIndex]);
        currentVertexIndex = predecessors[currentVertexIndex];
        while (currentVertexIndex != -1) {
            path.push(vertices[currentVertexIndex]);
            currentVertexIndex = predecessors[currentVertexIndex];
        }
        return pathLength[getPositionOfVertex(destinationVertex)];
    }


    @Override
    public ListADT<T> getInNeighbours(T vertex) {
        UnorderedListADT<T> list = new DoublyLinkedList<>();
        int vertexPosition = getPositionOfVertex(vertex);
        if (vertexPosition == -1) {
            return list;
        }
        for (int i = 0; i < getNumberOfVertices(); i++) {
            if (adjacencyMatrix[i][vertexPosition] != 0 && vertices[i] != null) {
                list.addLast(vertices[i]);
            }
        }
        return list;
    }

    @Override
    public ListADT<T> getOutNeighbours(T vertex) {
        UnorderedListADT<T> list = new DoublyLinkedList<>();
        int vertexPosition = getPositionOfVertex(vertex);
        if (vertexPosition == -1) {
            return list;
        }
        for (int i = 0; i < getNumberOfVertices(); i++) {
            if (adjacencyMatrix[vertexPosition][i] != 0 && vertices[i] != null) {
                list.addLast(vertices[i]);
            }
        }
        return list;
    }


    public void setNumberOfVertices(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
    }

    public void setNumberOfEdges(int numberOfEdges) {
        this.numberOfEdges = numberOfEdges;
    }

    protected double[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    protected T[] getVertices() {
        return vertices;
    }
}
