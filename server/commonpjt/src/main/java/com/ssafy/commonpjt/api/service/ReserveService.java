package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.reserveDTO.ReserveRequestDTO;
import com.ssafy.commonpjt.api.dto.reserveDTO.ReserveResponseDTO;

public interface ReserveService {
     boolean addReserve(ReserveRequestDTO requestDTO) throws Exception;
}
