package com.tawasalna.business.exceptions;

import com.tawasalna.shared.exceptions.InvalidEntityBaseException;

public class InvalidServiceException extends InvalidEntityBaseException {

    public InvalidServiceException(String id, String cause) {
        super("service", id, cause);
    }
}
