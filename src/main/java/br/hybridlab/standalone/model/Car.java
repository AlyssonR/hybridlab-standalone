package br.hybridlab.standalone.model;


import javax.persistence.*;

/**
 * Created by alysson on 19/11/14.
 */
@Entity
@Table(name = "chart_car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(columnDefinition = "varchar(50)")
    private String model;

    @Column(columnDefinition = "decimal(8,2)")
    private Double power;

    @Column(columnDefinition = "decimal(8,2)")
    private Double frontalArea;

    @Column(columnDefinition = "decimal(8,2)")
    private Double mass;

    @Column(columnDefinition = "decimal(8,2)")
    private Double dragCoefficient;


    public Car() {}

    public Car(String model, Double power, Double frontalArea,
               Double mass, Double dragCoefficient) {
        this.model = model;
        this.power = power;
        this.frontalArea = frontalArea;
        this.mass = mass;
        this.dragCoefficient = dragCoefficient;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Double getFrontalArea() {
        return frontalArea;
    }

    public void setFrontalArea(Double frontalArea) {
        this.frontalArea = frontalArea;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public Double getDragCoefficient() {
        return dragCoefficient;
    }

    public void setDragCoefficient(Double dragCoefficient) {
        this.dragCoefficient = dragCoefficient;
    }
}
