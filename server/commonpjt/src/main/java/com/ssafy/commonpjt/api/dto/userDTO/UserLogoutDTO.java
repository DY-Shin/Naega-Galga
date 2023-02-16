package com.ssafy.commonpjt.api.dto.userDTO;

import lombok.Data;

@Data
public class UserLogoutDTO {
    // 로그아웃시 필요한 토큰
    private String accessToken;
    private String refreshToken;
    private String checkPassword;
}
