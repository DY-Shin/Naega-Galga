package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.chatDTO.ChatRoomResponseDTO;
import com.ssafy.commonpjt.api.dto.chatDTO.MessageListResponseDTO;
import com.ssafy.commonpjt.api.dto.chatDTO.MessageSendRequestDTO;
import com.ssafy.commonpjt.api.dto.chatDTO.MessageSendResponseDTO;

import java.util.List;

public interface ChatService {
    List<ChatRoomResponseDTO> getChatList() throws Exception;

    MessageListResponseDTO getMessageList(int opIndex) throws Exception;

    MessageSendResponseDTO saveMessage(MessageSendRequestDTO requestDTO);
}
