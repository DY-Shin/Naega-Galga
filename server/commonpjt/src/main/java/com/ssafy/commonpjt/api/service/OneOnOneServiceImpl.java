package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.meetingDTO.RequestEnterOneOnOneDTO;
import com.ssafy.commonpjt.api.dto.meetingDTO.RequestExitOneOnOneDTO;
import com.ssafy.commonpjt.api.dto.meetingDTO.ResponseEnterOneOnOneDTO;
import com.ssafy.commonpjt.api.dto.meetingDTO.ResponseExitOneOnOneDTO;
import com.ssafy.commonpjt.db.entity.Meeting;
import com.ssafy.commonpjt.db.repository.MeetingRepository;
import com.ssafy.commonpjt.db.repository.ProductRepository;
import com.ssafy.commonpjt.db.repository.UserRepository;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OneOnOneServiceImpl implements OneOnOneService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    MeetingRepository meetingRepository;

    @Override
    public ResponseEnterOneOnOneDTO enterOneOnOne(RequestEnterOneOnOneDTO requestEnterOneOnOneDTO) throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = requestEnterOneOnOneDTO.getOpenVidu().getActiveSession(requestEnterOneOnOneDTO.getSessionId());

        //session이 아직 생성되지 않았을 때 session 열고 저장
        boolean sessionIsNull = session == null;
        if (sessionIsNull) {
            Map<String, String> params = new HashMap<>();
            params.put("customSessionId", requestEnterOneOnOneDTO.getSessionId());
            SessionProperties properties = SessionProperties.fromJson(params).build();
            session = requestEnterOneOnOneDTO.getOpenVidu().createSession(properties);
        }

        //session의 Token 저장
        ConnectionProperties properties = ConnectionProperties.fromJson(null).build();
        Connection connection = session.createConnection(properties);
        String token = connection.getToken();

        //반환값 처리
        ResponseEnterOneOnOneDTO responseEnterOneOnOneDTO = new ResponseEnterOneOnOneDTO();
        responseEnterOneOnOneDTO.setToken(token);

        //초기에 session이 안 열린 경우 token으로 할 일
        if (sessionIsNull) {
            requestEnterOneOnOneDTO.getUserList().put(token, new int[2]);
        }
        if (requestEnterOneOnOneDTO.getSessionList().get(Integer.parseInt(requestEnterOneOnOneDTO.getSessionId())) == null) {
            requestEnterOneOnOneDTO.getSessionList().put(Integer.parseInt(requestEnterOneOnOneDTO.getSessionId()), token);
        }
        //반환값 처리(sellerIndex & buyerIndex 저장)
        Optional<Meeting> optionalMeeting = meetingRepository.findById(Integer.parseInt(requestEnterOneOnOneDTO.getSessionId()));
        if (optionalMeeting.isPresent()) {
            Meeting meeting = optionalMeeting.get();
            responseEnterOneOnOneDTO.setBuyerIndex(meeting.getGuest().getUserIndex());
            responseEnterOneOnOneDTO.setSellerIndex(meeting.getOwner().getUserIndex());

        } else {
            //TODO: exception 처리
        }

        return responseEnterOneOnOneDTO;
    }

    @Override
    public void exitOneOnOne(RequestExitOneOnOneDTO requestExitOneOnOneDTO) throws OpenViduJavaClientException, OpenViduHttpException {
        String sessionId = requestExitOneOnOneDTO.getSessionId();
        requestExitOneOnOneDTO.getOpenVidu().getActiveSession(sessionId).close();
    }
}
