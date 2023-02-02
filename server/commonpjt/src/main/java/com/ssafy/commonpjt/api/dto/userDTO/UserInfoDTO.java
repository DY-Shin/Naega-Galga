package com.ssafy.commonpjt.api.dto.userDTO;

import com.ssafy.commonpjt.db.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
public class UserInfoDTO {
    // 회원 정보 조회 시 조회 정보
    private final Integer userIndex;
    private final String userId;
    private final String userPhone;
    private final String userName;
    private final String corporateRegistrationNumber;
    private final String userAddress;

    @Builder
    public UserInfoDTO(User user) {
        this.userIndex = user.getUserIndex();
        this.userId = user.getUserId();
        this.userPhone = user.getUserPhone();
        this.userName = user.getName();
        this.corporateRegistrationNumber = user.getCorporateRegistrationNumber();
        this.userAddress = user.getUserAddress();
    }
}
