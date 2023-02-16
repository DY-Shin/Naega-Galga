package com.ssafy.commonpjt.api.dto.productDTO;

import com.ssafy.commonpjt.db.entity.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoDTO {
    private int productIndex;
    private String productFloor;
    private String productType;
    private String productPrice;
    private int productManageCost;
    private String productSize;
    private String productAddress;
    private String productPhoto;

    @Builder
    public ProductInfoDTO(Product product) {
        this.productIndex = product.getProductIndex();
        this.productFloor = product.getProductFloor();
        this.productType = product.getProductType();
        this.productPrice = product.getProductPrice();
        this.productManageCost = product.getProductManageCost();
        this.productSize = product.getProductSize();
        this.productAddress = product.getBuilding().getBuildingAddress();
        this.productPhoto = product.getProductPhoto();
    }
}