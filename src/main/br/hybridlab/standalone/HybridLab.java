package main.br.hybridlab.standalone;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HybridLab extends Application {

    private CommunicationService communicationService;

    public static void main(String[] args) throws Exception {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        communicationService = new CommunicationService();
        communicationService.initialize();
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(400, 300);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        TextField outputField = new TextField();
        outputField.setPromptText("data from Arduino...");
        outputField.setLayoutX((pane.getWidth() - outputField.getWidth()) / 2);
        outputField.setLayoutY(100);
        Button getBtn = new Button();
        pane.getChildren().addAll(outputField, getBtn);

        getBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    String inputLine = communicationService.getInput().readLine();
                    outputField.setText(inputLine);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        stage.show();
    }
}

