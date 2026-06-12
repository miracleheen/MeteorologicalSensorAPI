package com.sverdlov.sensor.SensorApi.repositories;

import com.sverdlov.sensor.SensorApi.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    boolean existsByName(String name);
    Sensor findByName(String name);
}
