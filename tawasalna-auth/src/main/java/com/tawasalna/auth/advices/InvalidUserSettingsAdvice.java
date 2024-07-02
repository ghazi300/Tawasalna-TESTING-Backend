package com.tawasalna.auth.advices;

import com.tawasalna.auth.exceptions.InvalidUserSettingsException;
import com.tawasalna.shared.advice.InvalidEntityControllerAdvice;
import com.tawasalna.shared.dtos.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class InvalidUserSettingsAdvice extends InvalidEntityControllerAdvice<InvalidUserSettingsException> {

    @ExceptionHandler(InvalidUserSettingsException.class)
    public ResponseEntity<ApiResponse> invalidUserSettings(InvalidUserSettingsException ex) {
        return super.invalidEntityHandle(ex);
    }
}
