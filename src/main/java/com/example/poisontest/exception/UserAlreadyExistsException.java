package com.example.poisontest.exception;

/**
 * @author DratkOMG
 * @created 17:22 - 05/04/2024
 */
public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
