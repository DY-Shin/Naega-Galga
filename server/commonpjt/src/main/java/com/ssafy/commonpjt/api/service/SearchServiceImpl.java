package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.KakaoAddressDTO;
import com.ssafy.commonpjt.api.dto.SearchDTO;
import com.ssafy.commonpjt.db.entity.Product;
import com.ssafy.commonpjt.db.repository.BuildingRepository;
import com.ssafy.commonpjt.db.repository.OptionsRepository;
import com.ssafy.commonpjt.db.repository.ProductRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService{

    @Value("${kakao.restapi.key}")
    private String restApiKey;

    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OptionsRepository optionsRepository;

    @Override
    public List<?> searchProduct(String address) {
        System.out.println(address);
        Mono<String> mono = WebClient.builder().baseUrl("http://dapi.kakao.com")
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder.path("v2/local/search/address.json")
                        .queryParam("query", address)
                        .queryParam("size", 30)
                        .build()
                )
                .header("Authorization", "KakaoAK "+restApiKey)
                .exchangeToMono(response -> {
                    return response.bodyToMono(String.class);
                });

        JSONObject addressObject = new JSONObject(mono.block());
        List<KakaoAddressDTO> addressList = new ArrayList<KakaoAddressDTO>();
        if(addressObject.has("errorType")) {
            return addressList;
        }

        JSONArray addressArray = addressObject.getJSONArray("documents");
        for(int i = 0, addressArrayLength = addressArray.length(); i<addressArrayLength; i++) {
            KakaoAddressDTO dto = KakaoAddressDTO.builder()
                    .address("")
                    .roadAddress("")
                    .build();
            JSONObject object = addressArray.getJSONObject(i);

            JSONObject addressInfo = new JSONObject();

            if(!object.isNull("address")) {
                addressInfo = object.getJSONObject("address");
                dto.setAddress(addressInfo.getString("address_name"));
            }

            if(!object.isNull("road_address")) {
                addressInfo = object.getJSONObject("road_address");
                dto.setRoadAddress(addressInfo.getString("address_name"));
            }

            addressList.add(dto);
        }

        List<SearchDTO> searchResult = new ArrayList<>();
        for(KakaoAddressDTO KakaoAddress : addressList) {
            String roadAddr = KakaoAddress.getRoadAddress();
            String addr = KakaoAddress.getAddress();

            List<Integer> buildings = buildingRepository.findBuildingIndexByBuildingAddressStartingWithAndBuildingRoadAddressStartingWith(addr, roadAddr);

            for(Integer idx : buildings) {
                List<Product> product = productRepository.productFetchJoin(idx);

                for(Product p : product) {
                    SearchDTO.Building buildingDTO = SearchDTO.Building.builder()
                            .roadAddr(p.getBuilding().getBuildingRoadAddress())
                            .build();
                    SearchDTO.Product productDTO = SearchDTO.Product.builder()
                            .index(p.getProductIndex())
                            .price(p.getProductPrice())
                            .photo(p.getProductPhoto())
                            .build();
                    SearchDTO.Options optionsDTO = SearchDTO.Options.builder()
                            .airConditioner(p.getOptions().isOptionAirConditioner())
                            .fridge(p.getOptions().isOptionFridge())
                            .washingMachine(p.getOptions().isOptionWashingMachine())
                            .gasStove(p.getOptions().isOptionGasStove())
                            .induction(p.getOptions().isOptionInduction())
                            .microWave(p.getOptions().isOptionMicroWave())
                            .desk(p.getOptions().isOptionDesk())
                            .wifi(p.getOptions().isOptionWifi())
                            .closet(p.getOptions().isOptionCloset())
                            .bed(p.getOptions().isOptionBed())
                            .build();

                    searchResult.add(new SearchDTO(buildingDTO, productDTO, optionsDTO));
                }
            }
        }

        return searchResult;
    }

//    @Override
//    public List<?> detailProduct() {
//        return null;
//    }
}
