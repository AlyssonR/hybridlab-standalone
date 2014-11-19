package main.br.hybridlab.standalone.model;

import java.util.Date;

/**
 * Created by alysson on 19/11/14.
 */
public class Consumption {


    private Integer id;
    private Simulation simulation;
    private Experiment experiment;
    private Double value;
    private Date time;

    public Consumption() {}

    public Consumption(Integer id, Simulation simulation, Experiment experiment, Double value, Date time) {
        this.id = id;
        this.simulation = simulation;
        this.experiment = experiment;
        this.value = value;
        this.time = time;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public Experiment getExperiment() {
        return experiment;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
