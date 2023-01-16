package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    public String findById(int id);
}
