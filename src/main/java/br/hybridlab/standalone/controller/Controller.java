package br.hybridlab.standalone.controller;

import br.hybridlab.standalone.dao.CarDAO;
import br.hybridlab.standalone.model.Car;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private CarDAO carDAO;

    @FXML
    private ComboBox<String> comboExperimentCarModel;

    @FXML
    private Text txValueMass;

    @FXML
    private Text txValuePower;

    @FXML
    private Text txValueDragCoefficient;

    @FXML
    private Text txValueFrontalArea;

    @FXML
    private Text txValueSpeed;

    @FXML
    public void initialize() {
        //Car models ComboBox:
        comboExperimentCarModel.setPromptText("Escolha um modelo...");
        List<Car> carList = carDAO.get();
        ArrayList<String> modelList = new ArrayList<String>();
        for (Car car : carList) {
            modelList.add(car.getModel());
        }
        ObservableList<String> list = FXCollections.observableArrayList(modelList);
        comboExperimentCarModel.setItems(list);

        //
        comboExperimentCarModel.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (oldValue != newValue) {
                    Car newCar = carDAO.getByModel(newValue);
                    txValueMass.setText(""+newCar.getMass());
                    txValuePower.setText(""+newCar.getPower());
                    txValueDragCoefficient.setText(""+newCar.getDragCoefficient());
                    txValueFrontalArea.setText(""+newCar.getFrontalArea());
                }
            }
        });
    }

    @FXML
    public void handleComboExperimentCarModelAction(ActionEvent event) {

    }

    public Controller() {}

    public Controller(CarDAO carDAO) {
        this.carDAO = carDAO;
    }
}
