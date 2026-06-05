package com.itb.inf2cm.CursiFy.model.dto;

import java.time.Instant;

public class ApiErrorResponse {
    private final String error;
    private final String message;
    private final int status;
    private final Instant timestamp;

    public ApiErrorResponse(String error, String message, int status) {
        this.error = error;
        this.message = message;
        this.status = status;
        this.timestamp = Instant.now();
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
