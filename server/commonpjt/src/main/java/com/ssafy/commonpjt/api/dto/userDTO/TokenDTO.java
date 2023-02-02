package com.ssafy.commonpjt.api.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TokenDTO {
    // 토큰 타입, 액세스 토큰, 리프레시 토큰, 리프레시 토큰 만료 시간
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long refreshTokenExpirationTime;
}
