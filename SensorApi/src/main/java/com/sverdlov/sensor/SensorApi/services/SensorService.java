package com.sverdlov.sensor.SensorApi.services;

import com.sverdlov.sensor.SensorApi.model.Sensor;
import com.sverdlov.sensor.SensorApi.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public boolean findByName(String incomingNameDto) {
        return sensorRepository.existsByName(incomingNameDto);
    }

    @Transactional
    public void save(Sensor sensor) {
        //TODO: enrichEntity(sensor) -> пока нет необходимости
        sensorRepository.save(sensor);
    }

    private void enrichEntity(Sensor sensor) {
        //TODO
    }
}
