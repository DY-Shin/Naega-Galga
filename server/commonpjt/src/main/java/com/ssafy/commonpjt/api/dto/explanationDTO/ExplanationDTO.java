package com.ssafy.commonpjt.api.dto.explanationDTO;

import com.ssafy.commonpjt.db.entity.Meeting;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ExplanationDTO {
    private int productIndex;
    private int ownerIndex;
    private int guestIndex;
    private String reservedAt;
}
