package com.ssafy.commonpjt.api.dto;

import com.ssafy.commonpjt.db.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
public class UserInfoDTO {
    private final String userId;
    private final String userPhone;
    private final String userName;
    private final String corporateRegistrationNumber;
    private final String userAddress;

    @Builder
    public UserInfoDTO(User user) {
        this.userId = user.getUserId();
        this.userPhone = user.getUserPhone();
        this.userName = user.getName();
        this.corporateRegistrationNumber = user.getCorporateRegistrationNumber();
        this.userAddress = user.getUserAddress();
    }
}
