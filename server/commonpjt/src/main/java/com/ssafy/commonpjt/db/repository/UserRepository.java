package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByUserId(String username);
    boolean existsByUserId(String userId);
}