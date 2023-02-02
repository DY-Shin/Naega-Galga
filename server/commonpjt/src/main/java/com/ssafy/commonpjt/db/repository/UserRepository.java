package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserId(String userId);
    List<User> findAllByName(String name);

    User findByUserIndex(int userIndex);

    boolean existsByUserId(String userId);
}