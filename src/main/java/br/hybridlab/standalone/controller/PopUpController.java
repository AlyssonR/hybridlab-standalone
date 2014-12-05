package br.hybridlab.standalone.controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * Created by alysson on 21/11/14.
 */
public class PopUpController {

    private final String numberLamps;

    @FXML
    private Button continueButton;

    @FXML
    private ImageView bulbConfigurationImg;

    @FXML
    private Text txnumberOfBulbValue;

    @FXML
    public void initialize() {
        continueButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) continueButton.getScene().getWindow();
                stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            }
        });

        Image img = new Image(getClass().getResourceAsStream("/img/" + numberLamps + "PowerLoss.png"));
        bulbConfigurationImg.setImage(img);

        txnumberOfBulbValue.setText(""+Integer.parseInt(numberLamps)/10);
    }

    public PopUpController(String numberLamps) {
        this.numberLamps = numberLamps;
    }
}