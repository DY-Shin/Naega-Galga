package com.ssafy.commonpjt.api.dto.user;

import lombok.Data;

@Data
public class FindPasswordDTO {
    private String userId;
    private String userName;
    private String userPhone;
}
