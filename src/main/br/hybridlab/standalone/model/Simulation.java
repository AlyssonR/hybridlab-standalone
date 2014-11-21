package main.br.hybridlab.standalone.model;

import java.sql.Time;
import java.util.Date;

/**
 * Created by alysson on 19/11/14.
 */
public class Simulation {

    private Integer id;
    private Date date;
    private Time hour;
    private Time duration;
    private Car car;
    private String simulationReport;

    public Simulation(){

    }

    public Simulation(Integer id, Date date, Time hour, Time duration, Car car,
                      String simulationReport) {
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.duration = duration;
        this.car = car;
        this.simulationReport = simulationReport;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getHour() {
        return hour;
    }

    public void setHour(Time hour) {
        this.hour = hour;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
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
