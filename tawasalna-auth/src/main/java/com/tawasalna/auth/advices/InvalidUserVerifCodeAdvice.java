package com.tawasalna.auth.advices;

import com.tawasalna.auth.exceptions.InvalidUserVerifCodeException;
import com.tawasalna.shared.advice.InvalidEntityControllerAdvice;
import com.tawasalna.shared.dtos.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class InvalidUserVerifCodeAdvice extends InvalidEntityControllerAdvice<InvalidUserVerifCodeException> {

    @ExceptionHandler(InvalidUserVerifCodeException.class)
    public ResponseEntity<ApiResponse> invalidUserSettings(InvalidUserVerifCodeException ex) {
        return super.invalidEntityHandle(ex);
    }
}
