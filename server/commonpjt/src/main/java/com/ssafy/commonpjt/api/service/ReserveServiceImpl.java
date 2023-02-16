package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.reserveDTO.ReserveRequestDTO;
import com.ssafy.commonpjt.api.dto.reserveDTO.ReserveResponseDTO;
import com.ssafy.commonpjt.common.security.SecurityUtil;
import com.ssafy.commonpjt.db.entity.Meeting;
import com.ssafy.commonpjt.db.entity.User;
import com.ssafy.commonpjt.db.repository.MeetingRepository;
import com.ssafy.commonpjt.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class ReserveServiceImpl implements ReserveService{

    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    @Override
    public boolean addReserve(ReserveRequestDTO requestDTO) throws Exception{
//        User loginUser = userRepository.findByUserId(SecurityUtil.getLoginUsername())
//                .orElseThrow(()->new Exception("No User Exists"));
        User owner = User.builder()
                .userIndex(requestDTO.getUserIndex())
                .build();
        User guest = User.builder()
                .userIndex(requestDTO.getOpIndex())
                .build();
        Timestamp reserveAt = Timestamp.valueOf(requestDTO.getDate()+":00");

        if(meetingRepository.existsMeeting(owner, guest, reserveAt) != 0) {
            return false;
        }

        Meeting meeting = Meeting.builder()
                .owner(owner)
                .guest(guest)
                .reserveAt(reserveAt)
                .build();

        meetingRepository.save(meeting);
        String url = "/meeting/one-on-one/" + meeting.getMeetingIndex();

        meeting.setMeetingUrl(url);
        meetingRepository.save(meeting);

        return true;
    }
}
