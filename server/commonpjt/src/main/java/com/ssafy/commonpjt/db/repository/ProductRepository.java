package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>{
//    매물 찾기
    List<Product> findAll();
    @Query("select p " +
            "from Product p join fetch p.building b join fetch p.options op join fetch p.productSeller s " +
            "where p.building.buildingIndex = ?1")
    List<Product> productFetchJoin(Integer buildingIndex);

    @EntityGraph(attributePaths = {"building","options"}, type = EntityGraph.EntityGraphType.LOAD)
    Product findByProductIndex(Integer productIndex);
}