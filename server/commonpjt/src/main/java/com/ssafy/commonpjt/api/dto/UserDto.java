package com.ssafy.commonpjt.api.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String userId;
    private String userPassword;
    private String userPhone;
    private String userName;
    private String corporateRegistrationNumber;
    private String userAddress;
}
