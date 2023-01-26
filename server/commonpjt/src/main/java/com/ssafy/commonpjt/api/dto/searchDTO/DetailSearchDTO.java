package com.ssafy.commonpjt.api.dto.searchDTO;

import com.ssafy.commonpjt.db.entity.Building;
import com.ssafy.commonpjt.db.entity.Options;
import com.ssafy.commonpjt.db.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class DetailSearchDTO {
    private int index;
    private int seller;
    private String addr;
    private String roadAddr;
    private String name;
    private boolean elevator;
    private boolean parking;
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

    public static DetailSearchDTO toDTO(Building building, Product product, Options options) {
        return DetailSearchDTO.builder()
                .index(product.getProductIndex())
                .seller(product.getProductSeller().getUserIndex())
                .addr(building.getBuildingAddress())
                .roadAddr(building.getBuildingRoadAddress())
                .price(product.getProductPrice())
                .photo(product.getProductPhoto())
                .airConditioner(options.isOptionAirConditioner())
                .fridge(options.isOptionFridge())
                .washingMachine(options.isOptionWashingMachine())
                .gasStove(options.isOptionGasStove())
                .induction(options.isOptionInduction())
                .microWave(options.isOptionMicroWave())
                .desk(options.isOptionDesk())
                .wifi(options.isOptionWifi())
                .closet(options.isOptionCloset())
                .bed(options.isOptionBed())
                .build();
    }

    public static DetailSearchDTO to(Product product) {
        return DetailSearchDTO.builder()
                .index(product.getProductIndex())
                .seller(product.getProductSeller().getUserIndex())
                .addr(product.getBuilding().getBuildingAddress())
                .roadAddr(product.getBuilding().getBuildingRoadAddress())
                .name(product.getBuilding().getBuildingName())
                .elevator(product.getBuilding().isBuildingElevator())
                .parking(product.getBuilding().isBuildingParking())
                .detail(product.getProductDetail())
                .floor(product.getProductFloor())
                .type(product.getProductType())
                .price(product.getProductPrice())
                .manageCost(product.getProductManageCost())
                .size(product.getProductSize())
                .rooms(product.getProductRooms())
                .direction(product.getProductDirection())
                .photo(product.getProductPhoto())
                .animal(product.getProductAnimal())
                .airConditioner(product.getOptions().isOptionAirConditioner())
                .fridge(product.getOptions().isOptionFridge())
                .washingMachine(product.getOptions().isOptionWashingMachine())
                .gasStove(product.getOptions().isOptionGasStove())
                .induction(product.getOptions().isOptionInduction())
                .microWave(product.getOptions().isOptionMicroWave())
                .desk(product.getOptions().isOptionDesk())
                .wifi(product.getOptions().isOptionWifi())
                .closet(product.getOptions().isOptionCloset())
                .bed(product.getOptions().isOptionBed())
                .build();
    }
}
