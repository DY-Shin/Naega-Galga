package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.ChatMessage;
import com.ssafy.commonpjt.db.entity.ChatRoom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    List<ChatMessage> findByChatRoom(ChatRoom chatRoom);

    Slice<ChatMessage> findByChatRoom(ChatRoom chatRoom, Pageable pageable);
}