package com.synex.synexuserservice.exception;

public class UserPreferencesNotFoundException extends RuntimeException {
    public UserPreferencesNotFoundException(String message) {
        super(message);
    }
}
