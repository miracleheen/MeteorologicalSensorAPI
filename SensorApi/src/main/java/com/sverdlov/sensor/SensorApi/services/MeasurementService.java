package com.sverdlov.sensor.SensorApi.services;

import com.sverdlov.sensor.SensorApi.model.Measurement;
import com.sverdlov.sensor.SensorApi.model.Sensor;
import com.sverdlov.sensor.SensorApi.repositories.MeasurementRepository;
import com.sverdlov.sensor.SensorApi.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }

    public boolean findSensorByName(String sensorName) {
        return measurementRepository.existsBySensorName(sensorName);
    }

    @Transactional
    public void save(Measurement measurement) {
        enrichEntity(measurement);

        Sensor uniqueSensor = sensorRepository.findByName(measurement.getSensor().getName());
        measurement.setSensor(uniqueSensor);

        measurementRepository.save(measurement);
    }

    private void enrichEntity(Measurement measurement) {
        measurement.setMeasurementDateTime(LocalDateTime.now());

    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    @Transactional
    public int getRainyDaysCount() {
        return measurementRepository.countRainyDays();
    }
}
