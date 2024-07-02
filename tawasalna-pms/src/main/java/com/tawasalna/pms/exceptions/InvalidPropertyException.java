package com.tawasalna.pms.exceptions;

import com.tawasalna.shared.exceptions.InvalidEntityBaseException;

public class InvalidPropertyException extends InvalidEntityBaseException {
    public InvalidPropertyException(String id, String cause) {
        super("property", id, cause);
    }
}
