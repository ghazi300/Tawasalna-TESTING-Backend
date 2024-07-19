package com.tawasalna.auth.exceptions;

import com.tawasalna.shared.exceptions.InvalidEntityBaseException;

public class InvalidSignupRequestException extends InvalidEntityBaseException {
    public InvalidSignupRequestException(String id, String cause) {
        super("signup staff request", id, cause);
    }
}
