package com.ssafy.commonpjt.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "building")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="building_index", nullable = false)
    private Integer buildingIndex;

    @Column(nullable = false)
    private String buildingAddress;

    @Column(nullable = false)
    private String buildingRoadAddress;

    @Column(nullable = false)
    private String buildingName;

    @Column(nullable = false)
    private String buildingElevator;

    @Column(nullable = false)
    private String buildingParking;
}