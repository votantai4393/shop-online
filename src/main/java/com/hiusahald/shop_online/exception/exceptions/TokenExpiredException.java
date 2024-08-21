package com.hiusahald.shop_online.exception.exceptions;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException() {
        super();
    }

    public TokenExpiredException(String message) {
        super(message);
    }

}
