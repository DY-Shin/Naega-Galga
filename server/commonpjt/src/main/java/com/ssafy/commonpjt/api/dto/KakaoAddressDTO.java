package com.ssafy.commonpjt.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoAddressDTO {

    private String address;

    private String roadAddress;

    // private String X;

    // private String Y;


}
