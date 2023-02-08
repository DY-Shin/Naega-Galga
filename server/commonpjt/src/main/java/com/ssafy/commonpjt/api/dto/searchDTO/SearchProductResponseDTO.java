package com.ssafy.commonpjt.api.dto.searchDTO;

import com.ssafy.commonpjt.db.entity.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchProductResponseDTO {
    private int productIndex;
    private int sellerIndex;
    private String sellerName;
    private String roadAddr;
    private String photo;
    private String price;
    // 계약 유형
    private String type;
    // 집 크기
    private String size;
    // 집 형태
    private String rooms;
    private boolean presentation;

    public static SearchProductResponseDTO toDTO(Product product, boolean hasPresentation) {
        return SearchProductResponseDTO.builder()
                .productIndex(product.getProductIndex())
                .sellerIndex(product.getProductSeller().getUserIndex())
                .sellerName(product.getProductSeller().getName())
                .roadAddr(product.getBuilding().getBuildingRoadAddress())
                .photo(product.getProductPhoto())
                .price(product.getProductPrice())
                .type(product.getProductType())
                .size(product.getProductSize())
                .rooms(product.getProductRooms())
                .presentation(hasPresentation)
                .build();

    }
}