package com.ssafy.commonpjt.api.controller;

import com.ssafy.commonpjt.api.service.ChatServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatRoomController {

    @Autowired
    private ChatServiceImpl chatService;

    @GetMapping("/rooms")
    public ResponseEntity<?> getChatList() throws Exception{
        List<?> chatList = chatService.getChatList();
        return ResponseEntity.ok(chatList);
    }
}
