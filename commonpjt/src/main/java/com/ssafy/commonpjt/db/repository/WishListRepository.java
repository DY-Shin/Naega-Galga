package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Integer> {
//    WishList findByWishListIndex(int wishListIndex);

    WishList findByUserUserIndexAndProductProductIndex(int userIndex, int productIndex);
}
