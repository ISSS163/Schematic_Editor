package main.model.Vertex;

public class Resistor extends Vertex {

    private boolean isConnected;
    private String name = "Resistor";

    private boolean isConnected(){
        return isConnected;
    }

    public double getParameter() {
        return 0;
    }

    public void setParameter(double voltage) {

    }

    @Override
    public String getName() {
        return name;
    }

    public void draw() {
        System.out.println(name);
    }

    @Override
    public Vertex getTop() {
        return null;
    }

    @Override
    public Vertex getBottom() {
        return null;
    }

    @Override
    public void turnOnElement() {

    }

    @Override
    public void turnOffElement() {

    }
}
