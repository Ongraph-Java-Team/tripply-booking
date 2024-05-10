package com.tripply.booking.Exception;

public class ServiceCommunicationException extends RuntimeException {
    /**
     * Constructs a new ServiceCommunicationException with the specified message about exception details.
     *
     * @param message The detail message of exception.
     */
    public ServiceCommunicationException(String message) {
        super(message);
    }
}
