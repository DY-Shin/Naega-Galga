package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.KakaoAddressDTO;

import java.util.List;

public interface SearchService {
    List<?> searchProduct(String address);

//    List<?> detailProduct();
}
