package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
    @Query("select b.buildingIndex from Building b where b.buildingAddress like ?1% and b.buildingRoadAddress like ?2%")
    List<Integer> findBuildingIndexByBuildingAddressStartingWithAndBuildingRoadAddressStartingWith(String buildingAddress, String buildingRoadAddress);

    List<Building> findAll();

    Building findByBuildingAddress(String buildingAddress);

    Building findByBuildingIndex(int buildingIndex);

}