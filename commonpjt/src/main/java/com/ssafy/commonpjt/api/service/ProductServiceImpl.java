package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.BuildingDTO;
import com.ssafy.commonpjt.api.dto.OptionDTO;
import com.ssafy.commonpjt.api.dto.ProductDTO;
import com.ssafy.commonpjt.db.entity.*;
import com.ssafy.commonpjt.db.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final BuildingRepository buildingRepository;
    private final OptionRepository optionRepository;

    @Override
    public int[] addProduct(int userIndex, ProductDTO productDTO, BuildingDTO buildingDTO, OptionDTO optionDTO) throws Exception {
        Product product = productRepository.findByProductDetailAndBuildingBuildingAddress(productDTO.getProductDetail(), buildingDTO.getBuildingAddress());
        if (product == null) {
            Building building = buildingRepository.findByBuildingAddress(buildingDTO.getBuildingAddress());
            if (building == null) {
                building = new Building().builder()
                        .buildingAddress(buildingDTO.getBuildingAddress())
                        .buildingElevator(buildingDTO.getBuildingElevator())
                        .buildingName(buildingDTO.getBuildingName())
                        .buildingParking(buildingDTO.getBuildingParking())
                        .buildingRoadAddress(buildingDTO.getBuildingRoadAddress())
                        .build();
                buildingRepository.save(building);
            }
            Option option = new Option().builder()
                    .optionAirConditioner(optionDTO.getOptionAirConditioner())
                    .optionBed(optionDTO.getOptionBed())
                    .optionCloset(optionDTO.getOptionCloset())
                    .optionDesk(optionDTO.getOptionDesk())
                    .optionFridge(optionDTO.getOptionFridge())
                    .optionInduction(optionDTO.getOptionInduction())
                    .optionMicrowave(optionDTO.getOptionMicrowave())
                    .optionWashingMachine(optionDTO.getOptionWashingMachine())
                    .optionWifi(optionDTO.getOptionWifi())
                    .build();
            optionRepository.save(option);
            //TODO: Product Entity에 User 추가하기
            //TODO: Product Entity에 photo 추가하기
//            User user = userRepository.findByUserIndex(userIndex);
            product = new Product().builder()
                    .building(building)
                    .option(option)
//                    .user(user)
                    .productDetail(productDTO.getProductDetail())
                    .productFloor(productDTO.getProductFloor())
                    .productType(productDTO.getProductType())
                    .productPrice(productDTO.getProductPrice())
                    .productManageCost(productDTO.getProductManageCost())
                    .productSize(productDTO.getProductSize())
                    .productRooms(productDTO.getProductRooms())
                    .productDirection(productDTO.getProductDirection())
//                    .productPhoto(productDTO.getProductPhoto())
                    .productAnimal(productDTO.getProductAnimal())
                    .build();
            productRepository.save(product);

            int[] output = new int[3];
            output[0] = product.getProductIndex();
            output[1] = building.getBuildingIndex();
            output[2] = option.getOptionIndex();
            return output;
        } else {
            //TODO: ProductServiceImpl addProduct 리턴값 정하기
            return null;
        }
    }

    @Override
    public Map<String, Object> detailProduct(int productIndex) {
        Product product = productRepository.findByProductIndex(productIndex);
        if (product == null) {
            return null;
        } else {
            //TODO: 이거 안 됨?? Building building = product.getBuilding();
            Building building = buildingRepository.findByBuildingIndex(product.getBuilding().getBuildingIndex());
            Option option = optionRepository.findByOptionIndex(product.getOption().getOptionIndex());
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


//            map.put("userIndex", (int) user.getUserIndex());
//            map.put("userId", (String) user.getUserId());
//            map.put("userPassword", (String) user.getUserPassword());
//            map.put("userPhone", (String) user.getUserPhone());
//            map.put("userName", (String) user.getUserName());
//            map.put("userCorporationRegistrationNumber", (String) user.getUserCorporationRegistrationNumber());
//            map.put("userAddress", (String) user.getUserAddress());
            map.put("buildingAddress", (String) building.getBuildingAddress());
            map.put("buildingName", (String) building.getBuildingName());
            map.put("buildingRoadAddress", (String) building.getBuildingRoadAddress());
            map.put("buildingIndex", (int) building.getBuildingIndex());
            map.put("buildingElevator", (int) building.getBuildingElevator());
            map.put("buildingParking", (int) building.getBuildingParking());
            map.put("option", (int) option.getOptionIndex());
            map.put("option", (int) option.getOptionAirConditioner());
            map.put("option", (int) option.getOptionBed());
            map.put("option", (int) option.getOptionCloset());
            map.put("option", (int) option.getOptionDesk());
            map.put("option", (int) option.getOptionFridge());
            map.put("option", (int) option.getOptionGasStove());
            map.put("option", (int) option.getOptionInduction());
            map.put("option", (int) option.getOptionMicrowave());
            map.put("option", (int) option.getOptionWashingMachine());
            map.put("option", (int) option.getOptionWifi());

            //TODO: 이거 됨??
//            map.put("user", (User) user);
//            map.put("product", (Product) product);
//            map.put("building", (Building) building);
//            map.put("option", (Option) option);
            return map;
        }
    }

    @Override
    public int updateProduct(ProductDTO productDTO, BuildingDTO buildingDTO, OptionDTO optionDTO) {
        Product product = productRepository.findByProductIndex(productDTO.getProductIndex());
//        Product product = productRepository.findByProductIndex(productIndex);
        if (product == null) {
            return 204;
        } else {
            Building building = buildingRepository.findByBuildingIndex(buildingDTO.getBuildingIndex());
            Option option = optionRepository.findByOptionIndex(optionDTO.getOptionIndex());
            building.setBuildingAddress(buildingDTO.getBuildingAddress());
            building.setBuildingElevator(buildingDTO.getBuildingElevator());
            building.setBuildingName(buildingDTO.getBuildingName());
            building.setBuildingParking(buildingDTO.getBuildingParking());
            building.setBuildingRoadAddress(buildingDTO.getBuildingRoadAddress());
            option.setOptionAirConditioner(optionDTO.getOptionAirConditioner());
            option.setOptionBed(optionDTO.getOptionBed());
            option.setOptionCloset(optionDTO.getOptionCloset());
            option.setOptionDesk(optionDTO.getOptionDesk());
            option.setOptionFridge(optionDTO.getOptionFridge());
            option.setOptionInduction(optionDTO.getOptionInduction());
            option.setOptionMicrowave(optionDTO.getOptionMicrowave());
            option.setOptionWashingMachine(optionDTO.getOptionWashingMachine());
            option.setOptionWifi(optionDTO.getOptionWifi());
            //TODO: Product Entity에 User 추가하기
            //TODO: Product Entity에 photo 추가하기
//            User user = userRepository.findByUserIndex(userIndex);
            product.setBuilding(building);
            product.setOption(option);
//                  product.setUuser(user);
            product.setProductDetail(productDTO.getProductDetail());
            product.setProductFloor(productDTO.getProductFloor());
            product.setProductType(productDTO.getProductType());
            product.setProductPrice(productDTO.getProductPrice());
            product.setProductManageCost(productDTO.getProductManageCost());
            product.setProductSize(productDTO.getProductSize());
            product.setProductRooms(productDTO.getProductRooms());
            product.setProductDirection(productDTO.getProductDirection());
//                  product.setP.productPhoto(productDTO.getProductPhoto());
            product.setProductAnimal(productDTO.getProductAnimal());

            return 200;
        }
    }

    @Override
    public int deleteProduct(int productIndex) {
        Product product = productRepository.findByProductIndex(productIndex);
        if (product == null) {
            return 204;
        } else {
            productRepository.delete(product);
            return 200;
        }
    }

    @Override
    public int addWishList(int userIndex, int productIndex) {
        WishList wishList = wishListRepository.findByUserUserIndexAndProductProductIndex(userIndex, productIndex);
        if(wishList == null){
            return 409;
        }else{
            wishList = new WishList().builder()
//                    .product()
//                    .user()
                    .build();
            wishListRepository.save(wishList);
            return 201;
        }
    }

//    @Override
//    public int deleteWishList(int wishListIndex) {
//        WishList wishList = wishListRepository.findByWishListIndex(wishListIndex);
//        wishListRepository.delete(wishList);
//        return 200;
//    }
}
