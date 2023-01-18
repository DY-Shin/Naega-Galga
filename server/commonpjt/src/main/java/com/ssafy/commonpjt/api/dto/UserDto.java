package com.ssafy.commonpjt.api.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String userId;
    private String userPassword;
    private String userPhone;
    private String userNick;
    private String corporateRegistrationNumber;
    private String userAddress;
}
