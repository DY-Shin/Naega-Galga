package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.chatDTO.ChatRoomResponseDTO;
import com.ssafy.commonpjt.api.dto.chatDTO.MessageListRequestDTO;
import com.ssafy.commonpjt.api.dto.chatDTO.MessageListResponseDTO;

import java.util.List;

public interface ChatService {
    List<ChatRoomResponseDTO> getChatList() throws Exception;

    MessageListResponseDTO getMessageList(int opIndex) throws Exception;
}
