package com.hiusahald.shop_online.exceptions;

public class IncorrectTokenException extends RuntimeException {

    public IncorrectTokenException() {
        super();
    }

    public IncorrectTokenException(String message) {
        super(message);
    }

}
