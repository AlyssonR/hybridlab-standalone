package br.hybridlab.standalone;

import br.hybridlab.standalone.dao.CarDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

import java.net.URL;

public class HybridLab extends Application {

    private CommunicationService communicationService;
    private SessionFactory sessionFactory;
    //temporary:
    private static CarDAO carDAO = new CarDAO();

    public static void main(String[] args) throws Exception {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        communicationService = new CommunicationService();
        communicationService.initialize();
        String temp = System.getProperty("java.class.path");
        URL resource = getClass().getResource("br/hybridlab/standalone/view/pagina_inicial.fxml");

        Parent root = FXMLLoader.load(resource);

        Scene scene = new Scene(root, 300, 275);

        stage.setTitle("HybridLab");
        stage.setScene(scene);

//        AnchorPane pane = new AnchorPane();
//        pane.setPrefSize(400, 300);
//        Scene scene = new Scene(pane);
//        stage.setScene(scene);
//        TextField outputField = new TextField();
//        outputField.setPromptText("data from Arduino...");
//        outputField.setLayoutX((pane.getWidth() - outputField.getWidth()) / 2);
//        outputField.setLayoutY(100);
//        Button getBtn = new Button();
//        pane.getChildren().addAll(outputField, getBtn);
//
//        getBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                try {
//                    String inputLine = communicationService.getInput().readLine();
//                    outputField.setText(inputLine);
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        });
        stage.show();
    }
}

