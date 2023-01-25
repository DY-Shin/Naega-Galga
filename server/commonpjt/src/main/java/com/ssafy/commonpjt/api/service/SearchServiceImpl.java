package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.searchDTO.KakaoAddressDTO;
import com.ssafy.commonpjt.api.dto.searchDTO.SearchProductDTO;
import com.ssafy.commonpjt.api.dto.searchDTO.DetailSearchDTO;
import com.ssafy.commonpjt.db.entity.Product;
import com.ssafy.commonpjt.db.repository.BuildingRepository;
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

        List<SearchProductDTO> searchResult = new ArrayList<>();
        for(KakaoAddressDTO kakaoAddress : addressList) {
            String roadAddr = kakaoAddress.getRoadAddress();
            String addr = kakaoAddress.getAddress();

            List<Integer> buildings = buildingRepository
                    .findBuildingIndexByBuildingAddressStartingWithAndBuildingRoadAddressStartingWith(addr, roadAddr);

            for(Integer idx : buildings) {
                System.out.println(idx);
                List<Product> product = productRepository.productFetchJoin(idx);
                for(Product p : product) {
                    SearchProductDTO searchDTO = SearchProductDTO.builder()
                            .index(p.getProductIndex())
                            .addr(p.getBuilding().getBuildingAddress())
                            .roadAddr(p.getBuilding().getBuildingRoadAddress())
                            .price(p.getProductPrice())
                            .photo(p.getProductPhoto())
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
                    searchResult.add(searchDTO);
                }
            }
        }

        return searchResult;
    }

    @Override
    public DetailSearchDTO detailProduct(int id) {
        List<Product> product = productRepository.productFetchJoin(id);
        DetailSearchDTO detailDTO = new DetailSearchDTO();
        for(Product p:product) {
            detailDTO = DetailSearchDTO.builder()
                .index(p.getProductIndex())
                .addr(p.getBuilding().getBuildingAddress())
                .roadAddr(p.getBuilding().getBuildingRoadAddress())
                .price(p.getProductPrice())
                .photo(p.getProductPhoto())
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
        }

        return detailDTO;
    }
}
