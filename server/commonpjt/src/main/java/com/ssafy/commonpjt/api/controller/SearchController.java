package com.ssafy.commonpjt.api.controller;

import com.ssafy.commonpjt.api.service.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SearchController {
    @Autowired
    private SearchServiceImpl searchService;

    /**
     * @param
     * @return
     */

    @GetMapping("/search")
    public ResponseEntity<T> searchProduct() {
        List<T> productList = searchService.searchProduct();

        return ResponseEntity.ok(T);
    }

    @GetMapping("/search/detail")
    public ResponseEntity<T> detailProduct() {
        List<T> productInfo = searchService.detailProduct();
        return ResponseEntity.ok(T);
    }

}
