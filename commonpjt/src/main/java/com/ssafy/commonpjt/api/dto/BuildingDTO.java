package com.ssafy.commonpjt.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BuildingDTO {
    private int buildingIndex;
    private String buildingAddress;
    private String buildingRoadAddress;
    private String buildingName;
    private int buildingElevator;
    private int buildingParking;
}
