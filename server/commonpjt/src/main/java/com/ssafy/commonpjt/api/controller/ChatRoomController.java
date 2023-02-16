package com.ssafy.commonpjt.api.controller;

import com.ssafy.commonpjt.api.dto.chatDTO.MessageListRequestDTO;
import com.ssafy.commonpjt.api.dto.chatDTO.MessageListResponseDTO;
import com.ssafy.commonpjt.api.service.ChatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatRoomController {

    @Autowired
    private ChatServiceImpl chatService;

    // TODO : RequestBody로 받아야될까? 그냥 경로에 명시를 하는 것이 좋을 것 같다.
    @GetMapping("/rooms/{opIndex}")
    public ResponseEntity<?> getMessageList(@PathVariable("opIndex") int opIndex) throws Exception {
        MessageListResponseDTO messageList = chatService.getMessageList(opIndex);
        return ResponseEntity.ok(messageList);
    }

    @GetMapping("/rooms")
    public ResponseEntity<?> getChatList() throws Exception {
        List<?> chatList = chatService.getChatList();
        return ResponseEntity.ok(chatList);
    }
}
