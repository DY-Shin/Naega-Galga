package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    // 채팅방 유무 검사
    @Query("select chatroom from ChatRoom chatroom " +
            "where (chatroom.buyer.userIndex = ?1 and chatroom.seller.userIndex = ?2) " +
            "or (chatroom.buyer.userIndex = ?2 and chatroom.seller.userIndex = ?1)")
    ChatRoom hasChatRoom(Integer buyer, Integer seller);

    // 채팅방 목록 받아오기.
    @Query("select cr " +
            "from ChatRoom cr join fetch cr.seller s join fetch cr.buyer b " +
            "where s.userIndex = ?1 or b.userIndex = ?1")
    List<ChatRoom> findChatRoomByUser(Integer userIndex);


}