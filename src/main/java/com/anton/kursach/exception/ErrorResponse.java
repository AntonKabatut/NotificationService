package com.anton.kursach.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErrorResponse {

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private Long timestamp;

    @JsonProperty("path")
    private String path;

    public ErrorResponse() {
        this.timestamp = System.currentTimeMillis();
    }

    protected ErrorResponse(final String message, final Integer status, final String path) {
        this.message = message;
        this.status = status;
        this.path = path;
        this.timestamp = System.currentTimeMillis();
    }

    public static ErrorResponse of(final String message, final Integer status, final String path) {
        return new ErrorResponse(message, status, path);
    }

}
