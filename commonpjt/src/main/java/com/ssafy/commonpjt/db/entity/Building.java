package com.ssafy.commonpjt.db.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Building {
    @Id
    @GeneratedValue
    private int buildingIndex;

    @Column(nullable = false)
    private String buildingAddress;

    @Column(nullable = false)
    private String buildingRoadAddress;

    @Column(nullable = false)
    private String buildingName;

    @Column(nullable = false)
    private int buildingElevator;

    @Column(nullable = false)
    private int buildingParking;
}
