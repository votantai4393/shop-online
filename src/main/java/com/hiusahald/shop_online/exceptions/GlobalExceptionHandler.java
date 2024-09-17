package com.hiusahald.shop_online.exceptions;

import com.hiusahald.shop_online.dto.response.ExceptionResponse;
import com.hiusahald.shop_online.constants.BusinessError;
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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException e) {
        ExceptionResponse response = ExceptionResponse.builder()
                .businessCode(BusinessError.BAD_CREDENTIAL.getCode())
                .businessMessage(BusinessError.BAD_CREDENTIAL.getMessage())
                .error(e.getMessage())
                .build();
        return ResponseEntity.badRequest()
                .body(response);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException e) {
        ExceptionResponse response = ExceptionResponse.builder()
                .businessCode(BusinessError.LOCKED_ACCOUNT.getCode())
                .businessMessage(BusinessError.LOCKED_ACCOUNT.getMessage())
                .error(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(response);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException e) {
        ExceptionResponse response = ExceptionResponse.builder()
                .businessCode(BusinessError.DISABLE_ACCOUNT.getCode())
                .businessMessage(BusinessError.DISABLE_ACCOUNT.getMessage())
                .error(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(response);
    }

    @ExceptionHandler(MailSendingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException e) {
        ExceptionResponse response =
                ExceptionResponse.builder()
                        .error(e.getMessage())
                        .build();
        return ResponseEntity.internalServerError()
                .body(response);
    }

    @ExceptionHandler(FileUploadingException.class)
    public ResponseEntity<ExceptionResponse> handleException(FileUploadingException e) {
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
