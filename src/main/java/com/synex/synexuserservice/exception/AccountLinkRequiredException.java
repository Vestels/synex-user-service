package com.synex.synexuserservice.exception;

public class AccountLinkRequiredException extends RuntimeException {
    public AccountLinkRequiredException(String message) {
        super(message);
    }
}
