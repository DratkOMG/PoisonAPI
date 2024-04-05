package com.example.poisontest.dtos.user;

import com.example.poisontest.models.House;
import com.example.poisontest.models.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author DratkOMG
 * @created 14:56 - 04/04/2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String username;

    private Integer age;

    private List<Long> ownerHouses;

    private List<Long> livingHouses;

    public static UserDto from(Users users) {
        List<Long> ownerHouseIdsList = users.getOwnerHouses().stream().map(House::getHouseId).toList();
        List<Long> livingHouseIdsList = users.getLivingHouses().stream().map(House::getHouseId).toList();

        return UserDto.builder()
                .id(users.getUserId())
                .username(users.getUsername())
                .age(users.getAge())
                .ownerHouses(ownerHouseIdsList)
                .livingHouses(livingHouseIdsList)
                .build();
    }

    public static List<UserDto> from(List<Users> users) {
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }
}
