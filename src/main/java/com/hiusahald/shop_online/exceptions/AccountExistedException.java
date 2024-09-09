package com.hiusahald.shop_online.exceptions;

public class AccountExistedException extends RuntimeException {

    public AccountExistedException() {
        super();
    }

    public AccountExistedException(String message) {
        super(message);
    }

}
