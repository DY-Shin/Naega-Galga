package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.searchDTO.KakaoAddressDTO;
import com.ssafy.commonpjt.api.dto.searchDTO.SearchProductResponseDTO;
import com.ssafy.commonpjt.api.dto.searchDTO.DetailSearchDTO;
import com.ssafy.commonpjt.db.entity.Product;
import com.ssafy.commonpjt.db.repository.BuildingRepository;
import com.ssafy.commonpjt.db.repository.MeetingRepository;
import com.ssafy.commonpjt.db.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{

    @Value("${kakao.restapi.key}")
    private String restApiKey;

    private final BuildingRepository buildingRepository;
    private final ProductRepository productRepository;
    private final MeetingRepository meetingRepository;


    /**
     * 카카오 로컬 api를 호출
     * 검색한 주소의 정확한 값으로 변환하여
     * DB에 저장된 값과 비교하여 반환
     *
     * @param address
     * @return List<SearchDTO>
     */
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
                .exchangeToMono(response -> response.bodyToMono(String.class));

        JSONObject addressObject = new JSONObject(mono.block());
        List<KakaoAddressDTO> addressList = new ArrayList<>();
        if(addressObject.has("errorType")) {
            return addressList;
        }

        JSONArray addressArray = addressObject.getJSONArray("documents");
        for(int i = 0, addressArrayLength = addressArray.length(); i<addressArrayLength; i++) {
            KakaoAddressDTO kakaoAddressDTO = KakaoAddressDTO.builder()
                    .address("")
                    .roadAddress("")
                    .build();
            JSONObject object = addressArray.getJSONObject(i);

            JSONObject addressInfo = new JSONObject();

            if(!object.isNull("address")) {
                addressInfo = object.getJSONObject("address");
                kakaoAddressDTO.setAddress(addressInfo.getString("address_name"));
            }

            if(!object.isNull("road_address")) {
                addressInfo = object.getJSONObject("road_address");
                kakaoAddressDTO.setRoadAddress(addressInfo.getString("address_name"));
            }

            addressList.add(kakaoAddressDTO);
        }

        List<SearchProductResponseDTO> searchResult = new ArrayList<>();
        for(KakaoAddressDTO kakaoAddress : addressList) {
            String roadAddr = kakaoAddress.getRoadAddress();
            String addr = kakaoAddress.getAddress();

            List<Integer> buildings = buildingRepository
                    .findBuildingIndexByBuildingAddressStartingWithAndBuildingRoadAddressStartingWith(addr, roadAddr);

            for(Integer idx : buildings) {
                List<Product> product = productRepository.productFetchJoin(idx);
                for(Product productInfo : product) {
                    String[] photo = productInfo.getProductPhoto().split(",");
                    productInfo.setProductPhoto(photo[0]);
                    searchResult.add(SearchProductResponseDTO.toDTO(productInfo, meetingRepository.existsByProduct(productInfo)));
                }
            }
        }
        return searchResult;
    }

    @Override
    public DetailSearchDTO detailProduct(int id) {
        Product product = productRepository.findByProductIndex(id);
        return DetailSearchDTO.toDTO(product);
    }
}
