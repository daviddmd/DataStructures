package com.trivialware;

import java.util.Iterator;

public class AdjacencyMatrixDirectedNetwork<T> extends AdjacencyMatrixDirectedGraph<T> implements NetworkADT<T> {
    public AdjacencyMatrixDirectedNetwork() {
    }

    public AdjacencyMatrixDirectedNetwork(int defaultNumberVertices) {
        super(defaultNumberVertices);
    }

    @Override
    public boolean addEdge(T originVertex, T destinationVertex, double weight) {
        double[][] adjacencyMatrix = getAdjacencyMatrix();
        int originVertexPosition = getPositionOfVertex(originVertex);
        int destinationVertexPosition = getPositionOfVertex(destinationVertex);
        if (originVertexPosition == -1 || destinationVertexPosition == -1 || originVertexPosition == destinationVertexPosition || weight < 0) {
            return false;
        }
        if (adjacencyMatrix[originVertexPosition][destinationVertexPosition] != 0) {
            return false;
        }
        adjacencyMatrix[originVertexPosition][destinationVertexPosition] = weight;
        setNumberOfEdges(super.getNumberOfEdges() + 1);
        return true;
    }

    @Override
    public void setEdgeWeight(T originVertex, T destinationVertex, double weight) {
        double[][] adjacencyMatrix = getAdjacencyMatrix();
        int originVertexPosition = getPositionOfVertex(originVertex);
        int destinationVertexPosition = getPositionOfVertex(destinationVertex);
        if (originVertexPosition == -1 || destinationVertexPosition == -1 || originVertexPosition == destinationVertexPosition || weight < 0) {
            return;
        }
        adjacencyMatrix[originVertexPosition][destinationVertexPosition] = weight;
    }

    @Override
    public double getEdgeWeight(T originVertex, T destinationVertex) {
        double[][] adjacencyMatrix = getAdjacencyMatrix();
        int originVertexPosition = getPositionOfVertex(originVertex);
        int destinationVertexPosition = getPositionOfVertex(destinationVertex);
        if (originVertexPosition == -1 || destinationVertexPosition == -1 || originVertexPosition == destinationVertexPosition) {
            return -1;
        }
        return adjacencyMatrix[originVertexPosition][destinationVertexPosition];
    }


    @Override
    public Iterator<T> getCheapestPathIterator(T originVertex, T destinationVertex) {
        StackADT<T> stack = new LinkedStack<>();
        getCheapestPath(originVertex, destinationVertex, stack);
        return stack.iterator();

    }

    @Override
    public double getCheapestPath(T originVertex, T destinationVertex, StackADT<T> path) {
        T[] vertices = getVertices();
        int originVertexIndex = getPositionOfVertex(originVertex);
        int destinationVertexIndex = getPositionOfVertex(destinationVertex);
        if (originVertexIndex == -1 || destinationVertexIndex == -1 || originVertexIndex == destinationVertexIndex) {
            return -1;
        }
        double[] pathCost = new double[getNumberOfVertices()];
        for (int i = 0; i < getNumberOfVertices(); i++) {
            pathCost[i] = Double.MAX_VALUE;
        }
        pathCost[originVertexIndex] = 0;
        int[] predecessors = new int[getNumberOfVertices()];
        for (int i = 0; i < getNumberOfVertices(); i++) {
            predecessors[i] = -1;
        }
        boolean[] visited = new boolean[getNumberOfVertices()];
        for (int i = 0; i < getNumberOfVertices(); i++) {
            visited[i] = false;
        }
        boolean done = false;
        HeapADT<EntryPQ<T>> heap = new ArrayHeap<>();
        heap.insert(new EntryPQ<>(originVertex, null, 0));
        EntryPQ<T> frontEntry;
        T frontVertex;
        int frontVertexPosition, neighbourVertexPosition;
        double weightOfEdgeToNeighbour, neighbourCost;
        while (!done && !heap.isEmpty()) {
            frontEntry = heap.deleteMinimum();
            frontVertex = frontEntry.getVertex();
            frontVertexPosition = getPositionOfVertex(frontVertex);
            if (!visited[frontVertexPosition]) {
                visited[frontVertexPosition] = true;
                //pathCost[frontVertexPosition] = frontEntry.getPathCost();
                //predecessors[frontVertexPosition] = getPositionOfVertex(frontEntry.getPreviousVertex());
                if (frontVertex.equals(destinationVertex)) {
                    done = true;
                }
                else {
                    for (T neighbour : getOutNeighbours(frontVertex)) {
                        neighbourVertexPosition = getPositionOfVertex(neighbour);
                        weightOfEdgeToNeighbour = getEdgeWeight(frontVertex, neighbour);
                        neighbourCost = weightOfEdgeToNeighbour + pathCost[frontVertexPosition];
                        if (!visited[neighbourVertexPosition] && neighbourCost < pathCost[neighbourVertexPosition]) {
                            pathCost[neighbourVertexPosition] = neighbourCost;
                            predecessors[neighbourVertexPosition] = frontVertexPosition;
                            heap.insert(new EntryPQ<>(neighbour, frontVertex, neighbourCost));
                        }
                    }
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
        return pathCost[destinationVertexIndex];
    }

    private static class EntryPQ<T> implements Comparable<EntryPQ<T>> {
        private T vertex;
        private T previousVertex;
        private double pathCost;

        public EntryPQ(T vertex, T previousVertex, double pathCost) {
            this.vertex = vertex;
            this.previousVertex = previousVertex;
            this.pathCost = pathCost;
        }

        public T getVertex() {
            return vertex;
        }

        public void setVertex(T vertex) {
            this.vertex = vertex;
        }

        public T getPreviousVertex() {
            return previousVertex;
        }

        public void setPreviousVertex(T previousVertex) {
            this.previousVertex = previousVertex;
        }

        public double getPathCost() {
            return pathCost;
        }

        public void setPathCost(double pathCost) {
            this.pathCost = pathCost;
        }

        @Override
        public int compareTo(EntryPQ<T> o) {
            return Double.compare(getPathCost(),o.getPathCost());
            /*
            if (getPathCost() > o.getPathCost()) {
                return 1;
            }
            else if (getPathCost() == o.getPathCost()) {
                return 0;
            }
            return -1;

             */
        }
    }

}
