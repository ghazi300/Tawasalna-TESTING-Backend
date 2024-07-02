package com.tawasalna.auth.advices;

import com.tawasalna.auth.exceptions.InvalidRoleException;
import com.tawasalna.shared.advice.InvalidEntityControllerAdvice;
import com.tawasalna.shared.dtos.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class InvalidRoleAdvice extends InvalidEntityControllerAdvice<InvalidRoleException> {

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ApiResponse> invalidFileHandle(InvalidRoleException ex) {
        return super.invalidEntityHandle(ex);
    }
}
