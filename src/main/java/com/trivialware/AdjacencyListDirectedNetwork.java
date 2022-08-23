package com.trivialware;

import java.util.Iterator;

public class AdjacencyListDirectedNetwork<T> extends AdjacencyListDirectedGraph<T> implements NetworkADT<T> {
    public AdjacencyListDirectedNetwork() {
    }

    @Override
    public boolean addEdge(T originVertex, T destinationVertex, double weight) {
        AdjacencyListVertex<T> origin = getVertex(originVertex);
        AdjacencyListVertex<T> destination = getVertex(destinationVertex);
        if (origin == null || destination == null || origin.equals(destination)) {
            return false;
        }
        AdjacencyListVertex.Edge<T> edge = origin.getEdgeOfVertex(destination);
        if (edge != null) {
            return false;
        }
        origin.getEdges().addLast(new AdjacencyListVertex.Edge<>(destination, weight));
        setNumberOfEdges(super.getNumberOfEdges() + 1);
        return true;
    }

    @Override
    public void setEdgeWeight(T originVertex, T destinationVertex, double weight) {
        AdjacencyListVertex<T> origin = getVertex(originVertex);
        AdjacencyListVertex<T> destination = getVertex(destinationVertex);
        if (origin == null || destination == null || origin.equals(destination) || weight < 0) {
            return;
        }
        AdjacencyListVertex.Edge<T> edge = origin.getEdgeOfVertex(destination);
        if (edge != null) {
            edge.setWeight(weight);
        }
    }

    @Override
    public double getEdgeWeight(T originVertex, T destinationVertex) {
        AdjacencyListVertex<T> origin = getVertex(originVertex);
        AdjacencyListVertex<T> destination = getVertex(destinationVertex);
        if (origin == null || destination == null || origin.equals(destination)) {
            return -1;
        }
        AdjacencyListVertex.Edge<T> edge = origin.getEdgeOfVertex(destination);
        return edge == null ? -1 : edge.getWeight();
    }

    @Override
    public Iterator<T> getCheapestPathIterator(T originVertex, T destinationVertex) {
        StackADT<T> stack = new LinkedStack<>();
        getCheapestPath(originVertex, destinationVertex, stack);
        return stack.iterator();
    }

    @Override
    public double getCheapestPath(T originVertex, T destinationVertex, StackADT<T> path) {
        ListADT<AdjacencyListVertex<T>> vertices = getVertices();
        AdjacencyListVertex<T> origin = getVertex(originVertex);
        AdjacencyListVertex<T> destination = getVertex(destinationVertex);
        if (origin == null || destination == null || origin.equals(destination)) {
            return -1;
        }
        initializeVerticesForPathAlgorithms();
        origin.setPathCost(0);
        origin.setPredecessor(null);
        boolean done = false;
        HeapADT<EntryPQ<T>> heap = new ArrayHeap<>();
        heap.insert(new EntryPQ<>(origin, null, 0));
        EntryPQ<T> frontEntry;
        AdjacencyListVertex<T> frontVertex, neighbour;
        double neighbourCost;
        while (!done && !heap.isEmpty()) {
            frontEntry = heap.deleteMinimum();
            frontVertex = frontEntry.getVertex();
            if (!frontVertex.isVisited()) {
                frontVertex.setVisited(true);
                if (frontVertex.equals(destination)) {
                    done = true;
                }
                else {
                    for (AdjacencyListVertex.Edge<T> edge : frontVertex.getEdges()) {
                        neighbourCost = edge.getWeight() + frontVertex.getPathCost();
                        neighbour = edge.getVertex();
                        if (!neighbour.isVisited() && neighbourCost < neighbour.getPathCost()) {
                            neighbour.setPathCost(neighbourCost);
                            neighbour.setPredecessor(frontVertex);
                            heap.insert(new EntryPQ<>(neighbour, frontVertex, neighbourCost));
                        }
                    }
                }
            }
        }
        path.push(destination.getLabel());
        AdjacencyListVertex<T> currentVertex = destination;
        while (currentVertex.getPredecessor() != null) {
            currentVertex = currentVertex.getPredecessor();
            path.push(currentVertex.getLabel());
        }
        return destination.getPathCost();
    }

    private static class EntryPQ<T> implements Comparable<EntryPQ<T>> {
        private AdjacencyListVertex<T> vertex;
        private AdjacencyListVertex<T> previousVertex;
        private double pathCost;

        public EntryPQ(AdjacencyListVertex<T> vertex, AdjacencyListVertex<T> previousVertex, double pathCost) {
            this.vertex = vertex;
            this.previousVertex = previousVertex;
            this.pathCost = pathCost;
        }

        public AdjacencyListVertex<T> getVertex() {
            return vertex;
        }

        public void setVertex(AdjacencyListVertex<T> vertex) {
            this.vertex = vertex;
        }

        public AdjacencyListVertex<T> getPreviousVertex() {
            return previousVertex;
        }

        public void setPreviousVertex(AdjacencyListVertex<T> previousVertex) {
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
            return Double.compare(getPathCost(), o.getPathCost());
        }
    }
}
