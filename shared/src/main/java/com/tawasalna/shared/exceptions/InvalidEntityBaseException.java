package com.tawasalna.shared.exceptions;

import lombok.Getter;
import lombok.Setter;

import static java.text.MessageFormat.format;

@Getter
@Setter
public class InvalidEntityBaseException extends RuntimeException {

    protected final String type;
    protected final String id;
    protected final String problem;

    public InvalidEntityBaseException(String type, String id, String problem) {
        super(format("Invalid {0} with identifier: {1} - Cause: {2}", type, id, problem));

        this.type = type;
        this.id = id;
        this.problem = problem;
    }
}
