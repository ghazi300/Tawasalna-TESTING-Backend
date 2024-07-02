package com.tawasalna.shared.exceptions;

public class InvalidCommunityException extends InvalidEntityBaseException {

    public InvalidCommunityException(String id, String cause) {
        super("community", id, cause);
    }
}
