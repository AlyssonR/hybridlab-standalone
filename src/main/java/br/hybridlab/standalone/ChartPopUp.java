package br.hybridlab.standalone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Created by yago on 05/12/14.
 */
public class ChartPopUp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/largeGraph.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> type) {
                try {
//                    for (Constructor<?> constructor : type.getConstructors()) {
//                        if (constructor.getParameterCount()==1 &&
//                                constructor.getParameterTypes()[0]==String.class) {
//                            return constructor.newInstance(numberLamps);
//                        }
//                    }
                    // no matching constructor found, just call no-arg constructor as default:
                    return type.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null ;
                }
            }
        });
        Parent root = loader.<Parent>load();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Details");
        stage.setScene(scene);
        stage.showAndWait();
    }
}
