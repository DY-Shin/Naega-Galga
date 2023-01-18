package com.ssafy.commonpjt.api.controller;

import com.ssafy.commonpjt.api.dto.KakaoAddressDTO;
import com.ssafy.commonpjt.api.service.SearchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@Slf4j
public class SearchController {
    @Autowired
    private SearchServiceImpl searchService;


    private static final String API_SERVER_HOST = "https://dapi.kakao.com/v2/local/search/address.json";

    @GetMapping("/{address}")
    public ResponseEntity<?> searchProduct(@PathVariable("address") String address) {
        log.info(address);
        if(address.equals("")) {
        }
        List<?> list = searchService.searchProduct(address);

        return ResponseEntity.ok(list);
    }

//    @GetMapping("/search/detail")
//    public ResponseEntity<?> detailProduct() {
//        List<?> productInfo = searchService.detailProduct();
//        return ResponseEntity.ok(?);
//    }


}
