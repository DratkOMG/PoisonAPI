package com.example.poisontest.controller;

import com.example.poisontest.dto.token.TokenDto;
import com.example.poisontest.dto.user.CreateOrUpdateUserDto;
import com.example.poisontest.dto.user.UserDto;
import com.example.poisontest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author DratkOMG
 * @created 14:05 - 04/04/2024
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser() {
        return ResponseEntity
                .ok(userService.getListUserDto());
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMyProfile() {
        return ResponseEntity
                .ok(userService.getMyProfile());
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("user-id") Long userId) {
        return ResponseEntity
                .ok(userService.getUserDto(userId));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<TokenDto> addUser(@Valid @RequestBody CreateOrUpdateUserDto createOrUpdateUserDto) {
        TokenDto addUserAndGetToken = userService.addUser(createOrUpdateUserDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(addUserAndGetToken);
    }

    @PutMapping("/{user-id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody CreateOrUpdateUserDto createOrUpdateUserDto,
                                              @PathVariable("user-id") Long userId) {
        return ResponseEntity
                .accepted()
                .body(userService.updateUser(createOrUpdateUserDto, userId));
    }

    @DeleteMapping("/{user-id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("user-id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
