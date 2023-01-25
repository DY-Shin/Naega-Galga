package com.ssafy.commonpjt.api.dto.searchDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailSearchDTO {
    private int index;
    private String addr;
    private String roadAddr;
    private String name;
    private String elevator;
    private String parking;
    private String detail;
    private String floor;
    // 계약 형태 - 월세, 전새, 매매
    private String type;
    private String price;
    private int manageCost;
    // 평수
    private String size;
    // 원룸, 투룸 등 집의 형태
    private String rooms;
    // 방향
    private String direction;
    private String photo;
    // 반려동물 동거 가능여부
    private String animal;
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
