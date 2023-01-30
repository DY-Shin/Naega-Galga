package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.productDTO.BuildingDTO;
import com.ssafy.commonpjt.api.dto.productDTO.OptionsDTO;
import com.ssafy.commonpjt.api.dto.productDTO.ProductDTO;
import com.ssafy.commonpjt.db.entity.Building;
import com.ssafy.commonpjt.db.entity.Options;
import com.ssafy.commonpjt.db.entity.Product;
import com.ssafy.commonpjt.db.entity.User;
import com.ssafy.commonpjt.db.repository.BuildingRepository;
import com.ssafy.commonpjt.db.repository.OptionsRepository;
import com.ssafy.commonpjt.db.repository.ProductRepository;
import com.ssafy.commonpjt.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final BuildingRepository buildingRepository;
    private final OptionsRepository optionsRepository;

    private Product findProductByAddress(String productDetail, String jibunAddress){
        return productRepository.findByProductDetailAndBuildingBuildingAddress(productDetail, jibunAddress);
    }
    @Override
    public boolean addProduct(int userIndex, List<MultipartFile> fileList, ProductDTO productDTO, BuildingDTO buildingDTO, OptionsDTO optionsDTO) throws Exception {
        String productDetail = productDTO.getProductDetail();
        String jibunAddress = buildingDTO.getBuildingJibunAddress();
        Product product = findProductByAddress(productDetail, jibunAddress);
        
        //매물 정보 중복
        if(product!=null) return false;
        
        //매물 정보 없음 -> 매물 정보 저장
        StringBuilder imageFilePathListStr = new StringBuilder();      //구분자 -> ,

        //이미지 파일 저장
        //TODO : 프로젝트 파일에 넣을건지, 배포 하면 절대경로에서 상대경로로 바꿔야됨
        //경로 -> product/img/productRoadAddress/productDetail/i.png
        StringBuilder sb = new StringBuilder("C:/D106/product/img/");
        for(int i=0, size=fileList.size(); i<size; i++){
            StringBuilder tmpSb = new StringBuilder(sb.toString());
            tmpSb.append(buildingDTO.getBuildingRoadAddress())
                    .append("/")
                    .append(productDTO.getProductDetail())
                    .append("/");
            Path path = Paths.get(tmpSb.toString());
            Files.createDirectories(path);
            tmpSb.append(i).append(".png");

            imageFilePathListStr.append(tmpSb.toString());
            //마지막 파일이 아니면 구분자 추가
            if(i!=size-1){
                imageFilePathListStr.append(",");
            }

            File convFile = new File(tmpSb.toString());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(fileList.get(i).getBytes());
            fos.close();
        }
        Building building = buildingRepository.findByBuildingAddress(buildingDTO.getBuildingJibunAddress());
        if (building == null) {
            building = new Building().builder()
                    .buildingAddress(buildingDTO.getBuildingJibunAddress())
                    .buildingElevator(buildingDTO.getBuildingElevator() == 1)
                    .buildingName(buildingDTO.getBuildingName())
                    .buildingParking(buildingDTO.getBuildingParking() == 1)
                    .buildingRoadAddress(buildingDTO.getBuildingRoadAddress())
                    .build();
            buildingRepository.save(building);
        }
        Options options = new Options().builder()
                .optionAirConditioner(optionsDTO.getOptionAirConditioner() == 1)
                .optionBed(optionsDTO.getOptionBed() == 1)
                .optionCloset(optionsDTO.getOptionCloset() == 1)
                .optionDesk(optionsDTO.getOptionDesk() == 1)
                .optionFridge(optionsDTO.getOptionFridge() == 1)
                .optionInduction(optionsDTO.getOptionInduction() == 1)
                .optionMicroWave(optionsDTO.getOptionMicrowave() == 1)
                .optionWashingMachine(optionsDTO.getOptionWashingMachine() == 1)
                .optionWifi(optionsDTO.getOptionWifi() == 1)
                .build();
        optionsRepository.save(options);
        //TODO: Product Entity에 User 추가하기
        //TODO: Product Entity에 photo 추가하기
        User productSeller = userRepository.findByUserIndex(userIndex);
        product = new Product().builder()
                .building(building)
                .options(options)
                .productSeller(productSeller)
                .productDetail(productDTO.getProductDetail())
                .productFloor(productDTO.getProductFloor())
                .productType(productDTO.getProductType())
                .productPrice(productDTO.getProductPrice())
                .productManageCost(productDTO.getProductManageCost())
                .productSize(productDTO.getProductSize())
                .productRooms(productDTO.getProductRooms())
                .productDirection(productDTO.getProductDirection())
                .productPhoto(imageFilePathListStr.toString())
                .productAnimal(productDTO.getProductAnimal())
                .build();
        productRepository.save(product);

        int[] output = new int[3];
        output[0] = product.getProductIndex();
        output[1] = building.getBuildingIndex();
        output[2] = options.getOptionIndex();
        return true;
    }

    @Override
    public Map<String, Object> detailProduct(int productIndex) {
        Product product = productRepository.findByProductIndex(productIndex);
        if (product == null) {
            return null;
        } else {
            //TODO: 이거 안 됨?? Building building = product.getBuilding();
            Building building = buildingRepository.findByBuildingIndex(product.getBuilding().getBuildingIndex());
            Options options = optionsRepository.findByOptionIndex(product.getOptions().getOptionIndex());
            Map<String, Object> map = new HashMap<>();

            //TODO: map.put(user&&photo)
            map.put("productManageCost", (int) 3);
            map.put("productDetail", (String) product.getProductDetail());
            map.put("productFloor", (String) product.getProductFloor());
            map.put("productType", (String) product.getProductType());
            map.put("productPrice", (String) product.getProductPrice());
            map.put("productSize", (String) product.getProductSize());
            map.put("productRooms", (String) product.getProductRooms());
            map.put("productDirection", (String) product.getProductDirection());
//            map.put("productPhoto", (String) product.getPhoto());
            map.put("productAnimal", (String) product.getProductAnimal());
//            map.put("building", (Building) product.getBuilding());
//            map.put("option", (Option) product.getOption());
//            map.put("user", (User) product.getUser());

            //이거 안 됨?
//            User user =product.getUser();
            User user = userRepository.findByUserIndex(product.getProductSeller().getUserIndex());
            map.put("userIndex", (int) user.getUserIndex());
            map.put("userId", (String) user.getUserId());
            map.put("userPhone", (String) user.getUserPhone());
            map.put("userName", (String) user.getUserName());
            map.put("userCorporationRegistrationNumber", (String) user.getCorporateRegistrationNumber());
            map.put("userAddress", (String) user.getUserAddress());
            map.put("buildingAddress", (String) building.getBuildingAddress());
            map.put("buildingName", (String) building.getBuildingName());
            map.put("buildingRoadAddress", (String) building.getBuildingRoadAddress());
            map.put("buildingIndex", (int) building.getBuildingIndex());
            map.put("buildingElevator", building.isBuildingElevator());
            map.put("buildingParking", building.isBuildingParking());
            map.put("optionIndex", (int) options.getOptionIndex());
            map.put("optionAirConditioner", options.isOptionAirConditioner());
            map.put("optionBed", options.isOptionBed());
            map.put("optionCloset", options.isOptionCloset());
            map.put("optionDesk", options.isOptionDesk());
            map.put("optionFridge", options.isOptionFridge());
            map.put("optionGasStove", options.isOptionGasStove());
            map.put("optionInduction", options.isOptionInduction());
            map.put("optionMicrowave", options.isOptionMicroWave());
            map.put("optionWashingMachine", options.isOptionWashingMachine());
            map.put("optionWifi", options.isOptionWifi());

            //TODO: 이거 됨??
//            map.put("user", (User) user);
//            map.put("product", (Product) product);
//            map.put("building", (Building) building);
//            map.put("option", (Option) option);
            return map;
        }
    }

//    @Override
    public int updateProduct(ProductDTO productDTO, BuildingDTO buildingDTO, OptionsDTO optionsDTO) {
//        Product product = productRepository.findByProductIndex(productDTO.getProductIndex());
////        Product product = productRepository.findByProductIndex(productIndex);
//        if (product == null) {
//            return 204;
//        } else {
//            Building building = buildingRepository.findByBuildingIndex(buildingDTO.getBuildingIndex());
//            Options options = optionsRepository.findByOptionIndex(optionsDTO.getOptionIndex());
//            building.setBuildingAddress(buildingDTO.getBuildingJibunAddress());
//            building.setBuildingElevator(buildingDTO.getBuildingElevator() == 1);
//            building.setBuildingName(buildingDTO.getBuildingName());
//            building.setBuildingParking(buildingDTO.getBuildingParking() == 1);
//            building.setBuildingRoadAddress(buildingDTO.getBuildingRoadAddress());
//            options.setOptionAirConditioner(optionsDTO.getOptionAirConditioner() == 1);
//            options.setOptionBed(optionsDTO.getOptionBed() == 1);
//            options.setOptionCloset(optionsDTO.getOptionCloset() == 1);
//            options.setOptionDesk(optionsDTO.getOptionDesk() == 1);
//            options.setOptionFridge(optionsDTO.getOptionFridge() == 1);
//            options.setOptionInduction(optionsDTO.getOptionInduction() == 1);
//            options.setOptionMicroWave(optionsDTO.getOptionMicroWave() == 1);
//            options.setOptionWashingMachine(optionsDTO.getOptionWashingMachine() == 1);
//            options.setOptionWifi(optionsDTO.getOptionWifi() == 1);
//            //TODO: Product Entity에 User 추가하기
//            //TODO: Product Entity에 photo 추가하기
//
////            이거 안 됨 ?
////            User user = product.getUser();
//            User productSeller = userRepository.findByUserIndex(product.getProductSeller().getUserIndex());
//            product.setBuilding(building);
//            product.setOptions(options);
//            product.setProductSeller(productSeller);
//            product.setProductDetail(productDTO.getProductDetail());
//            product.setProductFloor(productDTO.getProductFloor());
//            product.setProductType(productDTO.getProductType());
//            product.setProductPrice(productDTO.getProductPrice());
//            product.setProductManageCost(productDTO.getProductManageCost());
//            product.setProductSize(productDTO.getProductSize());
//            product.setProductRooms(productDTO.getProductRooms());
//            product.setProductDirection(productDTO.getProductDirection());
////            product.setProductPhoto(productDTO.getProductPhoto());
//            product.setProductAnimal(productDTO.getProductAnimal());
//
            return 200;
//        }
    }

    @Override
    public int deleteProduct(int productIndex) {
        Product product = productRepository.findByProductIndex(productIndex);
        if (product == null) {
            return 204;
        } else {
            productRepository.deleteProductByProductIndex(productIndex);
            return 200;
        }
    }

}
