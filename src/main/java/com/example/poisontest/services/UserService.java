package com.example.poisontest.services;

import com.example.poisontest.dtos.user.CreateOrUpdateUserDto;
import com.example.poisontest.dtos.user.UserDto;
import com.example.poisontest.models.Users;

import java.util.List;
import java.util.Map;

/**
 * @author DratkOMG
 * @created 14:07 - 04/04/2024
 */
public interface UserService {
    Users getUserById(Long userId);

    UserDto getUserDto(Long userId);

    Map<String, String> addUser(CreateOrUpdateUserDto createOrUpdateUserDto);

    UserDto updateUser(CreateOrUpdateUserDto createOrUpdateUserDto, Long userId);

    void deleteUser(Long userId);

    Users getUserByUsername(String username);

    List<Users> getAllUserByIds(List<Long> userIds);

    List<UserDto> getListUserDto();

    UserDto getMyProfile();

    Users getAuthenticationUser();
}
