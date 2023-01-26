package com.ssafy.commonpjt.api.dto;

import lombok.Data;

@Data
public class UserLogoutRequestDTO {
    private String accessToken;
    private String refreshToken;
}
