package com.petProject.demo.security.exception;

public class UnexpectedUserRoleException extends RuntimeException {
    public UnexpectedUserRoleException(String message) {
        super(message);
    }
}
