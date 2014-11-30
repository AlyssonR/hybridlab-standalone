package br.hybridlab.standalone;

import br.hybridlab.standalone.dao.CarDAO;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.hibernate.SessionFactory;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HybridLab extends Application {

    private static final int MAX_DATA_POINTS = 50;

    private Series series;
    private int xSeriesData = 0;
    private ConcurrentLinkedQueue<Number> dataQ = new ConcurrentLinkedQueue<Number>();
    private ExecutorService executor;
    private AddToQueue addToQueue;
    private Timeline timeline2;
    private NumberAxis xAxis;

    private void init(Stage primaryStage) {
        xAxis = new NumberAxis(0,MAX_DATA_POINTS,MAX_DATA_POINTS/10);
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRanging(true);

        //-- Chart
        final LineChart<Number, Number> sc = new LineChart<Number, Number>(xAxis, yAxis) {
            // Override to remove symbols on each data point
            @Override protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {}
        };
        sc.setAnimated(false);
        sc.setId("liveLineChart");
        sc.setTitle("Animated Area Chart");

        //-- Chart Series
        series = new LineChart.Series<Number, Number>();
        series.setName("Area Chart Series");
        sc.getData().add(series);

        primaryStage.setScene(new Scene(sc));
    }



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
        Stage primaryStage = new Stage();
        init(primaryStage);
        primaryStage.show();

        //-- Prepare Executor Services
        executor = Executors.newCachedThreadPool();
        addToQueue = new AddToQueue();
        executor.execute(addToQueue);
        //-- Prepare Timeline
        prepareTimeline();
    }

    private class AddToQueue implements Runnable {
        public void run() {
            try {
                // add a item of random data to queue
                dataQ.add(Math.random());
                Thread.sleep(50);
                executor.execute(this);
            } catch (InterruptedException ex) {
                Logger.getLogger(HybridLab.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void prepareTimeline() {
        // Every frame to take any data from queue and add to chart
        new AnimationTimer() {
            @Override public void handle(long now) {
                addDataToSeries();
            }
        }.start();
    }

    private void addDataToSeries() {
        for (int i = 0; i < 20; i++) { //-- add 20 numbers to the plot+
            if (dataQ.isEmpty()) break;
            series.getData().add(new LineChart.Data(xSeriesData++, dataQ.remove()));
        }
        // remove points to keep us at no more than MAX_DATA_POINTS
        if (series.getData().size() > MAX_DATA_POINTS) {
            series.getData().remove(0, series.getData().size() - MAX_DATA_POINTS);
        }
        // update
        xAxis.setLowerBound(xSeriesData-MAX_DATA_POINTS);
        xAxis.setUpperBound(xSeriesData-1);
    }
}

