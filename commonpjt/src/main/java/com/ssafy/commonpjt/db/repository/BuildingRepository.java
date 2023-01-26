package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
    Building findByBuildingAddress(String buildingAddress);

    Building findByBuildingIndex(int buildingIndex);
}
