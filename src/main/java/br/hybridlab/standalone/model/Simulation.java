package br.hybridlab.standalone.model;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by alysson on 19/11/14.
 */
@Entity
@Table(name = "tb_simulation")
public class Simulation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "fk_car_id")
    private Car car;

    private Double inclination;

    private Double powerLoss;

    public Simulation(){

    }

    public Simulation(Long id, Date date, Car car) {
        this.id = id;
        this.date = date;
        this.car = car;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
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
