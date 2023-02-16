package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.meetingDTO.RequestEnterOneOnOneDTO;
import com.ssafy.commonpjt.api.dto.meetingDTO.RequestExitOneOnOneDTO;
import com.ssafy.commonpjt.api.dto.meetingDTO.ResponseEnterOneOnOneDTO;
import com.ssafy.commonpjt.api.dto.meetingDTO.ResponseExitOneOnOneDTO;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;

public interface OneOnOneService {
    ResponseEnterOneOnOneDTO enterOneOnOne(RequestEnterOneOnOneDTO requestEnterOneOnOneDTO) throws OpenViduJavaClientException, OpenViduHttpException;

    void exitOneOnOne(RequestExitOneOnOneDTO requestExitOneOnOneDTO) throws OpenViduJavaClientException, OpenViduHttpException;
}
