package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

    boolean existsByUserId(String userId);

    @Modifying
    @Query(value = "set FOREIGN_KEY_CHECKS = 0;", nativeQuery = true)
    @Transactional
    public void foreignKeyDelete();

//    @Modifying
//    @Query("delete from User where userId = :userId")
//    @Transactional
//    public void delete(@Param("userId") String userId);

    @Modifying
    @Query(value = "set FOREIGN_KEY_CHECKS = 1;", nativeQuery = true)
    @Transactional
    public void foreignKeyCheck();
}