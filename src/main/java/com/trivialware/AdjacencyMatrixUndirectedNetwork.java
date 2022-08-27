package com.trivialware;

import java.util.Random;

public class AdjacencyMatrixUndirectedNetwork<T> extends AdjacencyMatrixDirectedNetwork<T> implements UndirectedNetworkADT<T> {

    public AdjacencyMatrixUndirectedNetwork() {
    }

    public AdjacencyMatrixUndirectedNetwork(int defaultNumberVertices) {
        super(defaultNumberVertices);
    }

    @Override
    public boolean addEdge(T originVertex, T destinationVertex, double weight) {
        return super.addEdge(originVertex, destinationVertex, weight) && super.addEdge(destinationVertex, originVertex, weight);
    }

    @Override
    public void setEdgeWeight(T originVertex, T destinationVertex, double weight) {
        super.setEdgeWeight(originVertex, destinationVertex, weight);
        super.setEdgeWeight(destinationVertex, originVertex, weight);
    }

    @Override
    public int getNumberOfEdges() {
        return super.getNumberOfEdges() / 2;
    }

    @Override
    public boolean removeEdge(T originVertex, T destinationVertex) {
        return super.removeEdge(originVertex, destinationVertex) && super.removeEdge(destinationVertex, originVertex);
    }

    @Override
    public ListADT<T> getNeighbours(T vertex) {
        return super.getInNeighbours(vertex);
    }

    @Override
    public DirectedNetworkADT<T> minimumSpanningTreePrim() {
        Random r = new Random();
        T randomVertex = getVertices()[r.nextInt(0, getNumberOfVertices())];
        return minimumSpanningTreePrim(randomVertex);
    }

    @Override
    public DirectedNetworkADT<T> minimumSpanningTreePrim(T startingVertex) {

        UnorderedListADT<T> addedVertices = new ArrayList<>(getNumberOfVertices());
        addedVertices.addLast(startingVertex);
        DirectedNetworkADT<T> minimumSpanningTree = new AdjacencyMatrixUndirectedNetwork<>();
        //MST apenas se aplica a grafos conexos
        if (!isConnected() || getNumberOfVertices() == 0 || getNumberOfEdges() == 0) {
            return minimumSpanningTree;
        }
        minimumSpanningTree.addVertex(startingVertex);
        HeapADT<Edge<T>> heap = new ArrayHeap<>(getNumberOfEdges());
        Edge<T> minimumEdge;
        while (addedVertices.size() != getNumberOfVertices()) {
            for (T vertex : addedVertices) {
                for (T neighbour : getNeighbours(vertex)) {
                    if (!addedVertices.contains(neighbour)) {
                        heap.insert(new Edge<>(vertex, neighbour, getEdgeWeight(vertex, neighbour)));
                    }
                }
            }
            minimumEdge = heap.deleteMinimum();
            minimumSpanningTree.addEdge(minimumEdge.getStartingVertex(), minimumEdge.getEndingVertex(), minimumEdge.getCost());
            addedVertices.addLast(minimumEdge.getEndingVertex());
            heap.makeEmpty();
        }
        return minimumSpanningTree;
    }

    private static class Edge<T> implements Comparable<Edge<T>> {
        private T startingVertex;
        private T endingVertex;
        private double cost;

        public Edge(T startingVertex, T endingVertex, double cost) {
            this.startingVertex = startingVertex;
            this.endingVertex = endingVertex;
            this.cost = cost;
        }

        public T getStartingVertex() {
            return startingVertex;
        }

        public void setStartingVertex(T startingVertex) {
            this.startingVertex = startingVertex;
        }

        public T getEndingVertex() {
            return endingVertex;
        }

        public void setEndingVertex(T endingVertex) {
            this.endingVertex = endingVertex;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge<T> o) {
            return Double.compare(getCost(), o.getCost());
        }
    }
}
