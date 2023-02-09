package com.ssafy.commonpjt.api.dto.explanationDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExplanationInfoDTO {
    int meetingIndex=-1;
    int sellerIndex=-1;
    int buyerIndex = -1;
    String reservedAt;
}
