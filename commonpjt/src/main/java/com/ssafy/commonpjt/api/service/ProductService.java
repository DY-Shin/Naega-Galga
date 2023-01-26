package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.BuildingDTO;
import com.ssafy.commonpjt.api.dto.OptionDTO;
import com.ssafy.commonpjt.api.dto.ProductDTO;

import java.util.Map;

public interface ProductService {
    int[] addProduct(int userIndex, ProductDTO productDTO, BuildingDTO buildingDTO, OptionDTO optionDTO) throws Exception;

    Map<String, Object> detailProduct(int productIndex);

    int updateProduct(ProductDTO productDTO, BuildingDTO buildingDTO, OptionDTO optionDTO);

    int deleteProduct(int productIndex);

    int addWishList(int userIndex, int productIndex);

//    int deleteWishList(int wishListIndex);
}
