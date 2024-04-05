package com.example.poisontest.dtos.house;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DratkOMG
 * @created 19:38 - 05/04/2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateHouseDto {

    @NotBlank(message = "Address can't be blank")
    private String address;

    private Long ownerId;

}
