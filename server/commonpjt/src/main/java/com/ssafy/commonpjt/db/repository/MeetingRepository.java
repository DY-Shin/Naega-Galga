package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.Meeting;
import com.ssafy.commonpjt.db.entity.Product;
import com.ssafy.commonpjt.db.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    Integer countByProduct(Product product);

    Meeting findByProductAndOwner(Product product, User owner);

    Optional<Meeting> findByProduct(Product product);
}