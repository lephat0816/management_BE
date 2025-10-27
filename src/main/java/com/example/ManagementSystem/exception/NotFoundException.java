package com.example.ManagementSystem.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String massage) {
        super(massage);
    }
}
