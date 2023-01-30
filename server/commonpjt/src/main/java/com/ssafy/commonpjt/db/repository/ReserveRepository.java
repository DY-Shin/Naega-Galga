package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReserveRepository extends JpaRepository<Reserve, Integer> {
    Optional<Reserve> findByChatIndex(Integer chatIndex);
}
