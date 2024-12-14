package com.example.sso.exception;


public class ResourceNotFoundException extends RuntimeException {

    // Constructor that accepts a custom message
    public ResourceNotFoundException(String message) {
        super(message);
    }
}