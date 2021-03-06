package main.model.Vertex;

import controller.VertexView;
import model.Edge;
import model.Graph.Graph;

import java.util.ArrayList;

public abstract class Vertex {

    private ArrayList<Edge> adjacentEdges = new ArrayList<Edge>();
    private double parameter;
    private boolean isIncluded = false;
    private boolean swichedOn = false;
    private double resistance = 1;
    private Vertex next;
    private Vertex prev;
    public String name;
    private VertexView vertexView;
    private int ID = -1;

    public double getParameter() {
        return parameter;
    }

    public void setParameter(double parameter) {
        this.parameter = parameter;
    }

    public abstract String getName();

    public double getResistance() {
        return resistance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    public boolean isIncluded() {
        return isIncluded;
    }

    abstract public void draw();

    public Vertex getPrev() {
        return prev;
    }

    public Vertex getNext() {
        return next;
    }

    public void setPrev(Vertex prev) {
        this.prev = prev;
    }

    public void setNext(Vertex next) {
        this.next = next;
    }

    public void setNext(Graph graph, int numberOfVertex) {
        next = graph.getVertices().get(numberOfVertex);
    }

    public void setPrev(Graph graph, int numberOfVertex) {
        prev = graph.getVertices().get(numberOfVertex);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public VertexView getVertexView() {
        return vertexView;
    }

    public void setVertexView(VertexView vertexView) {
        this.vertexView = vertexView;
    }

    public abstract Vertex getTop();

    public abstract Vertex getBottom();

    public void addAdjacentEdge(Edge edge) {
        adjacentEdges.add(edge);
    }

    public ArrayList<Edge> getAdjacentEdges() {
        return adjacentEdges;
    }


    public void setIncluded(boolean included) {
        isIncluded = included;
        if (included) {
            turnOnElement();
        } else {
            turnOffElement();
        }
    }



    public boolean setIncludedKey(double parameter) {
        if (parameter == 0.0) return true;
        else return false;
    }


    public abstract void turnOnElement();

    public abstract void turnOffElement();
}
