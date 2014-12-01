package br.hybridlab.standalone.controller;

import br.hybridlab.standalone.ChartService;
import br.hybridlab.standalone.dao.CarDAO;
import br.hybridlab.standalone.model.Car;
import br.hybridlab.standalone.model.Simulation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private CarDAO carDAO;
    private Car selectedCar;
    private Simulation simulation;
    private ChartService chartService;
    private ChartService chartServiceT;

    private Double gravity = 9.8;
    private Double airDensity = 1.23;
    private Double speed = 22.2;

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
    private Text ST_SImulationMassValue;

    @FXML
    private Text ST_SImulationDragCoefficientValue;

    @FXML
    private Text ST_SImulationFrontalAreaValue;

    @FXML
    private Text ST_SImulationPowerValue;

    @FXML
    private Text ST_SImulationCarModelValue;

    @FXML
    private Button ST_CancelSimulationButton;

    @FXML
    private Text ST_SImulationPowerLossValue;

    @FXML
    private Text ST_SimulationInclinationValue;

    @FXML
    private TabPane tabPane;

    @FXML
    private LineChart<Number,Number> ST_SimulationConsumptionChart;

    @FXML
    private LineChart<Number,Number> ST_SimulationTensionChart;

    @FXML
    private ImageView ST_SimulationBulbConfigurationImg;

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
                    textFieldPowerLossValue.setText("");
                } else {
                    textFieldInclinationValue.setDisable(true);
                    textFieldPowerLossValue.setDisable(false);
                    textFieldInclinationValue.setText("");
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
                    String numberLamps;
                    if (selectedCar != null) {
                        simulation.setCar(selectedCar);
                    } else {
                        throw new Exception();
                    }
                    if (textFieldInclinationValue.getText().isEmpty() && textFieldPowerLossValue.getText().isEmpty()) {
                        throw new Exception();
                    } else if (!textFieldPowerLossValue.isDisabled()){
                        simulation.setPowerLoss(Double.parseDouble(textFieldPowerLossValue.getText())/100);
                        simulation.setInclination(Math.asin((((Double.parseDouble(textFieldPowerLossValue.getText()) * selectedCar.getPower() / speed - 0.5 * airDensity * speed * speed * selectedCar.getDragCoefficient() * selectedCar.getFrontalArea()) / (selectedCar.getMass() * gravity)))));
                        numberLamps = LampsChoice(simulation);
                        Image img = new Image(getClass().getResourceAsStream("/img/"+numberLamps+"PowerLoss.png"));
                        ST_SimulationBulbConfigurationImg.setImage(img);

                    } else {
                        simulation.setInclination(Double.parseDouble(textFieldInclinationValue.getText()));
                        simulation.setPowerLoss((selectedCar.getMass()*gravity*Math.sin(Double.parseDouble(textFieldInclinationValue.getText()) + 0.5*airDensity*speed*speed*selectedCar.getDragCoefficient()*selectedCar.getFrontalArea())*speed/selectedCar.getPower()));
                        numberLamps = LampsChoice(simulation);
                        Image img = new Image(getClass().getResourceAsStream("/img/"+numberLamps+"PowerLoss.png"));
                        ST_SimulationBulbConfigurationImg.setImage(img);
                    }
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


        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if (newValue.getText().equals("Simulação") && simulation != null) {
                    ST_SImulationCarModelValue.setText("" + selectedCar.getModel());
                    ST_SImulationMassValue.setText("" + selectedCar.getMass());
                    ST_SImulationFrontalAreaValue.setText("" + selectedCar.getFrontalArea());
                    ST_SImulationDragCoefficientValue.setText("" + selectedCar.getDragCoefficient());
                    ST_SImulationPowerValue.setText("" + selectedCar.getPower());
                    ST_SImulationPowerLossValue.setText("" + simulation.getPowerLoss());
                    ST_SimulationInclinationValue.setText("" + simulation.getInclination());


                }
            }
        });

        //cancel button
        ST_CancelSimulationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Tab> tabs = tabPane.getTabs();
                tabs.get(0).setDisable(false);
                tabs.get(2).setDisable(false);
                tabPane.getSelectionModel().select(0);
            }
        });

        chartService = new ChartService(ST_SimulationConsumptionChart);
        chartService.init();

        chartServiceT = new ChartService(ST_SimulationTensionChart);
        chartServiceT.init();
    }

    private String LampsChoice(Simulation simulation) {
        String number = null;
        if(simulation.getPowerLoss() < 0.05)
            number = "00";
        if(simulation.getPowerLoss() >= 0.05 && simulation.getPowerLoss() < 0.15)
            number = "10";
        if(simulation.getPowerLoss() >= 0.15 && simulation.getPowerLoss() < 0.25)
            number = "20";
        if(simulation.getPowerLoss() >= 0.25 && simulation.getPowerLoss() < 0.35)
            number = "30";
        if(simulation.getPowerLoss() >= 0.35 && simulation.getPowerLoss() < 0.45)
            number = "40";
        if(simulation.getPowerLoss() >= 0.45 && simulation.getPowerLoss() < 0.55)
            number = "50";
        if(simulation.getPowerLoss() >= 0.55 && simulation.getPowerLoss() < 0.65)
            number = "60";
        if(simulation.getPowerLoss() >= 0.65 && simulation.getPowerLoss() < 0.75)
            number = "70";
        if(simulation.getPowerLoss() >= 0.75 && simulation.getPowerLoss() < 0.85)
            number = "80";
        if(simulation.getPowerLoss() >= 0.85 && simulation.getPowerLoss() < 0.95)
            number = "90";
        if(simulation.getPowerLoss() >= 0.95)
            number = "100";

        return number;
    }

    public Controller() {}

    public Controller(CarDAO carDAO) {
        this.carDAO = carDAO;
    }
}
