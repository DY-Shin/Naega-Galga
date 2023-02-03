package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.chatDTO.ChatRoomResponseDTO;

import java.util.List;

public interface ChatService {
    List<ChatRoomResponseDTO> getChatList() throws Exception;


}
