package com.ssafy.commonpjt.api.dto.userDTO;

import com.ssafy.commonpjt.db.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
public class OwnerDTO {
    private String userName;
    private String userPhone;
    private String userAddress;
    private String corporateRegistrationNumber;

    @Builder
    public OwnerDTO (User owner) {
        this.userName = owner.getName();
        this.userPhone = owner.getUserPhone();
        this.userAddress = owner.getUserAddress();
        this.corporateRegistrationNumber = owner.getCorporateRegistrationNumber();
    }
}
