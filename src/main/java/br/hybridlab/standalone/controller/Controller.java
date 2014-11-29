package br.hybridlab.standalone.controller;

import br.hybridlab.standalone.dao.CarDAO;
import br.hybridlab.standalone.model.Car;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.swing.*;

public class Controller {

    private CarDAO carDAO;

    @FXML
    private ComboBox<String> comboExperimentCarModel;

    @FXML
    public void initialize() {}

    public Controller() {}

    public Controller(CarDAO carDAO) {
        this.carDAO = carDAO;
    }



}
