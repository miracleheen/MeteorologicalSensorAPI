package com.sverdlov.client;

import com.sverdlov.client.dto.MeasurementDTO;
import com.sverdlov.client.dto.SensorDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ThreadLocalRandom;

public class Client {
    private static final String BASE_URL = "http://localhost:8080";
    private static final String SENSOR_REGISTRATION_URL = BASE_URL + "/sensor/registration";
    private static final String MEASUREMENT_ADD_URL = BASE_URL + "/measurement/add";

    private static final int MEASUREMENTS_COUNT = 500;
    private static final double MIN_TEMPERATURE = -50.0;
    private static final double MAX_TEMPERATURE = 50.0;
    private static final String SENSOR_NAME = "Air Temperature Probe";
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    public static void main(String[] args) {
        SensorDTO sensor = new SensorDTO(SENSOR_NAME);

        registerSensor(sensor);

        for (int i = 0; i < MEASUREMENTS_COUNT; i++) {
            MeasurementDTO measurement = new MeasurementDTO();
            measurement.setValue(generateTemperature());
            measurement.setRaining(ThreadLocalRandom.current().nextBoolean());
            measurement.setSensor(sensor);

            sendMeasurement(measurement);
        }
    }

    private static void registerSensor(SensorDTO sensor) {
        postJson(SENSOR_REGISTRATION_URL, sensor, "sensor registration");
    }

    private static void sendMeasurement(MeasurementDTO measurement) {
        postJson(MEASUREMENT_ADD_URL, measurement, "measurement");
    }

    private static double generateTemperature() {
        return ThreadLocalRandom.current().nextDouble(MIN_TEMPERATURE, MAX_TEMPERATURE);
    }

    private static void postJson(String url, Object body, String actionName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(body, headers);

        try {
            REST_TEMPLATE.postForObject(url, request, String.class);
            System.out.println(actionName + " successfully sent");
        } catch (HttpStatusCodeException ex) {
            System.err.println(actionName + " failed");
            System.err.println("HTTP status: " + ex.getStatusCode());
            System.err.println("Server response: " + ex.getResponseBodyAsString());
        } catch (RestClientException ex) {
            System.err.println(actionName + " failed");
            System.err.println("Client error: " + ex.getMessage());
        }
    }
}