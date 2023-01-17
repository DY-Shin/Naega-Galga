package com.ssafy.commonpjt.api.controller;

import com.ssafy.commonpjt.api.service.SearchServiceImpl;
import com.sun.jndi.toolkit.url.Uri;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SearchController {
    @Autowired
    private SearchServiceImpl searchService;

    @Value("${kakao.restapi.key}")
    private String restApiKey;
    private static final String API_SERVER_HOST = "https://dapi.kakao.com/v2/local/search/address.json";

    @GetMapping("/search")
    public Map searchProduct(@RequestParam String query) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Authorization", "KakaoAK " + restApiKey);

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        URI targetUrl = UriComponentsBuilder
                .fromUriString(API_SERVER_HOST)
                .queryParam("query", query)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();

        ResponseEntity<Map> result = restTemplate.exchange(targetUrl, HttpMethod.GET, httpEntity, Map.class);

        return result.getBody().;
    }


//    @GetMapping("/search/detail")
//    public ResponseEntity<?> detailProduct() {
//        List<?> productInfo = searchService.detailProduct();
//        return ResponseEntity.ok(?);
//    }

}
