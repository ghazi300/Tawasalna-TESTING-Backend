package com.tawasalna.shared.advice;

import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.StorageFileNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class InvalidFileAdvice extends InvalidEntityControllerAdvice<StorageFileNotFoundException> {

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<ApiResponse> invalidFileHandle(StorageFileNotFoundException ex) {
        return super.invalidEntityHandle(ex);
    }
}
