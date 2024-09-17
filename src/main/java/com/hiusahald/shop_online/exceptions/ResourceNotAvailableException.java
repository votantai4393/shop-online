package com.hiusahald.shop_online.exceptions;

public class ResourceNotAvailableException extends RuntimeException {

    public ResourceNotAvailableException() {
        super();
    }

    public ResourceNotAvailableException(String message) {
        super(message);
    }

    public ResourceNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
