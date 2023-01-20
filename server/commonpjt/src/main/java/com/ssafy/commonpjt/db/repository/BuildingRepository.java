package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
    List<Building> findByBuildingAddressOrBuildingRoadAddress(String buildingAddress, String buildingRoadAddress);

}