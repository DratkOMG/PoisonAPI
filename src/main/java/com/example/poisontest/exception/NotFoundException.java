package com.example.poisontest.exception;

/**
 * @author DratkOMG
 * @created 14:36 - 04/04/2024
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
