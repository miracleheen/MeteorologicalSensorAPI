package com.sverdlov.sensor.SensorApi.exceptions;

public class MeasurementsNotFoundException extends RuntimeException  {
    public MeasurementsNotFoundException(String message) {
        super(message);
    }
}
