package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.productDTO.BuildingDTO;
import com.ssafy.commonpjt.api.dto.productDTO.OptionsDTO;
import com.ssafy.commonpjt.api.dto.productDTO.ProductDTO;

import java.util.Map;

public interface ProductService {
    int[] addProduct(int userIndex, ProductDTO productDTO, BuildingDTO buildingDTO, OptionsDTO optionsDTO) throws Exception;

    Map<String, Object> detailProduct(int productIndex);

    int updateProduct(ProductDTO productDTO, BuildingDTO buildingDTO, OptionsDTO optionsDTO);

    int deleteProduct(int productIndex);
}
