package com.ssafy.commonpjt.api.dto;

import lombok.Data;

@Data
public class UpdatePasswordDTO {
    private final String checkPassword;
    private final String toBePassword;
}
