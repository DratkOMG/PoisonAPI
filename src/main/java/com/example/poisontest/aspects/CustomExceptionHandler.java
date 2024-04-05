package com.example.poisontest.aspects;

import com.example.poisontest.dtos.exception.ExceptionDto;
import com.example.poisontest.exceptions.NotFoundException;
import com.example.poisontest.exceptions.UserAlreadyExistsException;
import com.example.poisontest.exceptions.UserNotOwnerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author DratkOMG
 * @created 11:45 - 05/04/2024
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> handlerExceptionNotFound(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionDto.builder()
                        .message(e.getMessage())
                        .status(HttpStatus.NOT_FOUND.value())
                        .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDto> handlerException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ExceptionDto.builder()
                        .message(e.getMessage())
                        .status(HttpStatus.FORBIDDEN.value())
                        .build());
    }

    @ExceptionHandler(UserNotOwnerException.class)
    public ResponseEntity<ExceptionDto> handlerAlreadyExistException(UserNotOwnerException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ExceptionDto.builder()
                        .message(e.getMessage())
                        .status(HttpStatus.FORBIDDEN.value())
                        .build());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionDto> handlerResidentsAlreadyExistsException(UserAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ExceptionDto.builder()
                        .message(e.getMessage())
                        .status(HttpStatus.CONFLICT.value())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionDto.builder()
                        .message(e.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build());
    }
}
