package br.hybridlab.standalone.model;

import javax.persistence.*;

/**
 * Created by alysson on 19/11/14.
 */
@Entity
@Table(name = "tb_experiment")
public class Experiment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double duration;

    @ManyToOne
    @JoinColumn(name = "fk_simulation_id")
    private Simulation simulation;

    private Double inclination;

    private Double powerLoss;

    public Experiment(){}

    public Experiment(Long id, Double duration, Simulation simulation, Double inclination, Double powerLoss) {
        this.id = id;
        this.duration = duration;
        this.simulation = simulation;
        this.inclination = inclination;
        this.powerLoss = powerLoss;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
