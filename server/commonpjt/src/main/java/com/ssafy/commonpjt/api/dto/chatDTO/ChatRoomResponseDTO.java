package com.ssafy.commonpjt.api.dto.chatDTO;

import com.ssafy.commonpjt.db.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomResponseDTO {
    private int roomIndex;
    private int OpIndex;
    private String OpName;
}
