package com.example.userservice3.dto;

import com.example.userservice3.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest dto) {
        if (dto == null) {
            return null;
        }
        return new User(dto.getName(), dto.getEmail(), dto.getAge());
    }

    public UserResponse toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getAge(), user.getCreatedAt());
    }
}
