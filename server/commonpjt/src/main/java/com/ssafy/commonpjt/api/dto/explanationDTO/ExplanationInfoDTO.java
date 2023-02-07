package com.ssafy.commonpjt.api.dto.explanationDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExplanationInfoDTO {
    int meetingIndex;
    int sellerIndex;
    int buyerIndex = -1;
    String reservedAt;
}
