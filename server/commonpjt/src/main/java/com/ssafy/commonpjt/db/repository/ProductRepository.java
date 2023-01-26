package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByProductDetailAndBuildingBuildingAddress(String productDetail, String buildingAddress);

    @Transactional
    void deleteProductByProductIndex(int productIndex);

    Product findByProductIndex(int productIndex);
}
