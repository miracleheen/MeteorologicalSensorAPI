package com.sverdlov.sensor.SensorApi.controllers;

import com.sverdlov.sensor.SensorApi.dto.MeasurementDTO;
import com.sverdlov.sensor.SensorApi.dto.RainyDaysCountDTO;
import com.sverdlov.sensor.SensorApi.exceptions.MeasurementErrorException;
import com.sverdlov.sensor.SensorApi.exceptions.MeasurementsNotFoundException;
import com.sverdlov.sensor.SensorApi.model.Measurement;
import com.sverdlov.sensor.SensorApi.services.MeasurementService;
import com.sverdlov.sensor.SensorApi.validators.MeasurementCreateValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurement")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final MeasurementCreateValidator measurementCreateValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, MeasurementCreateValidator measurementCreateValidator) {
        this.measurementService = measurementService;
        this.measurementCreateValidator = measurementCreateValidator;
    }

    @GetMapping()
    public ResponseEntity<List<MeasurementDTO>> getAllMeasurements() {
        List<MeasurementDTO> measurements = measurementService.findAll()
                .stream()
                .map(this::convertToMeasurementDTO)
                .toList();
        if (measurements.isEmpty()) {
            throw new MeasurementsNotFoundException("No measurements found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(measurements);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                 BindingResult bindingResult) {
        measurementCreateValidator.validate(measurementDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors
                        .append(fieldError.getDefaultMessage()).append("; ");
            }
            throw new MeasurementErrorException(errors.toString());
        }

        measurementService.save(convertToMeasurement(measurementDTO));

        return ResponseEntity.ok("Measurement added successfully");
    }

    @GetMapping("/rainyDaysCount")
    public ResponseEntity<RainyDaysCountDTO> getRainyDaysCount() {
        int count = measurementService.getRainyDaysCount();
        return ResponseEntity.status(HttpStatus.OK).body(new RainyDaysCountDTO(count));
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
