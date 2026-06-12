package com.sverdlov.sensor.SensorApi.controllers;

import com.sverdlov.sensor.SensorApi.dto.SensorDTO;
import com.sverdlov.sensor.SensorApi.exceptions.SensorNotCreatedException;
import com.sverdlov.sensor.SensorApi.model.Sensor;
import com.sverdlov.sensor.SensorApi.services.SensorService;
import com.sverdlov.sensor.SensorApi.validators.SensorValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensor")
public class SensorController {
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(SensorService sensorService, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> sensorRegistration(@RequestBody @Valid SensorDTO sensorDTO,
                                                     BindingResult bindingResult) {
        sensorValidator.validate(sensorDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.append(error.getDefaultMessage()).append("; ");
            }

            throw new SensorNotCreatedException(errors.toString());
        }
        sensorService.save(convertToSensor(sensorDTO));

        return ResponseEntity.ok("Sensor registered successfully!");
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}







