package br.hybridlab.standalone;

import br.hybridlab.standalone.dao.CarDAO;
import br.hybridlab.standalone.dao.SimulationDAO;
import br.hybridlab.standalone.model.Car;
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
    private static CarDAO carDAO = new CarDAO();
    private static SimulationDAO simulationDAO = new SimulationDAO();

    public static void main(String[] args) throws Exception {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        communicationService = CommunicationService.getInstance();
        communicationService.initialize();
        String temp = System.getProperty("java.class.path");

        URL resource = getClass().getResource("/pagina_inicial.fxml");

//        carDAO.save(new Car("Gol 1.6",74300d, 2.03d, 1100d, 0.34d));
//        carDAO.save(new Car("Uno 1.0 ",54000d, 2.06d, 955d, 0.35d));


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pagina_inicial.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> type) {
                try {
                    for (Constructor<?> constructor : type.getConstructors()) {
                        if (constructor.getParameterCount()==2 &&
                                constructor.getParameterTypes()[0]==CarDAO.class &&
                                constructor.getParameterTypes()[1]== SimulationDAO.class) {
                            return constructor.newInstance(carDAO,simulationDAO);
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
        stage.show();
    }
}

