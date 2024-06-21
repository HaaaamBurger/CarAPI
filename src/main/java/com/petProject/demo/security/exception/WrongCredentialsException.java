package com.petProject.demo.security.exception;

import javax.security.auth.login.CredentialException;

public class WrongCredentialsException extends RuntimeException {
    public WrongCredentialsException(String message) {
        super(message);
    }
}
