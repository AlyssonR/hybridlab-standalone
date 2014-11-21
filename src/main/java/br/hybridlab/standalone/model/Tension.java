package br.hybridlab.standalone.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by alysson on 19/11/14.
 */

@Entity
@Table(name = "tb_tension")
public class Tension {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_simulation_id")
    private Simulation simulation;

    @ManyToOne
    @JoinColumn(name = "fk_experiment_id")
    private Experiment experiment;

    private Double value;

    private Date time;

    public Tension() {}

    public Tension(Long id, Simulation simulation, Experiment experiment, Double value, Date time) {
        this.id = id;
        this.simulation = simulation;
        this.experiment = experiment;
        this.value = value;
        this.time = time;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
