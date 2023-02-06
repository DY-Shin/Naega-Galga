package com.ssafy.commonpjt;

import com.ssafy.commonpjt.api.dto.chatDTO.MessageListRequestDTO;
import com.ssafy.commonpjt.api.service.ChatServiceImpl;
import com.ssafy.commonpjt.db.entity.ChatMessage;
import com.ssafy.commonpjt.db.entity.ChatRoom;
import com.ssafy.commonpjt.db.repository.ChatMessageRepository;
import com.ssafy.commonpjt.db.repository.ChatRoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ChatTest {

    @Autowired
    private ChatServiceImpl chatService;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Test
    void contextLoads() throws Exception {
        ChatRoom chat= chatRoomRepository.hasChatRoom(1,3);

        System.out.println(chat != null ? "I GOT " +chat.getChatIndex() + " " + chat.getCreatedAt() : "I Will CREATE MY ROOM");
        chatService.getMessageList(7);

        chat = ChatRoom.builder()
                .chatIndex(1)
                .build();
        List<ChatMessage> messageList = chatMessageRepository.findByChatRoom(chat);
        for(ChatMessage message : messageList) {
            System.out.println("Sender : "+message.getSender().getName()+" MESSAGE : "+ message.getMessage());
        }
    }
}
