package com.ssafy.commonpjt.api.dto;

import lombok.Data;

import java.util.Optional;

@Data
public class UserUpdateDTO {
    private final Optional<String> userId;
    private final Optional<String> userPhone;
    private final Optional<String> userName;
    private final Optional<String> corporateRegistrationNumber;
    private final Optional<String> userAddress;
}
