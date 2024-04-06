package com.example.poisontest.dto.user;

import com.example.poisontest.model.House;
import com.example.poisontest.model.User;
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

    public static UserDto from(User user) {
        List<Long> ownerHouseIdsList = user.getOwnerHouses().stream().map(House::getHouseId).toList();
        List<Long> livingHouseIdsList = user.getLivingHouses().stream().map(House::getHouseId).toList();

        return UserDto.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .age(user.getAge())
                .ownerHouses(ownerHouseIdsList)
                .livingHouses(livingHouseIdsList)
                .build();
    }

    public static List<UserDto> from(List<User> users) {
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }
}
