package com.example.poisontest.dtos.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DratkOMG
 * @created 11:46 - 05/04/2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDto {

    private String message;

    private int status;
}
