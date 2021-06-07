package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Edge;
import model.Graph.Graph;
import model.Vertex.Vertex;

import java.net.URL;
import java.util.ResourceBundle;

public class SwitchKey extends EditDialog implements Initializable {

    @FXML
    private Vertex key;

    @FXML
    private ToggleButton btnOnAction;
    private Graph graph;
    private ResourceBundle resourceBundle;
    private AnchorPane anchorPane;
    private Label label;


    public void closeAction(ActionEvent actionEvent){
        Node source = (Node)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.hide();
    }

    public void actionSave(ActionEvent actionEvent){
        key.setResistance(0);

        if (btnOnAction.getText().equals("On")) {
            btnOnAction.setText("Off");
            key.turnOnElement();
            key.setParameter(0.0);
        }
        else {
            btnOnAction.setText("On");
            key.turnOffElement();
            key.setParameter(-1.0);
        }
        closeAction(actionEvent);
    }

    @Override
    public void setValues(Graph graph, Vertex vertex, AnchorPane anchorPane, Label label) {
        this.anchorPane = anchorPane;
        this.graph = graph;
        this.label=label;
        this.key = vertex;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
    }

    public void deleteElement(ActionEvent actionEvent) {
        anchorPane.getChildren().remove(key.getVertexView().getImageView());
        anchorPane.getChildren().remove(label);
        for (Edge adjacentEdge : key.getAdjacentEdges()) {
            anchorPane.getChildren().removeAll(adjacentEdge.getEdgeView().getLines());
            graph.getEdges().remove(adjacentEdge);
        }
        if (key.getName().equals("Key")){
            graph.setBatteryConnected(false);
        }
        graph.getVertices().remove(key);
        for (int i = 0; i < graph.getVertices().size(); ++i) {
            graph.getVertices().get(i).setID(i+1);
            graph.getVertices().get(i).getVertexView().setTextLabel("(" + String.valueOf(i+1) + ")");
        }
        closeAction(actionEvent);
    }
}
