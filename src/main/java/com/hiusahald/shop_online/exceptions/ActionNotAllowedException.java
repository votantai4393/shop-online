package com.hiusahald.shop_online.exceptions;

public class ActionNotAllowedException extends RuntimeException {

    public ActionNotAllowedException() {
        super();
    }

    public ActionNotAllowedException(String message) {
        super(message);
    }

    public ActionNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

}
