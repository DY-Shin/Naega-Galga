//package com.ssafy.commonpjt;
//
//import com.ssafy.commonpjt.api.dto.chatDTO.ChatRoomResponseDTO;
//import com.ssafy.commonpjt.api.service.ChatService;
//import com.ssafy.commonpjt.api.service.ChatServiceImpl;
//import com.ssafy.commonpjt.api.service.SearchServiceImpl;
//import com.ssafy.commonpjt.db.entity.Product;
//import com.ssafy.commonpjt.db.repository.BuildingRepository;
//import com.ssafy.commonpjt.db.repository.ProductRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootTest
//class ChatTest {
//
//    @Autowired
//    private ChatServiceImpl chatService;
//
//    @Test
//    void contextLoads() throws Exception {
//        List<ChatRoomResponseDTO> roomList = chatService.getChatList();
//
//        for(ChatRoomResponseDTO room: roomList) {
//            System.out.println(room.getRoomIndex() + " " + room.getOpIndex()+" "+room.getOpName());
//        }
//    }
//}
