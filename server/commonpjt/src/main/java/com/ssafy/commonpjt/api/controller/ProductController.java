package com.ssafy.commonpjt.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.commonpjt.api.dto.productDTO.BuildingDTO;
import com.ssafy.commonpjt.api.dto.productDTO.OptionsDTO;
import com.ssafy.commonpjt.api.dto.productDTO.ProductDTO;
import com.ssafy.commonpjt.api.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> addProduct( @RequestPart(value = "productPhoto", required = false)List<MultipartFile> files,
                                         @RequestParam(value = "product")String productStr,
                                         @RequestParam(value = "building")String buildingStr,
                                         @RequestParam(value = "options")String optionsStr,
                                         @RequestParam(value = "userIndex")String userIndexStr) throws Exception {
        log.info("in addProduct");
        try{
            ObjectMapper mapper = new ObjectMapper();
            ProductDTO productDTO = mapper.readValue(productStr, ProductDTO.class);
            BuildingDTO buildingDTO = mapper.readValue(buildingStr, BuildingDTO.class);
            OptionsDTO options = mapper.readValue(optionsStr, OptionsDTO.class);
            int userIndex = Integer.parseInt(userIndexStr);

            boolean created = productService.addProduct(userIndex, files, productDTO, buildingDTO, options);

            if(created){
                return new ResponseEntity<Void>(HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<Void>(HttpStatus.CONFLICT);
            }
        }
        catch(Exception e){
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
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

//    @PutMapping
//    public ResponseEntity<Integer> updateProduct(@RequestBody Map<String, Object> updateData) throws Exception {
//        log.info("in updateProduct");
//
//        BuildingDTO buildingDTO = new BuildingDTO();
//        buildingDTO.setBuildingJibunAddress((String) updateData.get("buildingAddress"));
//        buildingDTO.setBuildingRoadAddress((String) updateData.get("buildingRoadAddress"));
//        buildingDTO.setBuildingName((String) updateData.get("buildingName"));
//        buildingDTO.setBuildingElevator((int) updateData.get("buildingElevator"));
//        buildingDTO.setBuildingParking((int) updateData.get("buildingParking"));
//        buildingDTO.setBuildingIndex((int) updateData.get("buildingIndex"));
//
//        ProductDTO productDTO = new ProductDTO();
//        productDTO.setProductIndex((int) updateData.get("productIndex"));
//        productDTO.setProductManageCost((int) updateData.get("productManageCost"));
//        productDTO.setProductAnimal((String) updateData.get("productAnimal"));
//        productDTO.setProductDetail((String) updateData.get("productDetail"));
//        productDTO.setProductDirection((String) updateData.get("productDirection"));
//        productDTO.setProductFloor((String) updateData.get("productFloor"));
//        productDTO.setProductPrice((String) updateData.get("productPrice"));
//        productDTO.setProductRooms((String) updateData.get("productRooms"));
//        productDTO.setProductSize((String) updateData.get("productSize"));
//        productDTO.setProductType((String) updateData.get("productType"));
//
//        //TODO: ProductDTO에 photo 추가하기
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
//
//        OptionsDTO optionsDTO = new OptionsDTO();
//        optionsDTO.setOptionIndex((int) updateData.get("optionIndex"));
//        optionsDTO.setOptionAirConditioner((int) updateData.get("optionAirConditioner"));
//        optionsDTO.setOptionBed((int) updateData.get("optionBed"));
//        optionsDTO.setOptionCloset((int) updateData.get("optionCloset"));
//        optionsDTO.setOptionDesk((int) updateData.get("optionDesk"));
//        optionsDTO.setOptionFridge((int) updateData.get("optionFridge"));
//        optionsDTO.setOptionGasStove((int) updateData.get("optionGasStove"));
//        optionsDTO.setOptionInduction((int) updateData.get("optionInduction"));
//        optionsDTO.setOptionMicroWave((int) updateData.get("optionMicrowave"));
//        optionsDTO.setOptionWashingMachine((int) updateData.get("optionWashingMachine"));
//        optionsDTO.setOptionWifi((int) updateData.get("optionWifi"));
//
//        int statusCode = productService.updateProduct(productDTO, buildingDTO, optionsDTO);
//
//        return ResponseEntity.status(statusCode).body(null);
//    }
//
//    @DeleteMapping("/{productIndex}")
//    public ResponseEntity<Object> deleteProduct(@PathVariable int productIndex) throws Exception {
//        int statusCode = productService.deleteProduct(productIndex);
//        return ResponseEntity.status(statusCode).body(null);
//    }

}
