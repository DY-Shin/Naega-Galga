package com.ssafy.commonpjt.api.helper;

import org.springframework.beans.factory.annotation.Value;

public class KakaoRestApiHelper {
    @Value("${kakao.restapi.key}")
    private String restApiKey;

    private static final String API_SERVER_HOST = "https://dapi.kakao.com";
    private static final String LOCAL_ADDRESS_SEARCH_PATH = "/v2/local/search/address.json";


}
