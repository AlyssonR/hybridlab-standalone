package br.hybridlab.standalone.controller;

import br.hybridlab.standalone.dao.CarDAO;
import br.hybridlab.standalone.model.Car;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private CarDAO carDAO;

    @FXML
    private ComboBox<String> comboExperimentCarModel;

    @FXML
    public void initialize() {
        comboExperimentCarModel.setPromptText("Escolha um modelo...");
        List<Car> carList = carDAO.get();
        ArrayList<String> modelList = new ArrayList<String>();
        for (Car car : carList) {
            modelList.add(car.getModel());
        }
        ObservableList<String> list = FXCollections.observableArrayList(modelList);
        comboExperimentCarModel.setItems(list);
    }

    public Controller() {}

    public Controller(CarDAO carDAO) {
        this.carDAO = carDAO;
    }
}
