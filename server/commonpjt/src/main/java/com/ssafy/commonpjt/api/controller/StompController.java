package com.ssafy.commonpjt.api.controller;

import com.ssafy.commonpjt.api.dto.chatDTO.MessageSendRequestDTO;
import com.ssafy.commonpjt.api.dto.chatDTO.MessageSendResponseDTO;
import com.ssafy.commonpjt.api.service.ChatServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StompController {

    private final ChatServiceImpl chatService;
    // 특정 브로커로 메세지를 전달
    private final SimpMessagingTemplate template;
    // /pub/chat/enter
//    @MessageMapping("/chat/enter")
//    public void enter() {
//        template.convertAndSend("/sub/chat/room/", "Connection");
//    }

    // /pub/chat/message
    @MessageMapping("/chat/message")
    public void message(MessageSendRequestDTO requestDTO) {
        MessageSendResponseDTO message = chatService.saveMessage(requestDTO);
        template.convertAndSend("/sub/chat/room/"+message.getChatRoomIndex(), message);
    }
}
