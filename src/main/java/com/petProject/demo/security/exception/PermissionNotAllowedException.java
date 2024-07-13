package com.petProject.demo.security.exception;

public class PermissionNotAllowedException extends RuntimeException{
    public PermissionNotAllowedException(String message) {
        super(message);
    }
}
