package com.ssafy.commonpjt.api.dto.searchDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchProductDTO {
    private int index;
    private String addr;
    private String roadAddr;
    private String price;
    private String photo;
    private boolean airConditioner;
    private boolean fridge;
    private boolean washingMachine;
    private boolean gasStove;
    private boolean induction;
    private boolean microWave;
    private boolean desk;
    private boolean wifi;
    private boolean closet;
    private boolean bed;
}