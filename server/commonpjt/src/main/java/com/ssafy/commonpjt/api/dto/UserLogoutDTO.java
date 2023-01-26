package com.ssafy.commonpjt.api.dto;

import lombok.Data;

@Data
public class UserLogoutDTO {
    private String accessToken;
    private String refreshToken;
    private String checkPassword;
}
