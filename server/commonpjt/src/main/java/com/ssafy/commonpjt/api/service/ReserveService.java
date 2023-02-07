package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.reserveDTO.ReserveRequestDTO;

public interface ReserveService {
     boolean addReserve(ReserveRequestDTO requestDTO);
}
