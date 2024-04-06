package com.example.poisontest.services.impl;

import com.example.poisontest.dtos.user.CreateOrUpdateUserDto;
import com.example.poisontest.dtos.user.UserDto;
import com.example.poisontest.exceptions.NotFoundException;
import com.example.poisontest.exceptions.UserAlreadyExistsException;
import com.example.poisontest.models.User;
import com.example.poisontest.repositories.UserRepository;
import com.example.poisontest.security.utils.JwtUtil;
import com.example.poisontest.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.example.poisontest.dtos.user.UserDto.from;

/**
 * @author DratkOMG
 * @created 14:09 - 04/04/2024
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    @Override
    public User getUserById(Long userId) {
        return getUserByIdOrThrow(userId);
    }

    @Override
    public UserDto getUserDto(Long userId) {
        User user = getUserById(userId);

        return from(user);
    }

    @Override
    public Map<String, String> addUser(CreateOrUpdateUserDto createOrUpdateUserDto) {
        if (usernameExists(createOrUpdateUserDto.getUsername())) {
            throw new UserAlreadyExistsException(createOrUpdateUserDto.getUsername() + " username already exists");
        }

        User user = User.builder()
                .username(createOrUpdateUserDto.getUsername())
                .password(createOrUpdateUserDto.getPassword())
                .age(createOrUpdateUserDto.getAge())
                .build();

        userRepository.save(user);

        Map<String, String> token = jwtUtil.generateTokens(createOrUpdateUserDto.getUsername());

        return token;
    }

    @Override
    public UserDto updateUser(CreateOrUpdateUserDto createOrUpdateUserDto, Long userId) {
        User user = getUserById(userId);

        if (usernameExists(createOrUpdateUserDto.getUsername())) {
            throw new UserAlreadyExistsException(createOrUpdateUserDto.getUsername() + " username already exists");
        }

        String username = createOrUpdateUserDto.getUsername();
        String password = createOrUpdateUserDto.getPassword();
        Integer age = createOrUpdateUserDto.getAge();

        if (username != null && !username.isBlank()) {
            user.setUsername(username);
        }
        if (password != null && !password.isBlank()) {
            user.setPassword(password);
        }
        if (age != null && age > 17) {
            user.setAge(age);
        }

        userRepository.save(user);

        return from(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundException("User " + username + " not found"));
    }

    @Override
    public List<User> getAllUserByIds(List<Long> userIds) {
        return userRepository.findAllById(userIds);

    }

    @Override
    public List<UserDto> getListUserDto() {
        List<User> userList = userRepository.findAll();

        return from(userList);
    }

    @Override
    public UserDto getMyProfile() {
        return from(getAuthenticationUser());
    }

    private User getUserByIdOrThrow(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User " + userId + " not found"));
    }

    private boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public User getAuthenticationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return getUserByUsername(username);
    }

}
