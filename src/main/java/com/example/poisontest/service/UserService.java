package com.example.poisontest.service;

import com.example.poisontest.dto.token.TokenDto;
import com.example.poisontest.dto.user.CreateOrUpdateUserDto;
import com.example.poisontest.dto.user.UserDto;
import com.example.poisontest.model.User;

import java.util.List;
import java.util.Map;

/**
 * @author DratkOMG
 * @created 14:07 - 04/04/2024
 */
public interface UserService {
    User getUserById(Long userId);

    UserDto getUserDto(Long userId);

    TokenDto addUser(CreateOrUpdateUserDto createOrUpdateUserDto);

    UserDto updateUser(CreateOrUpdateUserDto createOrUpdateUserDto, Long userId);

    void deleteUser(Long userId);

    User getUserByUsername(String username);

    List<User> getAllUserByIds(List<Long> userIds);

    List<UserDto> getListUserDto();

    UserDto getMyProfile();

    User getAuthenticationUser();
}
