package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Integer> {
    List<WishList> findAllByUser(Integer userIndex);
}
