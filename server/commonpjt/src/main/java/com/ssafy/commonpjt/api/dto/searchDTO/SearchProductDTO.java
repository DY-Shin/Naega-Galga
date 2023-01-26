package com.ssafy.commonpjt.api.dto.searchDTO;

import com.ssafy.commonpjt.db.entity.Product;
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

    public static SearchProductDTO toDTO(Product product) {
        return SearchProductDTO.builder()
                .index(product.getProductIndex())
                .addr(product.getBuilding().getBuildingAddress())
                .roadAddr(product.getBuilding().getBuildingRoadAddress())
                .price(product.getProductPrice())
                .photo(product.getProductPhoto())
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