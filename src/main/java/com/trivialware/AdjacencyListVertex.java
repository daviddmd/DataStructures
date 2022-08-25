package com.trivialware;

public class AdjacencyListVertex<T> {
    private final T label;
    private UnorderedListADT<Edge<T>> edges;
    //Estas três variáveis serão úteis em algoritmos de caminhos
    private boolean visited;

    private double pathCost;

    private AdjacencyListVertex<T> predecessor;

    private int indegree;

    public AdjacencyListVertex(T label) {
        this.label = label;
        this.edges = new DoublyLinkedList<>();
        this.visited = false;
        this.pathCost = 0;
        this.predecessor = null;
        this.indegree = 0;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public double getPathCost() {
        return pathCost;
    }

    public void setPathCost(double pathCost) {
        this.pathCost = pathCost;
    }

    public AdjacencyListVertex<T> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(AdjacencyListVertex<T> predecessor) {
        this.predecessor = predecessor;
    }

    public T getLabel() {
        return label;
    }

    public void clearEdges() {
        edges = new DoublyLinkedList<>();
    }

    public UnorderedListADT<Edge<T>> getEdges() {
        return edges;
    }

    public Edge<T> getEdgeOfVertex(AdjacencyListVertex<T> vertex) {
        for (Edge<T> e : edges) {
            if (e.getVertex().equals(vertex)) {
                return e;
            }
        }
        return null;
    }

    public int getIndegree() {
        return indegree;
    }

    public void setIndegree(int indegree) {
        this.indegree = indegree;
    }


    public static class Edge<T> implements Comparable<Edge<T>> {

        private AdjacencyListVertex<T> vertex;
        private double weight;

        public Edge(AdjacencyListVertex<T> vertex, double weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        public AdjacencyListVertex<T> getVertex() {
            return vertex;
        }

        public double getWeight() {
            return weight;
        }

        public void setVertex(AdjacencyListVertex<T> vertex) {
            this.vertex = vertex;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge<T> o) {
            return Double.compare(getWeight(), o.getWeight());
        }
    }
}
