package com.hiusahald.shop_online.exceptions;

public class FileUploadingException extends RuntimeException {

    public FileUploadingException() {
        super();
    }

    public FileUploadingException(String message) {
        super(message);
    }

    public FileUploadingException(String message, Throwable cause) {
        super(message, cause);
    }

}
