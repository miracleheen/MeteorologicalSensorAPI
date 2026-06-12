package com.sverdlov.sensor.SensorApi.exceptions;

import com.sverdlov.sensor.SensorApi.responses.ErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SensorNotCreatedException.class)
    public ResponseEntity<ErrorException> handleEmptySensorException(SensorNotCreatedException ex) {
        ErrorException error = new ErrorException(ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MeasurementErrorException.class)
    public ResponseEntity<ErrorException> handleMeasurementException(MeasurementErrorException ex) {
        ErrorException error = new ErrorException(ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MeasurementsNotFoundException.class)
    public ResponseEntity<ErrorException> handleMeasurementsNotFoundException(MeasurementsNotFoundException ex) {
        ErrorException error = new ErrorException(ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
