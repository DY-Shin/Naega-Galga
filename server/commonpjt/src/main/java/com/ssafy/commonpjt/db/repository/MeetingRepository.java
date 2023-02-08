package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.Meeting;
import com.ssafy.commonpjt.db.entity.Product;
import com.ssafy.commonpjt.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;

import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    boolean existsByProduct(Product product);

    @Query("select m from Meeting m where (m.owner in (?1, ?2) or m.guest in (?1, ?2)) and m.reserveAt = ?3")
    Integer existsMeeting(User user, User op, Timestamp reserveAt);

    Meeting findByProductAndOwner(Product product, User owner);

    Optional<Meeting> findByProduct(Product product);
}