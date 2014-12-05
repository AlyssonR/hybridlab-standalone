package br.hybridlab.standalone;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by alysson on 04/12/14.
 */
public class BulbsPopUp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/popUp.fxml"));
        Parent root = loader.<Parent>load();
        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Configuração das Lâmpadas");
        stage.setScene(scene);
        stage.showAndWait();
    }
}
