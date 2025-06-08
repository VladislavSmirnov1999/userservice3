package com.example.userservice3.service;

import com.example.userservice3.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User save(User user);

    User update(Long id, User userForUpdate);

    Optional<User> findById(Long id);

    List<User> findAll();

    void deleteById(Long id);
}
