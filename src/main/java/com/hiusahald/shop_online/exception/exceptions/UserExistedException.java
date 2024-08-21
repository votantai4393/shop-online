package com.hiusahald.shop_online.exception.exceptions;

public class UserExistedException extends RuntimeException {

    public UserExistedException() {
        super();
    }

    public UserExistedException(String message) {
        super(message);
    }

}
