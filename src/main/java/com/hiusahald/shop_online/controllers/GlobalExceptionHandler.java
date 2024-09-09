package com.hiusahald.shop_online.controllers;

import com.hiusahald.shop_online.exceptions.ClientRequestException;
import com.hiusahald.shop_online.dto.response.ExceptionResponse;
import com.hiusahald.shop_online.exceptions.FileUploadException;
import com.hiusahald.shop_online.exceptions.MailSendingException;
import jakarta.mail.MessagingException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientRequestException.class)
    public ResponseEntity<ExceptionResponse> handleException(ClientRequestException e) {
        ExceptionResponse response =
                ExceptionResponse.builder()
                        .error(e.getMessage())
                        .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException e) {
        Set<String> errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toSet());
        ExceptionResponse response =
                ExceptionResponse.builder()
                        .validationErrors(errors)
                        .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({DisabledException.class, LockedException.class, BadCredentialsException.class})
    public ResponseEntity<ExceptionResponse> handleException(DisabledException e) {
        ExceptionResponse response = ExceptionResponse.builder()
                .error(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler({MailSendingException.class, FileUploadException.class})
    public ResponseEntity<ExceptionResponse> handleException(MessagingException e) {
        ExceptionResponse response =
                ExceptionResponse.builder()
                        .error(e.getMessage())
                        .build();
        return ResponseEntity.internalServerError()
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        ExceptionResponse response =
                ExceptionResponse.builder()
                        .error(e.getMessage())
                        .build();
        return ResponseEntity.internalServerError()
                .body(response);
    }

}
