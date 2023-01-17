package com.ssafy.commonpjt.repository;

import com.ssafy.commonpjt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String username);
    boolean existsByUserId(String userId);
}