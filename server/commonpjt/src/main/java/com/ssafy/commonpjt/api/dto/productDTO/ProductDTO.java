package com.ssafy.commonpjt.api.dto.productDTO;

import com.ssafy.commonpjt.db.entity.Building;
import com.ssafy.commonpjt.db.entity.Options;
import com.ssafy.commonpjt.db.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDTO {
    private String productType;
    private String productPrice;
    private int productManageCost;
    private String productSize;
    private String productDirection;
    private String productFloor;
    private String productRooms;
    private String productAnimal;
    private String productDetail;
}
