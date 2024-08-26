package com.hiusahald.shop_online.exceptions;

public class ClientRequestException extends RuntimeException {

    public ClientRequestException() {
        super();
    }

    public ClientRequestException(String message) {
        super(message);
    }

}
