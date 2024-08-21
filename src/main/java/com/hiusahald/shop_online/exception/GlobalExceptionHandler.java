package com.hiusahald.shop_online.exception;

import com.hiusahald.shop_online.exception.exceptions.ClientRequestException;
import com.hiusahald.shop_online.exception.exceptions.ResentEmailException;
import com.hiusahald.shop_online.exception.exceptions.UserExistedException;
import jakarta.mail.MessagingException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.hiusahald.shop_online.exception.BusinessError.*;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientRequestException.class)
    public ResponseEntity<ExceptionResponse> handler(ClientRequestException e) {
        return ResponseEntity.badRequest().body(
                ExceptionResponse.builder()
                        .messageError(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(ResentEmailException.class)
    public ResponseEntity<ExceptionResponse> handler(ResentEmailException e) {
        return ResponseEntity
                .status(RESEND_EMAIL.getCode())
                .body(
                        ExceptionResponse.builder()
                                .businessMessage(RESEND_EMAIL.getMessage())
                                .messageError(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(UserExistedException.class)
    public ResponseEntity<ExceptionResponse> handler(UserExistedException e) {
        return ResponseEntity
                .status(USER_EXISTED.getCode())
                .body(
                        ExceptionResponse.builder()
                                .businessMessage(USER_EXISTED.getMessage())
                                .messageError(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handler(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(
                ExceptionResponse.builder()
                        .validationErrors(
                                e.getBindingResult()
                                        .getAllErrors()
                                        .stream()
                                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                        .collect(Collectors.toSet())
                        ).build()
        );
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handler(MessagingException e) {
        return ResponseEntity.internalServerError()
                .body(
                        ExceptionResponse.builder()
                                .businessCode(ERROR_SERVER.getCode())
                                .messageError(e.getMessage())
                                .build()
                );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handler(Exception e) {
        return ResponseEntity.internalServerError()
                .body(
                        ExceptionResponse.builder()
                                .businessCode(ERROR_SERVER.getCode())
                                .businessMessage(ERROR_SERVER.getMessage())
                                .messageError(e.getMessage())
                                .build()
                );
    }

}
