package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.searchDTO.DetailSearchDTO;

import java.util.List;

public interface SearchService {
    List<?> searchProduct(String address);
    DetailSearchDTO detailProduct(int id);
}
