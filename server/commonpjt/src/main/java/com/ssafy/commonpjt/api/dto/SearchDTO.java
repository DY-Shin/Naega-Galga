package com.ssafy.commonpjt.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchDTO {

    private int buildingIndex;

    private String buildingAddress;
    private String buildingRoadAddress;
    private String buildingName;
    private String buildingElevator;
    private String buildingAnimal;

    private int productIndex;

    private String productDetail;
    private String productFloor;
    private String productType;
    private String productPrice;
    private int productManageCost;
    private String productSize;
    private String productRooms;
    private String productDirection;
    private String productPhoto;
    private String productAnimal;

    private int optionIndex;

    private boolean optionsAirConditioner;
    private boolean optionFridge;
    private boolean optionWashingMachine;
    private boolean optionGasStove;
    private boolean optionInduction;
    private boolean optionMicroWave;
    private boolean optionDesk;
    private boolean optionWifi;
    private boolean optionCloset;
    private boolean optionBed;
}
