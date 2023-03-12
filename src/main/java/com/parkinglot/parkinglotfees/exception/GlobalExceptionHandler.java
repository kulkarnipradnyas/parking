package com.parkinglot.parkinglotfees.exception;


import com.parkinglot.parkinglotfees.configuration.MessagesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * This class will add information and handle all exceptions thrown from the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String SPACE = " ";


    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception exception, WebRequest request) {
        logger.error("Handle exception", exception);
        ErrorResponse response = MessagesConfiguration.getConfig().getErrorResponse(ErrorCodes.E311012.name());
        response.setCause(String.join(SPACE, response.getCause(), exception.getMessage()));
        return response;
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRuntimeException(RuntimeException exception, WebRequest request) {
        logger.warn("Handle runtime exception", exception);
        ErrorResponse response = MessagesConfiguration.getConfig().getErrorResponse(ErrorCodes.E311011.name());
        response.setCause(String.join(SPACE, response.getCause(), exception.getMessage()));
        return response;
    }

    @ExceptionHandler(value = ParkingServiceException.class)
    public ResponseEntity<ErrorResponse> handleParkingServiceException(ParkingServiceException exception, WebRequest request) {
        logger.warn("Handle Parking service exception", exception);
        ErrorResponse errorResponse = exception.getErrorResponse();
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatus()));
    }
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception, WebRequest request) {
        logger.error("Handle http request method not supported exception", exception);
        ErrorResponse response = MessagesConfiguration.getConfig().getErrorResponse(ErrorCodes.E311023.name());
        response.setCause(String.join(SPACE, response.getCause(), exception.getMessage()));
        return response;
    }
}