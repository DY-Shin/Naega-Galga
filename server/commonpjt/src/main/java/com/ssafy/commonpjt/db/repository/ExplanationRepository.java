package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.Explanation;
import com.ssafy.commonpjt.db.entity.Meeting;
import com.ssafy.commonpjt.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExplanationRepository extends JpaRepository<Explanation, Integer> {
    Explanation findByMeetingAndReserveUser(Meeting meeting, User buyer);
}
