package org.exceptions;

public class UserOperationException extends RuntimeException {
    public UserOperationException(String message) {
        super(message);
    }
}
