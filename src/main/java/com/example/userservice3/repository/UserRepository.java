package com.example.userservice3.repository;

import com.example.userservice3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<Object> findByEmail(String mail);
}
