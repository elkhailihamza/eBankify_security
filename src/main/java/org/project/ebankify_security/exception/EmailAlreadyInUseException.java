package org.project.ebankify_security.exception;

public class EmailAlreadyInUseException extends DataConflictException {
    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}
