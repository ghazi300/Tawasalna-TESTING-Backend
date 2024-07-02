package com.tawasalna.shared.advice;

import com.tawasalna.shared.exceptions.InvalidEntityBaseException;
import com.tawasalna.shared.dtos.ApiResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class InvalidEntityControllerAdvice<T extends InvalidEntityBaseException> extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidEntityBaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ApiResponse> invalidEntityHandle(@NotNull T ex) {
        return new ResponseEntity<>(
                ApiResponse.ofError(
                        ex.getProblem(),
                        400
                ),
                HttpStatus.BAD_REQUEST
        );
    }
}
