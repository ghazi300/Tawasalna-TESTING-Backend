package com.tawasalna.shared.advice;


import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.InvalidUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InvalidUserAdvice extends InvalidEntityControllerAdvice<InvalidUserException> {

    @ExceptionHandler(InvalidUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> invalidUserHandle(InvalidUserException ex) {
        return super.invalidEntityHandle(ex);
    }
}
