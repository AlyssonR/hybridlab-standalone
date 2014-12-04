package br.hybridlab.standalone.controller;

import br.hybridlab.standalone.ChartService;
import br.hybridlab.standalone.dao.CarDAO;
import br.hybridlab.standalone.dao.ConsumptionDAO;
import br.hybridlab.standalone.dao.CurrentDAO;
import br.hybridlab.standalone.dao.SimulationDAO;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller {

    private SimulationDAO simulationDAO;
    private CarDAO carDAO;
    private Car selectedCar;
    private Simulation simulation;
    private ChartService chartService;
    private ChartService chartServiceT;
    private ConsumptionDAO consumptionDAO;
    private CurrentDAO currentDAO;

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
    private AttributeTextField textFieldInclinationValue;

    @FXML
    private AttributeTextField textFieldPowerLossValue;

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
        textFieldInclinationValue.setMaxLength(2);
        textFieldInclinationValue.setRestrict("^(([01]?[0-9])|(20))$");
        textFieldPowerLossValue.setMaxLength(3);
        textFieldPowerLossValue.setRestrict("\\b(0*(?:[1-9][0-9]?|100))\\b");


        Tooltip.install(
                textFieldInclinationValue,
                new Tooltip("A inclinação deve ser um valor entre 0 e 20.")
        );

        Tooltip.install(
                textFieldPowerLossValue,
                new Tooltip("A perda de potência deve ser um valor entre 0 e 100.")
        );

        Tooltip.install(
                comboExperimentCarModel,
                new Tooltip("Escolha um modelo de carro para prosseguir com a simulação.")
        );

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
                    txValueMass.setText("" + selectedCar.getMass()+" Kg");
                    txValuePower.setText("" + selectedCar.getPower()+" Kw");
                    txValueDragCoefficient.setText(""+selectedCar.getDragCoefficient());
                    txValueFrontalArea.setText("" + selectedCar.getFrontalArea()+" m²");
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
                        simulation.setPowerLoss(Double.parseDouble(textFieldPowerLossValue.getText()));
                        // teta=asind(((pot*potencia/velocidade-0.5*densidade_ar*velocidade*velocidade*coeficiente_aerodinamico*area_frontal)/(massa*gravidade)));
                        simulation.setInclination(((Math.asin((((simulation.getPowerLoss()/100)
                                * selectedCar.getPower() / speed - 0.5 * airDensity * speed * speed * selectedCar.getDragCoefficient() * selectedCar.getFrontalArea()) / (selectedCar.getMass() * gravity)))*180)/Math.PI));
                        numberLamps = LampsChoice(simulation);
                        Image img = new Image(getClass().getResourceAsStream("/img/" + numberLamps + "PowerLoss.png"));
                        ST_SimulationBulbConfigurationImg.setImage(img);

                    } else {
                        //pot=(massa*gravidade*sind(teta) + 0.5*densidade_ar*velocidade*velocidade*coeficiente_aerodinamico*area_frontal)*velocidade/potencia;
                        simulation.setInclination(Double.parseDouble(textFieldInclinationValue.getText()));
                        simulation.setPowerLoss(((selectedCar.getMass()*gravity*Math.sin(Double.parseDouble(textFieldInclinationValue.getText())
                                *Math.PI/180) + 0.5*airDensity*speed*speed*selectedCar.getDragCoefficient()*selectedCar.getFrontalArea())*speed/selectedCar.getPower())*100);
                        numberLamps = LampsChoice(simulation);
                        Image img = new Image(getClass().getResourceAsStream("/img/" + numberLamps + "PowerLoss.png"));
                        ST_SimulationBulbConfigurationImg.setImage(img);
                    }

                } catch (Exception e) { //TODO: create custom Exception
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Formulario Incompleto", JOptionPane.ERROR_MESSAGE);
                    formHasErrors = true;
                }

                simulation.setDate(new Date());
                simulationDAO.save(simulation);
                consumptionDAO = new ConsumptionDAO();
                currentDAO = new CurrentDAO();
                chartService = new ChartService(ST_SimulationConsumptionChart, "consumption", consumptionDAO, currentDAO, simulation);
                chartService.init();

                chartServiceT = new ChartService(ST_SimulationTensionChart, "current", consumptionDAO, currentDAO, simulation);
                chartServiceT.init();

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
                    ST_SImulationMassValue.setText("" + selectedCar.getMass()+" Kg");
                    ST_SImulationFrontalAreaValue.setText("" + selectedCar.getFrontalArea()+" m²");
                    ST_SImulationDragCoefficientValue.setText("" + selectedCar.getDragCoefficient());
                    ST_SImulationPowerValue.setText("" + selectedCar.getPower()+" Kw");
                    ST_SImulationPowerLossValue.setText(new DecimalFormat("##.##").format(simulation.getPowerLoss())+"%");
                    ST_SimulationInclinationValue.setText(new DecimalFormat("##.##").format(simulation.getInclination())+"º");

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

    }

    private String LampsChoice(Simulation simulation) {
        String number = null;
        if(simulation.getPowerLoss() < 5)
            number = "00";
        if(simulation.getPowerLoss() >= 5 && simulation.getPowerLoss() < 15)
            number = "10";
        if(simulation.getPowerLoss() >= 15 && simulation.getPowerLoss() < 25)
            number = "20";
        if(simulation.getPowerLoss() >= 25 && simulation.getPowerLoss() < 35)
            number = "30";
        if(simulation.getPowerLoss() >= 35 && simulation.getPowerLoss() < 45)
            number = "40";
        if(simulation.getPowerLoss() >= 45 && simulation.getPowerLoss() < 55)
            number = "50";
        if(simulation.getPowerLoss() >= 55 && simulation.getPowerLoss() < 65)
            number = "60";
        if(simulation.getPowerLoss() >= 65 && simulation.getPowerLoss() < 75)
            number = "70";
        if(simulation.getPowerLoss() >= 75 && simulation.getPowerLoss() < 85)
            number = "80";
        if(simulation.getPowerLoss() >= 85 && simulation.getPowerLoss() < 95)
            number = "90";
        if(simulation.getPowerLoss() >= 95)
            number = "100";

        return number;
    }

    public Controller() {}

    public Controller(CarDAO carDAO, SimulationDAO simulationDAO) {
        this.carDAO = carDAO;
        this.simulationDAO = simulationDAO;
    }
}
