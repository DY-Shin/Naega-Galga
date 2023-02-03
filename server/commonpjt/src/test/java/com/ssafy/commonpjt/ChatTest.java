package com.ssafy.commonpjt;

import com.ssafy.commonpjt.api.service.ChatServiceImpl;
import com.ssafy.commonpjt.db.entity.ChatRoom;
import com.ssafy.commonpjt.db.repository.ChatRoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ChatTest {

    @Autowired
    private ChatServiceImpl chatService;
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Test
    void contextLoads() throws Exception {
        ChatRoom chat= chatRoomRepository.hasChatRoom(1,2);

        System.out.println(chat != null ? "I GOT " +chat.getChatIndex() + " " + chat.getCreatedAt() : "I Will CREATE MY ROOM");
    }
}
