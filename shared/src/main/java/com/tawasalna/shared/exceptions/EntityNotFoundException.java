package com.tawasalna.shared.exceptions;

public class EntityNotFoundException extends InvalidEntityBaseException {

    public EntityNotFoundException(String type, String id, String problem) {
        super(type, id, problem);
    }
}
