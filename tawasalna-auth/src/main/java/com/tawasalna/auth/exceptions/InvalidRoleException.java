package com.tawasalna.auth.exceptions;

import com.tawasalna.shared.exceptions.InvalidEntityBaseException;

public class InvalidRoleException extends InvalidEntityBaseException {

    public InvalidRoleException(String id, String cause) {
        super("role", id, cause);
    }
}
