package com.ssafy.commonpjt.api.controller;

import com.ssafy.commonpjt.api.dto.productDTO.BuildingDTO;
import com.ssafy.commonpjt.api.dto.productDTO.OptionsDTO;
import com.ssafy.commonpjt.api.dto.productDTO.ProductDTO;
import com.ssafy.commonpjt.api.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<int[]> addProduct(@RequestBody Map<String, Object> addData) throws Exception {
        log.info("in addProduct");

        BuildingDTO buildingDTO = new BuildingDTO();
        buildingDTO.setBuildingAddress((String) addData.get("buildingAddress"));
        buildingDTO.setBuildingRoadAddress((String) addData.get("buildingRoadAddress"));
        buildingDTO.setBuildingName((String) addData.get("buildingName"));
        buildingDTO.setBuildingElevator((int) addData.get("buildingElevator"));
        buildingDTO.setBuildingParking((int) addData.get("buildingParking"));

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductManageCost((int) addData.get("productManageCost"));
        productDTO.setProductAnimal((String) addData.get("productAnimal"));
        productDTO.setProductDetail((String) addData.get("productDetail"));
        productDTO.setProductDirection((String) addData.get("productDirection"));
        productDTO.setProductFloor((String) addData.get("productFloor"));
        productDTO.setProductPrice((String) addData.get("productPrice"));
        productDTO.setProductRooms((String) addData.get("productRooms"));
        productDTO.setProductSize((String) addData.get("productSize"));
        productDTO.setProductType((String) addData.get("productType"));

        //TODO: ProductDTO에 photo 추가하기
//        File[] file = (File[]) addData.get("productPhoto");
//        BufferedImage image = null;
//        String pathString = "C:/ssafy/workspace/PJT/common/ProductImages/" + buildingDTO.getBuildingAddress() + "/" + productDTO.getProductDetail();
//        productDTO.setProductPhoto(pathString);
//        Path path = Paths.get(pathString);
//        Files.createDirectory(path);
//        for (int i = 1; i <= file.length; i++) {
//            image = ImageIO.read(file[i - 1]);
//            String tmp = String.valueOf(i);
//            pathString = "C:/ssafy/workspace/PJT/common/ProductImages/" + buildingDTO.getBuildingAddress() + "/" + productDTO.getProductDetail() + "/" + tmp;
//            File outputFile = new File(pathString);
//            ImageIO.write(image, "png", outputFile);
//        }

        OptionsDTO optionsDTO = new OptionsDTO();
        optionsDTO.setOptionAirConditioner((int) addData.get("optionAirConditioner"));
        optionsDTO.setOptionBed((int) addData.get("optionBed"));
        optionsDTO.setOptionCloset((int) addData.get("optionCloset"));
        optionsDTO.setOptionDesk((int) addData.get("optionDesk"));
        optionsDTO.setOptionFridge((int) addData.get("optionFridge"));
        optionsDTO.setOptionGasStove((int) addData.get("optionGasStove"));
        optionsDTO.setOptionInduction((int) addData.get("optionInduction"));
        optionsDTO.setOptionMicroWave((int) addData.get("optionMicrowave"));
        optionsDTO.setOptionWashingMachine((int) addData.get("optionWashingMachine"));
        optionsDTO.setOptionWifi((int) addData.get("optionWifi"));

        int userIndex = (int) addData.get("userIndex");

        int[] output = productService.addProduct(userIndex, productDTO, buildingDTO, optionsDTO);

        if (output == null) {
            return ResponseEntity.status(409).body(null);
        } else {
            return ResponseEntity.status(201).body(output);
        }
    }

    @GetMapping("/{productIndex}")
    public ResponseEntity<Map<String, Object>> detailProduct(@PathVariable int productIndex) throws Exception {
        Map<String, Object> map = productService.detailProduct(productIndex);
        if (map == null) {
            return ResponseEntity.status(204).body(null);
        } else {
            return ResponseEntity.status(201).body(map);
        }
    }

    @PutMapping
    public ResponseEntity<Integer> updateProduct(@RequestBody Map<String, Object> updateData) throws Exception {
        log.info("in updateProduct");

        BuildingDTO buildingDTO = new BuildingDTO();
        buildingDTO.setBuildingAddress((String) updateData.get("buildingAddress"));
        buildingDTO.setBuildingRoadAddress((String) updateData.get("buildingRoadAddress"));
        buildingDTO.setBuildingName((String) updateData.get("buildingName"));
        buildingDTO.setBuildingElevator((int) updateData.get("buildingElevator"));
        buildingDTO.setBuildingParking((int) updateData.get("buildingParking"));
        buildingDTO.setBuildingIndex((int) updateData.get("buildingIndex"));

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductIndex((int) updateData.get("productIndex"));
        productDTO.setProductManageCost((int) updateData.get("productManageCost"));
        productDTO.setProductAnimal((String) updateData.get("productAnimal"));
        productDTO.setProductDetail((String) updateData.get("productDetail"));
        productDTO.setProductDirection((String) updateData.get("productDirection"));
        productDTO.setProductFloor((String) updateData.get("productFloor"));
        productDTO.setProductPrice((String) updateData.get("productPrice"));
        productDTO.setProductRooms((String) updateData.get("productRooms"));
        productDTO.setProductSize((String) updateData.get("productSize"));
        productDTO.setProductType((String) updateData.get("productType"));

        //TODO: ProductDTO에 photo 추가하기
//        File[] file = (File[]) updateData.get("productPhoto");
//        BufferedImage image = null;
//        String pathString = "C:/ssafy/workspace/PJT/common/ProductImages/" + buildingDTO.getBuildingAddress() + "/" + productDTO.getProductDetail();
//        productDTO.setProductPhoto(pathString);
//        Path path = Paths.get(pathString);
//        Files.createDirectory(path);
//        for (int i = 1; i <= file.length; i++) {
//            image = ImageIO.read(file[i - 1]);
//            String tmp = String.valueOf(i);
//            pathString = "C:/ssafy/workspace/PJT/common/ProductImages/" + buildingDTO.getBuildingAddress() + "/" + productDTO.getProductDetail() + "/" + tmp;
//            File outputFile = new File(pathString);
//            ImageIO.write(image, "png", outputFile);
//        }

        OptionsDTO optionsDTO = new OptionsDTO();
        optionsDTO.setOptionIndex((int) updateData.get("optionIndex"));
        optionsDTO.setOptionAirConditioner((int) updateData.get("optionAirConditioner"));
        optionsDTO.setOptionBed((int) updateData.get("optionBed"));
        optionsDTO.setOptionCloset((int) updateData.get("optionCloset"));
        optionsDTO.setOptionDesk((int) updateData.get("optionDesk"));
        optionsDTO.setOptionFridge((int) updateData.get("optionFridge"));
        optionsDTO.setOptionGasStove((int) updateData.get("optionGasStove"));
        optionsDTO.setOptionInduction((int) updateData.get("optionInduction"));
        optionsDTO.setOptionMicroWave((int) updateData.get("optionMicrowave"));
        optionsDTO.setOptionWashingMachine((int) updateData.get("optionWashingMachine"));
        optionsDTO.setOptionWifi((int) updateData.get("optionWifi"));

        int statusCode = productService.updateProduct(productDTO, buildingDTO, optionsDTO);

        return ResponseEntity.status(statusCode).body(null);
    }

    @DeleteMapping("/{productIndex}")
    public ResponseEntity<Object> deleteProduct(@PathVariable int productIndex) throws Exception {
        int statusCode = productService.deleteProduct(productIndex);
        return ResponseEntity.status(statusCode).body(null);
    }

}
