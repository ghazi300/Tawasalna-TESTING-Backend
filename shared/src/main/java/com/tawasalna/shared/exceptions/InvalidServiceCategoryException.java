package com.tawasalna.shared.exceptions;

public class InvalidServiceCategoryException extends InvalidEntityBaseException {
    public InvalidServiceCategoryException(String id, String cause) {
        super("category", id, cause);
    }
}