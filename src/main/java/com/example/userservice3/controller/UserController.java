package com.example.userservice3.controller;

import com.example.userservice3.dto.UserMapper;
import com.example.userservice3.dto.UserRequest;
import com.example.userservice3.dto.UserResponse;
import com.example.userservice3.model.User;
import com.example.userservice3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);
        User createdUser = userService.save(user);
        UserResponse userResponse = userMapper.toDto(createdUser);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        User userForUpdate = userMapper.toEntity(userRequest);
        User updatedUser = userService.update(id, userForUpdate);
        UserResponse responseDto = userMapper.toDto(updatedUser);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            User getUser = user.get();
            UserResponse responseDto = userMapper.toDto(getUser);
            return ResponseEntity.ok(responseDto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userService.findAll();
        List<UserResponse> userResponses = users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}