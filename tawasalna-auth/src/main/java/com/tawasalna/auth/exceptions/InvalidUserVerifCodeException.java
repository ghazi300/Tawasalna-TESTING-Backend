package com.tawasalna.auth.exceptions;

import com.tawasalna.shared.exceptions.InvalidEntityBaseException;

public class InvalidUserVerifCodeException extends InvalidEntityBaseException {

    public InvalidUserVerifCodeException(String id, String cause) {
        super("verifCode", id, cause);
    }
}
