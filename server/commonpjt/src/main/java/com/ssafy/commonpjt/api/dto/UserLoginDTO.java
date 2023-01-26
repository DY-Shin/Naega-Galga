package com.ssafy.commonpjt.api.dto;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
public class UserLoginDTO {
    private String userId;
    private String userPassword;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(userId, userPassword);
    }
}
