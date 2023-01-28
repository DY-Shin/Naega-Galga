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
}
