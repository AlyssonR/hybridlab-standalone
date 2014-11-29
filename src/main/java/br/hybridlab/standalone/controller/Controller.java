package br.hybridlab.standalone.controller;

import br.hybridlab.standalone.dao.CarDAO;
import br.hybridlab.standalone.model.Car;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private ToggleGroup inputTypeRadioGroup;

    @FXML
    private RadioButton radioInclination;

    @FXML
    private TextField textFieldInclinationValue;

    @FXML
    private TextField textFieldPowerLossValue;

    @FXML
    private Button buttonStartSimulation;

    @FXML
    private TabPane tabPane;

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

        //selected car details:
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

        //radio buttons behavior
        inputTypeRadioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (inputTypeRadioGroup.getSelectedToggle().equals(radioInclination)) {
                    textFieldInclinationValue.setDisable(false);
                    textFieldPowerLossValue.setDisable(true);
                } else {
                    textFieldInclinationValue.setDisable(true);
                    textFieldPowerLossValue.setDisable(false);
                }
            }
        });

        //Start simulation button behavior
        buttonStartSimulation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                selectionModel.select(1);
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
