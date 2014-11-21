package br.hybridlab.standalone.model;

import javax.persistence.*;
import java.sql.Time;
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

    private Date date;

    private Date hour;

    private Date duration;

    @ManyToOne
    @JoinColumn(name = "fk_car_id")
    private Car car;

    private String simulationReport;

    public Simulation(){

    }

    public Simulation(Long id, Date date, Time hour, Time duration, Car car,
                      String simulationReport) {
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.duration = duration;
        this.car = car;
        this.simulationReport = simulationReport;
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

    public Date getHour() {
        return hour;
    }

    public void setHour(Date hour) {
        this.hour = hour;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getSimulationReport() {
        return simulationReport;
    }

    public void setSimulationReport(String simulationReport) {
        this.simulationReport = simulationReport;
    }
}
