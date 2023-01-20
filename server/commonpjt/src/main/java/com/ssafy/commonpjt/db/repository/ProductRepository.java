package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select '*' from product")
    List<Product> findProductBy();
}