package com.ssafy.commonpjt.api.dto.meetingDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseEnterOneOnOneDTO {
    String token;
    int sellerIndex;
    int buyerIndex;
}
