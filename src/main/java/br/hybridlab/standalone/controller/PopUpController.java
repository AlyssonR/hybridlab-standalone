package br.hybridlab.standalone.controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Created by alysson on 21/11/14.
 */
public class PopUpController {

    @FXML
    private Button continueButton;

    @FXML
    public void initialize() {
        continueButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) continueButton.getScene().getWindow();
                stage.fireEvent(new WindowEvent(stage,WindowEvent.WINDOW_CLOSE_REQUEST));
            }
        });
    }
}