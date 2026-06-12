package com.sverdlov.sensor.SensorApi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @DecimalMin(value = "-100.0", message = "Temperature cannot be less than -100 degrees")
    @DecimalMax(value = "100.0", message = "Temperature cannot be bellow than 100 degrees")
    @Column(name = "value", nullable = false)
    private double value;

    @Column(name = "is_raining")
    private boolean isRaining;

    @ManyToOne
    @JoinColumn(name = "sensor", referencedColumnName = "name")
    private Sensor sensor;


    @Column(name = "measurement_date_time")
    private LocalDateTime measurementDateTime;

    public Measurement() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return isRaining;
    }

    public void setRaining(boolean raining) {
        isRaining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public LocalDateTime getMeasurementDateTime() {
        return measurementDateTime;
    }

    public void setMeasurementDateTime(LocalDateTime measurementDateTime) {
        this.measurementDateTime = measurementDateTime;
    }

    @Override
    public String toString() {
        return "Value: " + value + ", isRaining: " + isRaining + ", sensor: " + sensor + ", measurementDateTime: " + measurementDateTime;
    }
}
