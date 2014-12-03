package br.hybridlab.standalone.model;

import javax.persistence.*;

/**
 * Created by alysson on 19/11/14.
 */
@Entity
@Table(name = "chart_consumption")
public class Consumption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_simulation_id")
    private Simulation simulation;

    @Column(columnDefinition = "decimal(8,2)")
    private Double value;

    private Integer time;

    public Consumption() {}

    public Consumption(Integer id, Simulation simulation, Double value, Integer time) {
        this.id = id;
        this.simulation = simulation;
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
