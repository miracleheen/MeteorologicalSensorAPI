package com.sverdlov.sensor.SensorApi.validators;

import com.sverdlov.sensor.SensorApi.dto.MeasurementDTO;
import com.sverdlov.sensor.SensorApi.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementCreateValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementCreateValidator(SensorService sensorService) {
        this.sensorService =  sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasurementDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return;

        MeasurementDTO measurementDTO = (MeasurementDTO) target;

        if (!sensorService.findByName(measurementDTO.getSensor().getName())) {
            errors.rejectValue("sensor", "", "Sensor does not exist");
        }
    }
}
