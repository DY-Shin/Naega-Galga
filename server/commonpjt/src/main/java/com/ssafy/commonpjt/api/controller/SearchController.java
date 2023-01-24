package com.ssafy.commonpjt.api.controller;

import com.ssafy.commonpjt.api.dto.KakaoAddressDTO;
import com.ssafy.commonpjt.api.service.SearchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@Slf4j
public class SearchController {
    @Autowired
    private SearchServiceImpl searchService;


    private static final String API_SERVER_HOST = "https://dapi.kakao.com/v2/local/search/address.json";

    /**
     * 기능설명 부탁
     * @param address
     * @return
     */

    @GetMapping("")
    public ResponseEntity<?> searchProduct(@RequestParam("address") String address) {
        log.info(address);
        System.out.println(address);
//        if(address == null) {
//            return ResponseEntity.ok("Empty");
//        }
//        // 아무것도 없을 시 404 에러 - 카카오에서는 400 에러
//        if(address.equals("")) {
//            return ResponseEntity.ok("Empty");
//        }
        List<?> list = searchService.searchProduct(address);

        return ResponseEntity.ok(list);
    }

//    @GetMapping("/{address}")
//    public String searchProduct(@PathVariable("address") String address) {
//        log.info(address);
//        if(address.equals("")) {
//        }
//        return searchService.searchProduct(address);
//    }

//    @GetMapping("/search/detail")
//    public ResponseEntity<?> detailProduct() {
//        List<?> productInfo = searchService.detailProduct();
//        return ResponseEntity.ok(?);
//    }


}
