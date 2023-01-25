package com.ssafy.commonpjt;

import com.ssafy.commonpjt.api.dto.SearchDTO;
import com.ssafy.commonpjt.api.service.SearchServiceImpl;
import com.ssafy.commonpjt.db.entity.Building;
import com.ssafy.commonpjt.db.entity.Product;
import com.ssafy.commonpjt.db.repository.BuildingRepository;
import com.ssafy.commonpjt.db.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SearchTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private SearchServiceImpl searchService;

    @Test
    void contextLoads() {

//        List<Building> buildings = buildingRepository.findAll();
//        List<Product> products = productRepository.findAll();
        // List<?> list = searchService.searchProduct("구미시 송정동");
        List<Integer> buildingIndex = buildingRepository.findBuildingIndexByBuildingAddressStartingWithAndBuildingRoadAddressStartingWith("경북 구미시 송정동","");

        for (Integer idx : buildingIndex) {
            List<Product> product = new ArrayList<>();
            System.out.println(idx);
            product = productRepository.productFetchJoin(idx);

            for(Product p : product) {
                System.out.println(p.getProductIndex());
                System.out.println(p.getProductPrice());
                System.out.println(p.getBuilding().getBuildingRoadAddress());
                System.out.println(p.getOptions().isOptionBed());
            }
        }
    }

}
