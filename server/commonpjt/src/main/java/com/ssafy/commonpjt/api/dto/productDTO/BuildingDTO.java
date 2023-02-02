package com.ssafy.commonpjt.api.dto.productDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BuildingDTO {
    private int buildingParking;
    private String buildingRoadAddress;
    private String buildingJibunAddress;
    private String buildingName;
    private int buildingElevator;
}
