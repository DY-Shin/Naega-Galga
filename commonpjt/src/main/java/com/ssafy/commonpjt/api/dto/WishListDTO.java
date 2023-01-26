package com.ssafy.commonpjt.api.dto;

import com.ssafy.commonpjt.db.entity.Product;
import com.ssafy.commonpjt.db.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WishListDTO {
    private int wishListIndex;
    private User user;
    private Product product;
}
