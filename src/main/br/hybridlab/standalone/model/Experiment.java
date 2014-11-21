package main.br.hybridlab.standalone.model;

/**
 * Created by alysson on 19/11/14.
 */
public class Experiment {

    private Integer id;
    private Double duration;
    private Simulation simulation;
    private Double inclination;
    private Double powerLoss;

    public Experiment(){}

    public Experiment(Integer id, Double duration, Simulation simulation, Double inclination, Double powerLoss) {
        this.id = id;
        this.duration = duration;
        this.simulation = simulation;
        this.inclination = inclination;
        this.powerLoss = powerLoss;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public Double getInclination() {
        return inclination;
    }

    public void setInclination(Double inclination) {
        this.inclination = inclination;
    }

    public Double getPowerLoss() {
        return powerLoss;
    }

    public void setPowerLoss(Double powerLoss) {
        this.powerLoss = powerLoss;
    }
}
