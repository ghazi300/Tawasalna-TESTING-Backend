package com.ipactconsult.tawasalnabackendapp.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum BusinessErrorCodes {;
    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
