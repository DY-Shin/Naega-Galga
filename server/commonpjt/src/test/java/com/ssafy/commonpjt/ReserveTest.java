package com.ssafy.commonpjt;

import com.ssafy.commonpjt.api.dto.reserveDTO.ReserveRequestDTO;
import com.ssafy.commonpjt.api.service.ReserveServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReserveTest {

    @Autowired
    private ReserveServiceImpl reserveService;
    @Test
    void test() {
        ReserveRequestDTO requestDTO = ReserveRequestDTO.builder()
                .owner(2)
                .guest(3)
                .date("2023-02-08 12:00:00")
                .build();
        reserveService.addReserve(requestDTO);
    }
}
