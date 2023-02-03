package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.chatDTO.ChatRoomResponseDTO;
import com.ssafy.commonpjt.common.security.SecurityUtil;
import com.ssafy.commonpjt.db.entity.ChatRoom;
import com.ssafy.commonpjt.db.entity.User;
import com.ssafy.commonpjt.db.repository.ChatRoomRepository;
import com.ssafy.commonpjt.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public List<ChatRoomResponseDTO> getChatList() throws Exception {
        User loginUser = userRepository
//                .findByUserId(SecurityUtil.getLoginUsername())
                .findByUserId("aabbcc")
                .orElseThrow(() -> new Exception("No User Exists"));
        int loginUserIndex = loginUser.getUserIndex();
        String loginUserName = loginUser.getName();
        List<ChatRoom> roomList = chatRoomRepository.findChatRoomByUser(loginUserIndex);

        List<ChatRoomResponseDTO> result = new ArrayList<>();
        for(ChatRoom room : roomList) {
            int OpIndex = room.getBuyer().getUserIndex() != loginUserIndex ? room.getBuyer().getUserIndex() : room.getSeller().getUserIndex();
            String OpName = !room.getBuyer().getName().equals(loginUserName) ? room.getBuyer().getName() : room.getSeller().getName();
            ChatRoomResponseDTO dto = ChatRoomResponseDTO.builder()
                    .roomIndex(room.getChatIndex())
                    .OpIndex(OpIndex)
                    .OpName(OpName)
                    .build();
            result.add(dto);
        }

        return result;
    }


}
