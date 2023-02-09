package com.ssafy.commonpjt.api.dto.reserveDTO;

import com.ssafy.commonpjt.api.dto.productDTO.ProductInfoDTO;
import com.ssafy.commonpjt.db.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class ReserveResponseDTO {
    private Timestamp reserveAt;
    private String owner;
    private String guest;
    private ProductInfoDTO product;
}
