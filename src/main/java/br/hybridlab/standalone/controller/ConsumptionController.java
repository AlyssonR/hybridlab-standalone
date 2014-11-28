package br.hybridlab.standalone.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by alysson on 21/11/14.
 */
public class ConsumptionController {


    @FXML
    private LineChart<String, Integer> barChart;

    @FXML
    private NumberAxis xAxis;

    private ObservableList<String> monthNames = FXCollections.observableArrayList();

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
}
