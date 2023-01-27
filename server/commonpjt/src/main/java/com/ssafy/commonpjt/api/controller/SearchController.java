package com.ssafy.commonpjt.api.controller;

import com.ssafy.commonpjt.api.dto.searchDTO.DetailSearchDTO;
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
    /**
     * 기능설명 부탁
     * @param address
     * @return
     */

    @GetMapping("")
    public ResponseEntity<?> searchProduct(@RequestParam("address") String address) {
        List<?> searchResult = searchService.searchProduct(address);
        return ResponseEntity.ok(searchResult);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> detailProduct(@PathVariable("id") int id) {
        DetailSearchDTO productInfo = searchService.detailProduct(id);
        return ResponseEntity.ok(productInfo);
    }
}
