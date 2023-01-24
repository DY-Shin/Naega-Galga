package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.KakaoAddressDTO;
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
        if(address==null) {
            System.out.println("Empty");
            return null;
        }
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
        JSONArray addressArray = addressObject.getJSONArray("documents");

        int addressArrayLength = addressArray.length();

        List<KakaoAddressDTO> addressList = new ArrayList<KakaoAddressDTO>();
        for(int i = 0; i<addressArrayLength; i++) {
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

        int addressCount = addressList.size();

//
//
//        for(KakaoAddressDTO KakaoAddress : addressList) {
//            String roadAddr = KakaoAddress.getRoadAddress();
//            String addr = KakaoAddress.getAddress();
//
//            buildingRepository.findBuildingIndexByBuildingAddressStartingWithOrBuildingRoadAddressStartingWith(addr, roadAddr);
//
//        }

        return addressList;
    }

//    @Override
//    public List<?> detailProduct() {
//        return null;
//    }
}
