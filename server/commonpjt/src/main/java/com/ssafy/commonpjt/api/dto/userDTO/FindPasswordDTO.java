package com.ssafy.commonpjt.api.dto.userDTO;

import lombok.Data;

@Data
public class FindPasswordDTO {
    private String userId;
    private String userName;
    private String userPhone;
    private String toBePassword;
}
