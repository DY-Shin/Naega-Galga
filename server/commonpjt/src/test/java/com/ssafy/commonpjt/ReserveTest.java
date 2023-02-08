package com.ssafy.commonpjt;

import com.ssafy.commonpjt.api.dto.reserveDTO.ReserveRequestDTO;
import com.ssafy.commonpjt.api.service.ReserveServiceImpl;
import com.ssafy.commonpjt.db.entity.User;
import com.ssafy.commonpjt.db.repository.MeetingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@SpringBootTest
public class ReserveTest {

    @Autowired
    private ReserveServiceImpl reserveService;
    @Autowired
    private MeetingRepository meetingRepository;

    @Test
    void test() {
//        ReserveRequestDTO requestDTO = ReserveRequestDTO.builder()
//                .owner(2)
//                .guest(3)
//                .date("2023-02-08 12:00:00")
//                .build();
//        reserveService.addReserve(requestDTO);
        String date = "2023-02-08 11:10:50";
        User owner = User.builder()
                .userIndex(3)
                .build();
        User guest = User.builder()
                .userIndex(4)
                .build();
        meetingRepository.existsMeeting(guest, owner,Timestamp.valueOf(date));

    }
}
