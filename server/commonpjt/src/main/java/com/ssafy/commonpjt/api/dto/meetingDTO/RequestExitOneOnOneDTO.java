package com.ssafy.commonpjt.api.dto.meetingDTO;

import io.openvidu.java.client.OpenVidu;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class RequestExitOneOnOneDTO {
    OpenVidu openVidu;
    String sessionId;
}
