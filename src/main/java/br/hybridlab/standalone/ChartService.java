package br.hybridlab.standalone;

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

    public ChartService(LineChart<Number,Number> chart) {
        this.chart = chart;
    }


    public void init() {
        commService = CommunicationService.getInstance();
        xAxis = (NumberAxis) chart.getXAxis();
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);
        chart.getYAxis().setAutoRanging(true);
        chart.setAnimated(false);
//        chart.setTitle("combustível x tempo");
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

    private class AddToQueue implements Runnable {
        public void run() {
            try {
                // add a item of random data to queue
                Double temp =  commService.getReadValues();
                dataQ.add(temp);
                Thread.sleep(500);
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
