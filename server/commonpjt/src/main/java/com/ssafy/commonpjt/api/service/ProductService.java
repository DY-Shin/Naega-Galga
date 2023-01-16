package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.ProductDto;

import java.util.Map;

public interface ProductService {

    public boolean addProduct(ProductDto productDto) throws Exception;

    public boolean detailProduct(int productNo) throws Exception;

    public boolean updateProduct(Map<String, Object> putData) throws Exception;

    public boolean deleteProduct(int productNo) throws Exception;
}
