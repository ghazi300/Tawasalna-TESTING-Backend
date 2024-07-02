package com.tawasalna.shared.advice;

import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class EntityNotFoundControllerAdvice<T extends EntityNotFoundException> extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse> entityNotFoundHandle(T ex) {
        return new ResponseEntity<>(
                ApiResponse.ofError(
                        ex.getProblem(),
                        404
                ),
                HttpStatus.NOT_FOUND
        );
    }
}
