package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.productDTO.BuildingDTO;
import com.ssafy.commonpjt.api.dto.productDTO.OptionsDTO;
import com.ssafy.commonpjt.api.dto.productDTO.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProductService {
    boolean addProduct(int userIndex, List<MultipartFile> fileList, ProductDTO productDTO, BuildingDTO buildingDTO, OptionsDTO optionsDTO) throws Exception;

    Map<String, Object> detailProduct(int productIndex);

    int updateProduct(ProductDTO productDTO, BuildingDTO buildingDTO, OptionsDTO optionsDTO);

    int deleteProduct(int productIndex);
}
