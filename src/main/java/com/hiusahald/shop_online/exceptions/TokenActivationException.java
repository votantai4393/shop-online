package com.hiusahald.shop_online.exceptions;

public class TokenActivationException extends RuntimeException {

    public TokenActivationException() {
        super();
    }

    public TokenActivationException(String message) {
        super(message);
    }

    public TokenActivationException(String message, Throwable cause) {
        super(message, cause);
    }
}
