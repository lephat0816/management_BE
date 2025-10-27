package com.example.ManagementSystem.exception;

public class NameValueRequiredException extends RuntimeException{
    public NameValueRequiredException(String message) {
        super(message);
    }
}
