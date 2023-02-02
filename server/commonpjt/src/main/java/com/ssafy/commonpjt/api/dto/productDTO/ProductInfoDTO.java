package com.ssafy.commonpjt.api.dto.productDTO;

import com.ssafy.commonpjt.db.entity.Building;
import com.ssafy.commonpjt.db.entity.Options;
import com.ssafy.commonpjt.db.entity.Product;
import com.ssafy.commonpjt.db.entity.User;
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
    private String productPhoto;

    @Builder
    public ProductInfoDTO(Product product) {
        this.productIndex = product.getProductIndex();
        this.productFloor = product.getProductFloor();
        this.productType = product.getProductType();
        this.productPrice = product.getProductPrice();
        this.productManageCost = product.getProductManageCost();
        this.productSize = product.getProductSize();
        this.productPhoto = product.getProductPhoto();
    }
}