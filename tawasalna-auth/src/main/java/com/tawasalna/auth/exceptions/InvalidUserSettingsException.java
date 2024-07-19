package com.tawasalna.auth.exceptions;

import com.tawasalna.shared.exceptions.InvalidEntityBaseException;

public class InvalidUserSettingsException extends InvalidEntityBaseException {

    public InvalidUserSettingsException(String id, String cause) {
        super("userSettings", id, cause);
    }
}
