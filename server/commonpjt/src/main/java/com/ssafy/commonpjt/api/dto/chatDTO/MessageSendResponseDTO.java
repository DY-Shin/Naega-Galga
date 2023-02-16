package com.ssafy.commonpjt.api.dto.chatDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageSendResponseDTO {
    private int chatRoomIndex;
    private MessageDTO message;
}
