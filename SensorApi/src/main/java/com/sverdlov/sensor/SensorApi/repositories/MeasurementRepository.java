package com.sverdlov.sensor.SensorApi.repositories;

import com.sverdlov.sensor.SensorApi.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    boolean existsBySensorName(String name);

    @Query("SELECT COUNT(m) FROM Measurement m WHERE m.isRaining = true")
    int countRainyDays();
}
