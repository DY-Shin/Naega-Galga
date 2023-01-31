package com.ssafy.commonpjt.api.dto.productDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDTO {
    private ProductDTO product;
    private BuildingDTO building;
    private OptionsDTO options;
    private ProductSellerDTO seller;

    private String[] imagePaths;
}
