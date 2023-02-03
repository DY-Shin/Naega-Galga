package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.chatDTO.ChatRoomResponseDTO;
import com.ssafy.commonpjt.api.dto.chatDTO.MessageListRequestDTO;
import com.ssafy.commonpjt.api.dto.chatDTO.MessageListResponseDTO;
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

    public User getLoginUser() throws Exception{
        User loginUser = userRepository
//                .findByUserId(SecurityUtil.getLoginUsername())
                .findByUserId("aabbcc")
                .orElseThrow(() -> new Exception("No User Exists"));
        return loginUser;
    }
    @Override
    public List<ChatRoomResponseDTO> getChatList() throws Exception {
        User loginUser = getLoginUser();
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

    @Override
    public MessageListResponseDTO getMessageList(MessageListRequestDTO dto) throws Exception {
        User loginUser = getLoginUser();
        int loginUserIndex = loginUser.getUserIndex();
        int opIndex = dto.getOpIndex();
        User opUser = userRepository.findById(opIndex)
                .orElseThrow(() -> new Exception("No User Exists"));
        ChatRoom chatRoom = chatRoomRepository.hasChatRoom(loginUserIndex, opIndex);

        int roomIndex;

        if(chatRoom == null) {
            ChatRoom createRoom = ChatRoom.builder()
                    .buyer(loginUser)
                    .seller(opUser)
                    .build();
        }

        return null;
    }


}
