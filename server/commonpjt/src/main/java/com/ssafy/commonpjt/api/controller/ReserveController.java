package com.ssafy.commonpjt.api.controller;

import com.ssafy.commonpjt.api.dto.reserveDTO.ReserveRequestDTO;
import com.ssafy.commonpjt.api.dto.reserveDTO.ReserveResponseDTO;
import com.ssafy.commonpjt.api.service.ReserveServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reserve")
public class ReserveController {

    private final ReserveServiceImpl reserveService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("")
    public ResponseEntity<?> createReserve(@RequestBody ReserveRequestDTO requestDTO) throws Exception{
        boolean result = reserveService.addReserve(requestDTO);
        return ResponseEntity.ok(result);
    }
}
