package com.ssafy.commonpjt.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    private String userId;
    private String userPhone;
    private String userName;
    private String corporateRegistrationNumber;
    private String userAddress;
}
