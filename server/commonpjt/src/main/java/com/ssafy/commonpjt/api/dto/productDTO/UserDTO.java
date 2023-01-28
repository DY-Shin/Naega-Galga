package com.ssafy.commonpjt.api.dto.productDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {
    private int userIndex;
    private String userId;
    private String userPassword;
    private String userPhone;
    private String userName;
    private String userCorporationRegistrationNumber;
    private String userAddress;
}
