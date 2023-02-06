package com.ssafy.commonpjt.api.service;

import com.ssafy.commonpjt.api.dto.chatDTO.ChatRoomResponseDTO;
import com.ssafy.commonpjt.api.dto.chatDTO.MessageDTO;
import com.ssafy.commonpjt.api.dto.chatDTO.MessageListRequestDTO;
import com.ssafy.commonpjt.api.dto.chatDTO.MessageListResponseDTO;
import com.ssafy.commonpjt.common.security.SecurityUtil;
import com.ssafy.commonpjt.db.entity.ChatMessage;
import com.ssafy.commonpjt.db.entity.ChatRoom;
import com.ssafy.commonpjt.db.entity.User;
import com.ssafy.commonpjt.db.repository.ChatMessageRepository;
import com.ssafy.commonpjt.db.repository.ChatRoomRepository;
import com.ssafy.commonpjt.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository messageRepository;
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
    public MessageListResponseDTO getMessageList(int opIndex) throws Exception {
        MessageListResponseDTO result;
        User loginUser = getLoginUser();
        int loginUserIndex = loginUser.getUserIndex();
        User opUser = User.builder()
                .userIndex(opIndex)
                .build();
        ChatRoom chatRoom = chatRoomRepository.hasChatRoom(loginUserIndex, opIndex);
        List<MessageDTO> resultMessage = new ArrayList<>();
        if(chatRoom == null) {
            log.info("NO CHAT ROOM I WILL CREATE");
            chatRoom = ChatRoom.builder()
                    .buyer(loginUser)
                    .seller(opUser)
                    .build();
            chatRoomRepository.save(chatRoom);
            log.info("I CREATE ROOM INDEX " + chatRoom.getChatIndex());
            result = MessageListResponseDTO.builder()
                    .chatRoomIndex(chatRoom.getChatIndex())
                    .messageList(resultMessage)
                    .build();
            return result;
        }
        log.info("I GOT ROOM INDEX " + chatRoom.getChatIndex());

        List<ChatMessage> messageList = messageRepository.findByChatRoom(chatRoom);

        for(ChatMessage message : messageList) {
            log.info("I SEND MESSAGE : " + message.getMessage() + " WHO AM I : "+message.getSender().getUserIndex());
            MessageDTO dto = MessageDTO.builder()
                    .senderIndex(message.getSender().getUserIndex())
                    .message(message.getMessage())
                    .time(new SimpleDateFormat("HH:mm:ss").format(message.getCreatedAt()))
                    .build();

            resultMessage.add(dto);
        }
        result = MessageListResponseDTO.builder()
                .chatRoomIndex(chatRoom.getChatIndex())
                .messageList(resultMessage)
                .build();
        return result;
    }


}
