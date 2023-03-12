package com.parkinglot.parkinglotfees.configuration;

import com.parkinglot.parkinglotfees.exception.ErrorResponse;
import com.parkinglot.parkinglotfees.exception.ParkingServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class MessagesConfiguration {
    private static final Logger log = LoggerFactory.getLogger(MessagesConfiguration.class);
    private final Properties properties;
    private static final String TITLE = "title";
    private static final String STATUS = "status";
    private static final String CAUSE = "cause";
    private static final String ACTION = "action";
    private static final String DOT = ".";
    private static final String MESSAGES_FILE_NAME = "messages.properties";
    private MessagesConfiguration() {

        try (InputStream input = ParkingServiceException.class.getClassLoader()
                .getResourceAsStream(MESSAGES_FILE_NAME)) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException ex) {
            throw new ParkingServiceException(String.join(" ", "Unable to load messages.properties", ex.getMessage()), ex);
        }

    }

    private static final MessagesConfiguration singletonInstance = new MessagesConfiguration();

    public static MessagesConfiguration getConfig() {
        return singletonInstance;
    }

    /**
     * This method will return property for provided key.
     *
     * @param key key.
     * @return property value.
     */
    public String getMessage(String key) {
        return this.properties.getProperty(key);
    }

    /**
     * This method will return error response.
     *
     * @param codeKey errorCodeKey
     * @return error response.
     */
    public ErrorResponse getErrorResponse(String codeKey) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTitle(this.properties.getProperty(String.join(DOT, codeKey, TITLE)));
        try {
            errorResponse.setStatus(Integer.valueOf(this.properties.getProperty(String.join(DOT, codeKey, STATUS)).trim()));
        } catch (Exception e) {
            errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            log.warn("Error occurred while reading status {} {}", codeKey, e);
        }
        errorResponse.setCause(this.properties.getProperty(String.join(DOT, codeKey, CAUSE)));
        errorResponse.setAction(this.properties.getProperty(String.join(DOT, codeKey, ACTION)));
        errorResponse.setCode(codeKey);
        return errorResponse;
    }
}
