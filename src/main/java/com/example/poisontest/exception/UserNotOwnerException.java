package com.example.poisontest.exception;

/**
 * @author DratkOMG
 * @created 17:10 - 05/04/2024
 */
public class UserNotOwnerException extends RuntimeException{

    public UserNotOwnerException(String message) {
        super(message);
    }
}
