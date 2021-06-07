package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Edge;
import model.Graph.Graph;
import model.Vertex.*;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {

    @FXML
    private ToggleButton btnOnAction;
    private Stage mainStage;
    private static Graph graph;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView imgBattery;
    @FXML
    private ImageView imgResistor;
    @FXML
    private ImageView imgLamp;
    @FXML
    private ImageView imgKey;

    private FXMLLoader fxmlLoader = new FXMLLoader();
    private FXMLLoader fxmlLoaderEdge = new FXMLLoader();
    private FXMLLoader fxmlLoaderDeleteEdge = new FXMLLoader();
    private FXMLLoader fxmlLoaderKey = new FXMLLoader();

    private Parent fxmlEdit;
    private VertexEditDialog vertexEditDialog;
    private Stage vertexEdit;

    private Parent fxmlDeleteEdge;
    private EdgeEditDialog deleteEdgeDialog;
    private Stage deleteEdge;

    private Parent fxmlEdgeEdit;
    private EdgeEditDialog edgeEditDialog;
    private Stage edgeEdit;

    private Parent fxmlSwitchkey;
    private controller.SwitchKey switchKey;
    private Stage keyEdit;

    private ActionEvent actionEvent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgBattery.setImage(new Image("/elements/Battery.png"));
        imgResistor.setImage(new Image("/elements/Res.png"));
        imgLamp.setImage(new Image("/elements/lampOff.png"));
        imgKey.setImage(new Image("/elements/keyOff.png"));

        fxmlLoaderDeleteEdge.setLocation(getClass().getResource("/fxml/DeleteEdge.fxml"));
        try {
            fxmlDeleteEdge = fxmlLoaderDeleteEdge.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        deleteEdgeDialog = fxmlLoaderDeleteEdge.getController();

        fxmlLoader.setLocation(getClass().getResource("/fxml/EditDialog.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("bundles.Locale", new Locale("en")));
        try {
            fxmlEdit = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        vertexEditDialog = fxmlLoader.getController();

        fxmlLoaderEdge.setLocation(getClass().getResource("/fxml/EdgeEditDialog.fxml"));
        fxmlLoaderEdge.setResources(ResourceBundle.getBundle("bundles.Locale", new Locale("en")));
        try {
            fxmlEdgeEdit = fxmlLoaderEdge.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        edgeEditDialog = fxmlLoaderEdge.getController();

        fxmlLoaderKey.setLocation(getClass().getResource("/fxml/SwitchKey.fxml"));
        fxmlLoaderKey.setResources(ResourceBundle.getBundle("bundles.Locale", new Locale("en")));
        try{
            fxmlSwitchkey = fxmlLoaderKey.load();
        } catch (IOException e){
            e.printStackTrace();
        }
        switchKey = fxmlLoaderKey.getController();

        keyEdit = initStage(keyEdit, fxmlSwitchkey);
        vertexEdit = initStage(vertexEdit, fxmlEdit);
        edgeEdit = initStage(edgeEdit, fxmlEdgeEdit);
        deleteEdge = initStage(deleteEdge, fxmlDeleteEdge);
    }

    public static void setGraph(Graph graphInit) { graph = graphInit; }

    public void addBattery(MouseDragEvent mouseDragEvent) { }

    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) return;
        Button clickedButton = (Button) source;
        String title = "";
        Image image;
        switch (clickedButton.getId()) {
            case "btnAddBattery":
                image = new Image("/elements/Battery.png");
                VertexView viewBattery = new VertexView(47, 50, 20, image, "");
                viewBattery.setRotate(VertexView.Rotate.vertical);
                Battery battery = new Battery();
                createElement(battery, viewBattery, vertexEditDialog, vertexEdit);
                graph.setBatteryID(battery.getID() - 1);
                graph.setBatteryConnected(true);
                break;
            case "btnAddResistor":
                image = new Image("/elements/Res.png");
                VertexView viewResistor = new VertexView(15, 140, 35, image, "");
                viewResistor.setRotate(VertexView.Rotate.horizontal);
                Resistor resistor = new Resistor();
                createElement(resistor, viewResistor, vertexEditDialog, vertexEdit);
                break;
            case "btnAddLamp":
                image = new Image("/elements/lampOff.png");
                VertexView viewLamp = new VertexView(15, 230, 42, image, "");
                viewLamp.setRotate(VertexView.Rotate.horizontal);
                Lamp lamp = new Lamp();
                createElement(lamp, viewLamp, vertexEditDialog, vertexEdit);
                break;
            case "btnAddKey":
                image = new Image("/elements/keyOff.png");
                VertexView viewKey = new VertexView(15, 320, 42, image, "");
                viewKey.setRotate(VertexView.Rotate.horizontal);
                Key key = new Key();
                createElement(key, viewKey, switchKey, keyEdit);
                break;
            case "btnAddEdge":
                Edge edge = new Edge();
                edgeEditDialog.setValues(graph, edge, anchorPane);
                showDialog(edgeEdit, "Connect", fxmlEdgeEdit);
                if (edge.getFrom() == null || edge.getTo() == null) break;
                edge.getFrom().addAdjacentEdge(edge);
                edge.getTo().addAdjacentEdge(edge);

                //todo Отрисовка линий
                edge.setEdgeView(new EdgeView(edge.getFrom().getVertexView(), edge.getTo().getVertexView()));
                edge.getFrom().getVertexView().addAdjacentLine(edge.getEdgeView());
                edge.getTo().getVertexView().addAdjacentLine(edge.getEdgeView());
                anchorPane.getChildren().addAll(edge.getEdgeView().getLines());
                graph.addEdge(edge);
                edge.setID(graph.edgeSize());
                for (Line o : edge.getEdgeView().getLines()) {
                    o.setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2) {
                            deleteEdgeDialog.setValues(graph, edge, anchorPane);
                            deleteEdge.showAndWait();
                        }
                    });
                }
                break;
        }
    }
    private Stage initStage(Stage stage, Parent parent){
        stage = new Stage();
        stage.setTitle("Edit");
        stage.setResizable(false);
        stage.setScene(new Scene(parent));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);
        return stage;
    }
    private void showDialog(Stage stage, String title, Parent parent) {
        if (stage == null){
            stage = new Stage();
            stage.setTitle(title);
            stage.setResizable(false);
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainStage);
        }
        stage.showAndWait();
    }

    public void setMainStage(Stage mainStage) { this.mainStage = mainStage; }

    private void createElement(Vertex vertex, VertexView vertexView, EditDialog editDialog, Stage stage){
        vertex.setVertexView(vertexView);
        graph.addVertex(vertex);
        vertex.setID(graph.vertexSize());
        vertexView.setTextLabel("(" + graph.vertexSize() + ")");
        anchorPane.getChildren().add(vertexView.getImageView());
        anchorPane.getChildren().add(vertexView.getLabel());
        vertexView.getImageView().setId("btnEdit");
        vertexView.getImageView().setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                editDialog.setValues(graph, vertex, anchorPane, vertex.getVertexView().getLabel());
                stage.showAndWait();
                System.out.println(vertex.getParameter());
            }
        });
        System.out.println(vertex.getParameter());
        System.out.println(vertex.getID());
    }

    public void btnOnAction(ActionEvent actionEvent) {
        if (btnOnAction.getText().equals("On")) {
            graph.printCycles();
            btnOnAction.setText("Off");
        }
        else {
            graph.turnOffGraph();
            btnOnAction.setText("On");
        }
    }

    public void clearAll(ActionEvent actionEvent) {
        graph.getVertices().clear();
        graph.getEdges().clear();
        anchorPane.getChildren().clear();
    }
}
