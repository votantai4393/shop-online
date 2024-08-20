package com.hiusahald.shop_online.exception;

import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handler(MethodArgumentNotValidException e) {
        return null;
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handler(MessagingException e) {
        return null;
    }
}
