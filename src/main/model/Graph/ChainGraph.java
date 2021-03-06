package main.model.Graph;


import model.Edge;
import model.Vertex.Vertex;

import java.util.ArrayList;
import java.util.Collections;

public class ChainGraph implements Graph {

    private int vertexSize;
    private int edgeSize;
    private ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;
    private boolean batteryIsConnected;
    private ArrayList<String> catalogCycles = new ArrayList<String>();
    private ArrayList<ArrayList<Integer>> intCycles = new ArrayList<ArrayList<Integer>>();
    private int batteryID;

    public ChainGraph(){
        vertexSize = 0;
        edgeSize = 0;
        vertices = null;
        edges = new ArrayList<Edge>();
        batteryID = -1;
    }

    public int vertexSize() {
        return vertices.size();
    }
    public int edgeSize() { return edgeSize; }

    public void setBatteryIsConnected(boolean batteryIsConnected) {
        this.batteryIsConnected = batteryIsConnected;
    }

    public void turnOn(){ }

    public void setGraph(ArrayList<Vertex> vertexList) {
        this.vertices = vertexList;
    }

    public void addVertex( Vertex vertex) {
        vertices.add(vertex);
        vertexSize++;
    }
    public void addEdge(Edge edge){
        edges.add(edge);
        edgeSize++;
    }
    public ArrayList<Edge> getEdges() { return edges;}
    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    private void DFScycle(int u, int endV, ArrayList<Edge> E, int[] color,
                          int unavailableEdge, ArrayList<Integer> cycle) {
        if (u != endV)
            color[u] = 2;
        else if (cycle.size() >= 2) {
            Collections.reverse(cycle);
            String s = cycle.toArray()[0].toString();
            for (int i = 1; i < cycle.size(); i++)
                s += "-" + cycle.toArray()[i].toString();
            boolean flag = false;
            for (int i = 0; i < catalogCycles.size(); i++) {
                if (catalogCycles.toArray()[i].toString().equals(s)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                Collections.reverse(cycle);
                s = cycle.toArray()[0].toString();
                for (int i = 1; i < cycle.size(); i++) {
                    s += "-" + cycle.toArray()[i].toString();
                }
                intCycles.add(cycle);
                catalogCycles.add(s);
            }
            return;
        }
        for (int w = 0; w < E.size(); w++) {
            if (w == unavailableEdge)
                continue;
            if (color[E.get(w).getTo().getID()-1] == 1 && (E.get(w).getFrom().getID()-1) == u) {
                ArrayList<Integer> cycleNEW = new ArrayList<Integer>(cycle);
                cycleNEW.add(E.get(w).getTo().getID() );
                DFScycle(E.get(w).getTo().getID()-1, endV, E, color, w, cycleNEW);
                color[E.get(w).getTo().getID()-1] = 1;
            }
            else if (color[E.get(w).getFrom().getID()-1] == 1 && (E.get(w).getTo().getID()-1) == u) {
                ArrayList<Integer> cycleNEW = new ArrayList<Integer>(cycle);
                cycleNEW.add(E.get(w).getFrom().getID());
                DFScycle(E.get(w).getFrom().getID()-1, endV, E, color, w, cycleNEW);
                color[E.get(w).getFrom().getID()-1] = 1;
            }
        }
    }

    private void cyclesSearch() {
        catalogCycles.clear();
        intCycles.clear();
        int[] color = new int[vertices.size()];
        for (int k = 0; k < vertices.size(); k++)
            color[k] = 1;
        ArrayList<Integer> cycle = new ArrayList<Integer>();
        cycle.add(batteryID + 1);
        DFScycle(batteryID, batteryID, edges, color, -1, cycle);
    }
    public void printCycles(){
        if (batteryIsConnected) {
            for (Vertex vertex : vertices) {
                if (vertex.getParameter()== -1.0) {
                    vertex.setIncluded(false);
                }
            }
            cyclesSearch();
            for (int i = 0; i < catalogCycles.size(); ++i) {
                System.out.print(catalogCycles.toArray()[i]);
                for (ArrayList<Integer> intCycle : intCycles) {
                    for (int j = 0; j < intCycle.size(); ++j) {
                        if (Integer.parseInt(intCycle.toArray()[0].toString()) - 1 != batteryID) {
                            return;
                        }
                        if (vertices.get(Integer.parseInt(intCycle.toArray()[j].toString()) - 1).getName().equals("Key")) {
                            if (vertices.get(Integer.parseInt(intCycle.toArray()[j].toString()) - 1).getParameter() == -1.0) {
                                turnOffCycle(intCycle);
                                break;
                            }
                        } else {
                            vertices.get(Integer.parseInt(intCycle.toArray()[j].toString()) - 1).setIncluded(true);
                        }
                    }
                    System.out.println();
                }
            }
        }
    }

    @Override
    public void setBatteryID(int batteryID) {
        this.batteryID = batteryID;
    }
    public void turnOffCycle(ArrayList<Integer> cycle){
        for (Vertex vertex : vertices)
        {
            for (Integer intVertex : cycle) {
                if ((vertex.getID() == intVertex) && (!vertex.getName().equals("Key"))) {
                    if (!vertex.isIncluded())
                        vertex.setIncluded(false);
                }

            }
        }
    }

    public void turnOnCycle(ArrayList<Integer> cycle){
        for (Vertex vertex : vertices) {
            for (Integer intVertex : cycle) {
                if (vertex.getID() == intVertex) vertex.setIncluded(true);
            }
        }
    }

    public void turnOffGraph() {
        for (Vertex vertex : vertices) {
            if (!vertex.getName().equals("Key"))
                vertex.setIncluded(false);
        }
    }

    public void setBatteryConnected(boolean isConnected){
        batteryIsConnected = isConnected;
    }
}
