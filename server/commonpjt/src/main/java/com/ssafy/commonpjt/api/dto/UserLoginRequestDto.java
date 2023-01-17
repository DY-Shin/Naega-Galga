package com.ssafy.commonpjt.dto;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class UserLoginRequestDto {
    private String userId;
    private String userPassword;
}
