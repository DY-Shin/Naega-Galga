package com.ssafy.commonpjt.api.controller;

import com.ssafy.commonpjt.api.dto.meetingDTO.RequestEnterOneOnOneDTO;
import com.ssafy.commonpjt.api.dto.meetingDTO.RequestExitOneOnOneDTO;
import com.ssafy.commonpjt.api.dto.meetingDTO.ResponseEnterOneOnOneDTO;
import com.ssafy.commonpjt.api.service.OneOnOneService;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/meeting/one-on-one")
public class OneOnOneController {

    private final OneOnOneService oneOnOneService;

    @Value("${OPENVIDU_URL}")
    private String URL;

    @Value("${OPENVIDU_SECRET}")
    private String SECRET;

    private OpenVidu openVidu;

    //TODO: 설명회는 arrlist or array
    private Map<String, int[]> findUsersInTheSessionByToken;
    private Map<Integer, String> findSessionTokenByMeetingIndex;

    @PostConstruct
    private void init() {
        openVidu = new OpenVidu(URL, SECRET);
        findUsersInTheSessionByToken = new HashMap<>();
        findSessionTokenByMeetingIndex = new HashMap<>();
    }

    @GetMapping("/{meetingIndex}")
    public ResponseEntity<ResponseEnterOneOnOneDTO> enterOneOnOne(@PathVariable int meetingIndex) throws OpenViduJavaClientException, OpenViduHttpException {
        log.info("in");
        RequestEnterOneOnOneDTO requestEnterOneOnOneDTO = new RequestEnterOneOnOneDTO();

        requestEnterOneOnOneDTO.setSessionId(String.valueOf(meetingIndex));
        requestEnterOneOnOneDTO.setOpenVidu(this.openVidu);
        requestEnterOneOnOneDTO.setUserList(this.findUsersInTheSessionByToken);
        requestEnterOneOnOneDTO.setSessionList(this.findSessionTokenByMeetingIndex);

        ResponseEnterOneOnOneDTO responseEnterOneOnOneDTO = oneOnOneService.enterOneOnOne(requestEnterOneOnOneDTO);

        return new ResponseEntity<>(responseEnterOneOnOneDTO, HttpStatus.OK);
    }

    @PostMapping("/{meetingIndex}")
    public ResponseEntity<?> exitOneOnOne(@PathVariable int meetingIndex) throws OpenViduJavaClientException, OpenViduHttpException {
        RequestExitOneOnOneDTO requestExitOneOnOneDTO = new RequestExitOneOnOneDTO();

        requestExitOneOnOneDTO.setSessionId(String.valueOf(meetingIndex));
        requestExitOneOnOneDTO.setOpenVidu(this.openVidu);

        oneOnOneService.exitOneOnOne(requestExitOneOnOneDTO);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
