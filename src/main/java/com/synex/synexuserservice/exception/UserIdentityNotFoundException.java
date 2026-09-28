package com.synex.synexuserservice.exception;

public class UserIdentityNotFoundException extends RuntimeException {
    public UserIdentityNotFoundException(String message) {
        super(message);
    }
}
