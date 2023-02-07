package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.reserveDTO.ReserveRequestDTO;
import com.ssafy.commonpjt.db.entity.Meeting;
import com.ssafy.commonpjt.db.entity.User;
import com.ssafy.commonpjt.db.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class ReserveServiceImpl implements ReserveService{

    private final MeetingRepository meetingRepository;

    @Override
    public boolean addReserve(ReserveRequestDTO requestDTO) {
        User owner = User.builder()
                .userIndex(requestDTO.getOwner())
                .build();
        User guest = User.builder()
                .userIndex(requestDTO.getGuest())
                .build();

        Meeting meeting = Meeting.builder()
                .owner(owner)
                .guest(guest)
                .reserveAt(Timestamp.valueOf(requestDTO.getDate()))
                .build();
        meetingRepository.save(meeting);
        String url = "/meeting/one-on-one/" + meeting.getMeetingIndex();

        meeting.setMeetingUrl(url);
        meetingRepository.save(meeting);
        return false;
    }
}
