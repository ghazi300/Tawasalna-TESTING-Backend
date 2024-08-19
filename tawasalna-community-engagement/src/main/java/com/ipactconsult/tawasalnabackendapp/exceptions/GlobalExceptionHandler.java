package com.ipactconsult.tawasalnabackendapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp)
    {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ExceptionResponse.builder()

                        .error(exp.getMessage())
                        .build()
        );


    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse>handleException(MethodArgumentNotValidException exp)
    {
        Set<String> errors=new HashSet<>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var errorMessage= error.getDefaultMessage();
                    errors.add(errorMessage);
                });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ExceptionResponse.builder()

                        .validationErrors(errors)
                        .build()
        );


    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse>handleException(Exception exp)
    {
        //log the exception
        exp.printStackTrace();


        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ExceptionResponse.builder()
                        .businessErrorDescription("Internal error , contact the admin")

                        .error(exp.getMessage())
                        .build()
        );


    }

    @ExceptionHandler(EquipmentNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(EquipmentNotFoundException exp) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ExceptionResponse.builder()
                        .error(exp.getMessage())
                        .build()
        );
    }
}
