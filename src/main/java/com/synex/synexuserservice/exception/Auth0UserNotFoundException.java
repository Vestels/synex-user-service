package com.synex.synexuserservice.exception;

public class Auth0UserNotFoundException extends RuntimeException {
    public Auth0UserNotFoundException(String message) {
        super(message);
    }
}
