package com.ssafy.commonpjt.api.dto.searchDTO;

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
}
