package com.sverdlov.sensor.SensorApi.responses;

import java.time.LocalDateTime;

public class ErrorException {
    private String error;
    private LocalDateTime timestamp;

    public ErrorException(String error, LocalDateTime timestamp) {
        this.error = error;
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
