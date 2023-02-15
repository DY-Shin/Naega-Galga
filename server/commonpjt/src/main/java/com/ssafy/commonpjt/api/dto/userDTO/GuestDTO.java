package com.ssafy.commonpjt.api.dto.userDTO;

import com.ssafy.commonpjt.db.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
public class GuestDTO {
    private String userName;
    private String userPhone;

    @Builder
    public GuestDTO (User guest) {
        this.userName = guest.getName();
        this.userPhone = guest.getUserPhone();
    }
}
