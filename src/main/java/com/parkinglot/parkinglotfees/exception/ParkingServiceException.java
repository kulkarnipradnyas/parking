package com.parkinglot.parkinglotfees.exception;

import com.parkinglot.parkinglotfees.configuration.MessagesConfiguration;

import java.util.Arrays;

public class ParkingServiceException extends RuntimeException {
    private final transient ErrorResponse errorResponse;

    private static ParkingServiceException build(String errorCode, Throwable cause) {
        return new ParkingServiceException(errorCode, cause, cause.getMessage());
    }
    public ParkingServiceException(String errorCode, Throwable cause, String... values) {

        super(errorCode.toString() + " " + (values != null? Arrays.stream(values).reduce(" \"\"", (s1, s2) -> s1 + " " + s2): ""), cause);

        ErrorResponse errorMessageResponse = MessagesConfiguration.getConfig().getErrorResponse(errorCode);

        StringBuilder stringBuilder = new StringBuilder("");

        if (values != null && values.length > 0) {
            for (String value : values) {
                if (value != null && !value.isEmpty() && !value.isBlank()) {
                    stringBuilder.append(" ");
                    stringBuilder.append(value);
                }
            }
        }

        String causeString = String.join("", errorMessageResponse.getCause(), stringBuilder.toString());

        if (causeString != null && !causeString.isEmpty() && !causeString.isBlank()) {
            causeString = causeString.trim();
        }

        errorMessageResponse.setCause(causeString);

        this.errorResponse = errorMessageResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
