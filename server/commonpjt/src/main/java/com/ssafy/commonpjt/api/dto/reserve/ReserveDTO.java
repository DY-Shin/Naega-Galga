package com.ssafy.commonpjt.api.dto.reserve;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReserveDTO {
    private Integer id;
    private LocalDate reserveDate;
    private LocalTime reserveTime;
}
