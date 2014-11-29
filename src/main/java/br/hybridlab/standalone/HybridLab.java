package br.hybridlab.standalone;

import br.hybridlab.standalone.dao.CarDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.hibernate.SessionFactory;

import java.lang.reflect.Constructor;
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

        URL resource = getClass().getResource("/pagina_inicial.fxml");

//        Parent root = FXMLLoader.load(resource);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pagina_inicial.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> type) {
                try {
                    for (Constructor<?> constructor : type.getConstructors()) {
                        if (constructor.getParameterCount()==1 &&
                                constructor.getParameterTypes()[0]==CarDAO.class) {
                            return constructor.newInstance(carDAO);
                        }
                    }
                    // no matching constructor found, just call no-arg constructor as default:
                    return type.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null ;
                }
            }
        });
        Parent root = loader.<Parent>load();

        Scene scene = new Scene(root, 1146, 796);

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

