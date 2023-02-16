package com.ssafy.commonpjt.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.commonpjt.api.dto.productDTO.BuildingDTO;
import com.ssafy.commonpjt.api.dto.productDTO.OptionsDTO;
import com.ssafy.commonpjt.api.dto.productDTO.ProductDTO;
import com.ssafy.commonpjt.api.dto.productDTO.ProductDetailDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProductService {
    boolean addProduct(List<MultipartFile> fileList, ProductDTO productDTO, BuildingDTO buildingDTO, OptionsDTO optionsDTO) throws Exception;

    ProductDetailDTO detailProduct(int productIndex) throws JsonProcessingException;

    int updateProduct(ProductDTO productDTO, BuildingDTO buildingDTO, OptionsDTO optionsDTO);

    boolean deleteProduct(int productIndex) throws Exception;
}
