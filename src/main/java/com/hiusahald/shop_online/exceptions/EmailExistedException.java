package com.hiusahald.shop_online.exceptions;

public class EmailExistedException extends RuntimeException {

    public EmailExistedException() {
        super();
    }

    public EmailExistedException(String message) {
        super(message);
    }

}
