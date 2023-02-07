package com.ssafy.commonpjt.api.dto.reserveDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReserveRequestDTO {
    private int owner;
    private int guest;
    private String date;
}
