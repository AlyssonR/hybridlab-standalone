package br.hybridlab.standalone.controller;

import br.hybridlab.standalone.dao.CarDAO;
import br.hybridlab.standalone.model.Car;
import br.hybridlab.standalone.model.Simulation;
import javafx.beans.property.ReadOnlyObjectProperty;
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
    private Car selectedCar;
    private Simulation simulation;

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
                    selectedCar = carDAO.getByModel(newValue);
                    txValueMass.setText("" + selectedCar.getMass());
                    txValuePower.setText("" + selectedCar.getPower());
                    txValueDragCoefficient.setText(""+selectedCar.getDragCoefficient());
                    txValueFrontalArea.setText("" + selectedCar.getFrontalArea());
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

                boolean formHasErrors = false;

                //populating simulation field
                try {
                    simulation = new Simulation();
                    if (selectedCar != null) {
                        simulation.setCar(selectedCar);
                    } else {
                        throw new Exception();
                    }
                    if (textFieldInclinationValue.getText().isEmpty() && textFieldPowerLossValue.getText().isEmpty()) {
                        throw new Exception();
                    } else if (!textFieldPowerLossValue.isDisabled()){
                            simulation.setPowerLoss(Double.parseDouble(textFieldPowerLossValue.getText()));
                    } else simulation.setInclination(Double.parseDouble(textFieldInclinationValue.getText()));
                } catch (Exception e) { //TODO: create custom Exception
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Formulario Incompleto", JOptionPane.ERROR_MESSAGE);
                    formHasErrors = true;
                }

                //Locking tab
                if (!formHasErrors) {
                    SelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                    selectionModel.select(1);
                    ObservableList<Tab> tabs = tabPane.getTabs();
                    tabs.get(0).setDisable(true);
                    tabs.get(2).setDisable(true);
                }



            }
        });

    }

    public Controller() {}

    public Controller(CarDAO carDAO) {
        this.carDAO = carDAO;
    }
}
