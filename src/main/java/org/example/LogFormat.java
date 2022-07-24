package org.example;

public class LogFormat {
    private String message;
    private String correlationId;

    public LogFormat(String message, String correlationId) {
        this.message = message;
        this.correlationId = correlationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
}
