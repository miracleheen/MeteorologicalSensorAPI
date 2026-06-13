package com.sverdlov.client;

import com.sverdlov.client.dto.MeasurementDTO;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

public class TemperatureChartViewer {
    private static final String URL = "http://localhost:8080/measurement";
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    public static void main(String[] args) {
        List<Double> temperatures = getTemperaturesFromServer();
        drawGraph(temperatures);
    }

    private static List<Double> getTemperaturesFromServer() {
        try {
            MeasurementDTO[] measurements = REST_TEMPLATE.getForObject(URL, MeasurementDTO[].class);

            if (measurements == null || measurements.length == 0) {
                return Collections.emptyList();
            }

            return Arrays.stream(measurements)
                    .map(MeasurementDTO::getValue)
                    .toList();

        } catch (RestClientException ex) {
            System.err.println("Failed to load measurements: " + ex.getMessage());
            return Collections.emptyList();
        }
    }

    private static void drawGraph(List<Double> temperatures) {
        if (temperatures.isEmpty()) {
            System.out.println("No temperature data available");
            return;
        }

        double[] xData = IntStream.range(0, temperatures.size())
                .asDoubleStream()
                .toArray();

        double[] yData = temperatures.stream()
                .mapToDouble(Double::doubleValue)
                .toArray();

        XYChart chart = QuickChart.getChart(
                "Temperatures",
                "X",
                "Y",
                "temperature",
                xData,
                yData
        );

        new SwingWrapper<>(chart).displayChart();
    }
}