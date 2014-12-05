package br.hybridlab.standalone;

import br.hybridlab.standalone.dao.ConsumptionDAO;
import br.hybridlab.standalone.dao.CurrentDAO;
import br.hybridlab.standalone.model.Consumption;
import br.hybridlab.standalone.model.Current;
import br.hybridlab.standalone.model.Simulation;
import javafx.animation.AnimationTimer;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by alysson on 30/11/14.
 */
public class ChartService {

    private String title;
    private ConsumptionDAO consumptionDAO;
    private CurrentDAO currentDAO;
    private Simulation simulation;
    private CommunicationService commService;

    private LineChart<Number,Number> chart;
    private static final int MAX_DATA_POINTS = 50;

    private XYChart.Series series;
    private int xSeriesData = 0;
    private ConcurrentLinkedQueue<Number> dataQ = new ConcurrentLinkedQueue<Number>();
    private ExecutorService executor;
    private AddToQueue addToQueue;
//    private Timeline timeline2;
    private NumberAxis xAxis;

    public ChartService(LineChart<Number,Number> chart, String title, ConsumptionDAO consumptionDAO,
                        CurrentDAO currentDAO, Simulation simulation) {
        this.chart = chart;
        this.title = title;
        this.currentDAO = currentDAO;
        this.consumptionDAO = consumptionDAO;
        this.simulation = simulation;
    }


    public void init() {
        commService = CommunicationService.getInstance();
        xAxis = (NumberAxis) chart.getXAxis();
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);
        chart.getYAxis().setAutoRanging(true);
        chart.setAnimated(false);
        series = new LineChart.Series<Number, Number>();
        series.setName("Line Chart Series");
        chart.getData().add(series);
        initParallelBehavior();
    }

    private void initParallelBehavior() {
        //-- Prepare Executor Services
        executor = Executors.newCachedThreadPool();
        addToQueue = new AddToQueue();
        executor.execute(addToQueue);
        //-- Prepare Timeline
        prepareTimeline();
    }

    Integer time = 0;
    private class AddToQueue implements Runnable {
        public void run() {
            try {
                // add a item of random data to queue
                Double temp = null;
                if (title.equals("consumption")) {
                    temp = commService.getConsumption();
                    consumptionDAO.save(new Consumption(simulation, temp, time));
                } else if (title.equals("current")) {
                    temp = commService.getCurrent();
                    currentDAO.save(new Current(simulation, temp, time));
                }
                time++;
                dataQ.add(temp);
                Thread.sleep(1000);
                executor.execute(this);
            } catch (InterruptedException ex) {
                //TODO:deal with it;
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
