package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.ProductDto;

import javax.transaction.Transactional;
import java.util.Map;

public class ProductServiceImpl implements ProductService {

    @Override
    @Transactional
    public boolean addProduct(ProductDto productDto) throws Exception {


        return false;
    }

    @Override
    public boolean detailProduct(int productNo) throws Exception {
        return false;
    }

    @Override
    public boolean updateProduct(Map<String, Object> putData) throws Exception {
        return false;
    }

    @Override
    public boolean deleteProduct(int productNo) throws Exception {
        return true;
    }
}
