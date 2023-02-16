package com.ssafy.commonpjt.api.dto.chatDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageListResponseDTO {
    private int chatRoomIndex;
    private List<MessageDTO> messageList;
}
