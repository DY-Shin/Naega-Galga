package com.ssafy.commonpjt.api.dto.product;

import com.ssafy.commonpjt.db.entity.Building;
import com.ssafy.commonpjt.db.entity.Options;
import com.ssafy.commonpjt.db.entity.Product;
import com.ssafy.commonpjt.db.entity.User;
import lombok.*;

@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class ProductDTO {
    private int productIndex;
    private Building building;
    private Options options;
    private User user;
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

//    @Builder
//    public ProductDTO(Product product) {
//        this.productIndex = product.getProductIndex();
//        this.building = Building.builder().build();
//        this.options = Options.builder().build();
//        this.user = User.builder().build();
//        this.productDetail = product.getProductDetail();
//        this.productFloor = product.getProductFloor();
//        this.productType = product.getProductType();
//        this.productPrice = product.getProductPrice();
//        this.productManageCost = product.getProductManageCost();
//        this.productSize = product.getProductSize();
//        this.productRooms = product.getProductRooms();
//        this.productDirection = product.getProductDirection();
//        this.productPhoto = product.getProductPhoto();
//        this.productAnimal = product.getProductPhoto();
//    }
}