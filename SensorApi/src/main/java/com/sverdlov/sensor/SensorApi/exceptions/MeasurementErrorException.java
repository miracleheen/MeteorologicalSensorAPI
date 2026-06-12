package com.sverdlov.sensor.SensorApi.exceptions;


public class MeasurementErrorException extends RuntimeException {
    public MeasurementErrorException(String message) {
        super(message);
    }
}
