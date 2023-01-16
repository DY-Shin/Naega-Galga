package com.ssafy.commonpjt.api.controller;

import com.ssafy.commonpjt.api.dto.ProductDto;
import com.ssafy.commonpjt.api.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    @PostMapping
    public void add(@RequestBody ProductDto productDto) throws Exception {
        ResponseEntity.status(HttpStatus.ACCEPTED).body(productDto);
        productService.addProduct(productDto);
    }


    @GetMapping("/{id}")
    public void detail(@PathVariable int id) throws Exception {
        ResponseEntity.status(HttpStatus.ACCEPTED).body(id);
        productService.detailProduct(id);
    }

    @PutMapping
    public void update(@RequestBody Map<String, Object> putData) throws Exception {
        productService.updateProduct(putData);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) throws Exception {
        productService.deleteProduct(id);
    }

}
