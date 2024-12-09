package org.project.ebankify_security.exception;

public class EntityCRUDFailedException extends RuntimeException {
    public EntityCRUDFailedException(String message) {
        super(message);
    }
}
