package com.ssafy.commonpjt.api.dto.user;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String userId;
    private String userPassword;
    private String userPhone;
    private String userName;
    private String corporateRegistrationNumber;
    private String userAddress;
}
