package com.sverdlov.sensor.SensorApi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class MeasurementDTO {
    @DecimalMin(value = "-100.0", message = "Temperature cannot be less than -100 degrees")
    @DecimalMax(value = "100.0", message = "Temperature cannot be bellow than 100 degrees")
    private double value;

    private boolean isRaining;

    @Valid
    @NotNull(message = "Sensor should not be null")
    private SensorDTO sensor;

    public MeasurementDTO() {
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

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "MeasurementDTO{" +
                "value=" + value +
                ", isRaining=" + isRaining +
                ", sensor=" + sensor +
                '}';
    }
}
