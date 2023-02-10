package com.ssafy.commonpjt.api.dto.reserveDTO;

import com.ssafy.commonpjt.api.dto.productDTO.ProductInfoDTO;
import com.ssafy.commonpjt.api.dto.userDTO.GuestDTO;
import com.ssafy.commonpjt.api.dto.userDTO.OwnerDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class ReserveResponseDTO {
    private Integer meetingIndex;
    private String type;
    private String role;
    private Timestamp reserveAt;
    private String meetingUrl;
    private Integer guestNumber;
    private OwnerDTO owner;
    private GuestDTO guest;
    private ProductInfoDTO product;
}
