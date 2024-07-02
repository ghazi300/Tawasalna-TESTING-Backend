package com.tawasalna.auth.exceptions;

import com.tawasalna.shared.exceptions.InvalidEntityBaseException;

public class InvalidAssistanceException extends InvalidEntityBaseException {
    public InvalidAssistanceException(String id, String cause) {
        super("assistance", id, cause);
    }
}
