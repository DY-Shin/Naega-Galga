package com.ssafy.commonpjt.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.commonpjt.api.dto.productDTO.*;
import com.ssafy.commonpjt.common.security.SecurityUtil;
import com.ssafy.commonpjt.db.entity.*;
import com.ssafy.commonpjt.db.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private OptionsRepository optionsRepository;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private ExplanationRepository explanationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;
    private String imagePath = "product/img/";

    private Product findProductByAddress(String productDetail, String jibunAddress){
        return productRepository.findByProductDetailAndBuildingBuildingAddress(productDetail, jibunAddress);
    }
    @Override
    public boolean addProduct(List<MultipartFile> fileList, ProductDTO productDTO, BuildingDTO buildingDTO, OptionsDTO optionsDTO) throws Exception {
        String productDetail = productDTO.getProductDetail();
        String jibunAddress = buildingDTO.getBuildingJibunAddress();
        Product product = findProductByAddress(productDetail, jibunAddress);

        //매물 정보 중복
        if(product!=null) return false;
        
        //매물 정보 없음 -> 매물 정보 저장
        StringBuilder imageFilePathListStr = new StringBuilder();      //구분자 -> ,

        //이미지 파일 저장
        for(int i=0, size=fileList.size(); i<size; i++){
            StringBuilder fullPathSb = new StringBuilder(imagePath);
            StringBuilder tmpSb = new StringBuilder();
            tmpSb.append(buildingDTO.getBuildingRoadAddress())
                    .append("/")
                    .append(productDTO.getProductDetail())
                    .append("/");

            StringBuilder realPath = new StringBuilder(imagePath);
            realPath.append(tmpSb.toString());

            Path path = Paths.get(realPath.toString());
            Files.createDirectories(path);

            String[] fileExtensionSplit = fileList.get(i).getContentType().split("/");
            String fileExtension = fileExtensionSplit[1];

            tmpSb.append(i).append(".").append(fileExtension);

            imageFilePathListStr.append(tmpSb.toString());
            //마지막 파일이 아니면 구분자 추가
            if(i!=size-1){
                imageFilePathListStr.append(",");
            }

            fullPathSb.append(tmpSb.toString());
            System.out.println(fullPathSb.toString());

            File convFile = new File(fullPathSb.toString());
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

        User seller = userRepository.findByUserId(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("No User Exists"));
        product = new Product().builder()
                .building(building)
                .options(options)
                .productSeller(seller)
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

    //entity -> dto
    private <DTO, ENTITY> DTO parseFromEntityToDTO(ENTITY entity, Class<DTO> className){
        return modelMapper.map(entity, className);
    }

    @Override
    public ProductDetailDTO detailProduct(int productIndex) throws JsonProcessingException {
        Product product = productRepository.findByProductIndex(productIndex);

        if (product == null) {
            return null;
        }
        ProductDetailDTO productDetailDTO = new ProductDetailDTO();
        
        //이미지 파일 경로
        String[] imagePaths = product.getProductPhoto().split(",");
        productDetailDTO.setImagePaths(imagePaths);

        //product to json string
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDetailDTO.setProduct(productDTO);


        //building to json string
        int buildingIndex = product.getBuilding().getBuildingIndex();
        Building buildingEntity = buildingRepository.findByBuildingIndex(buildingIndex);
        BuildingDTO building = parseFromEntityToDTO(buildingEntity, BuildingDTO.class);
        building.setBuildingJibunAddress(buildingEntity.getBuildingAddress());
        productDetailDTO.setBuilding(building);

        //options to json string
        int optionIndex = product.getOptions().getOptionIndex();
        Options optionsEntity = optionsRepository.findByOptionIndex(optionIndex);
        OptionsDTO options = parseFromEntityToDTO(optionsEntity, OptionsDTO.class);
        productDetailDTO.setOptions(options);

        //seller to json string
        int sellerIndex = product.getProductSeller().getUserIndex();
        User seller = userRepository.findByUserIndex(sellerIndex);
        ProductSellerDTO productSellerDTO = parseFromEntityToDTO(seller, ProductSellerDTO.class);
        productDetailDTO.setSeller(productSellerDTO);

        return productDetailDTO;
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
    public boolean deleteProduct(int productIndex) throws Exception, SecurityException {
        Product product = productRepository.findByProductIndex(productIndex);
        int optionsIndex = product.getOptions().getOptionIndex();
        Options options = optionsRepository.findByOptionIndex(optionsIndex);

        //매물 정보 없음
        if (product == null) {
            return false;
        }

        int buildingIndex = product.getBuilding().getBuildingIndex();
        Building building = buildingRepository.findByBuildingIndex(buildingIndex);

        StringBuilder imageDirectoryPath = new StringBuilder(imagePath);
        imageDirectoryPath
                .append(building.getBuildingRoadAddress()).append("/")
                .append(product.getProductDetail()).append("/");

        String directoryPathStr = imageDirectoryPath.toString();    //이미지의 부모 디렉토리 정보
        File directory = new File(directoryPathStr);

        //디렉토리가 존재하는지
        if(!directory.exists()) {
            throw new SecurityException();
        }
        //디렉토리인지
        if(!directory.isDirectory()){
            throw new SecurityException();
        }

        File[] imageFiles = directory.listFiles();
        for(File image : imageFiles){
            image.delete();
        }
        directory.delete();

        //관련 미팅 정보 다 지우기
        List<Meeting> meetingList = meetingRepository.findAllByProduct(product);
        for(Meeting meeting : meetingList){
            List<Explanation> explanationList = explanationRepository.findAllByMeeting(meeting);
            
            //미팅과 관련된 설명회 지우기
            for(Explanation explanation : explanationList){
                explanationRepository.delete(explanation);
            }
            meetingRepository.delete(meeting);
        }

        productRepository.deleteProductByProductIndex(productIndex);
        optionsRepository.deleteById(optionsIndex);
        return true;
    }

}
