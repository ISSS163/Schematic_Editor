package main.model.Graph;

import model.Edge;
import model.Vertex.Vertex;

import java.util.ArrayList;

public interface Graph {
    void setGraph(ArrayList<Vertex> vertexMap);
    void addVertex( Vertex vertex);
    void addEdge(Edge edge);
    int edgeSize();
    ArrayList<Vertex> getVertices();
    int vertexSize();
    void printCycles();
    void setBatteryID(int batteryID);
    ArrayList<Edge> getEdges();
    void turnOffGraph();
    void setBatteryConnected(boolean isConected);
}
