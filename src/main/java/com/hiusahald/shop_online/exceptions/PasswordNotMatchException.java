package com.hiusahald.shop_online.exceptions;

public class PasswordNotMatchException extends RuntimeException {

    public PasswordNotMatchException() {
        super();
    }

    public PasswordNotMatchException(String message) {
        super(message);
    }

}
