import { AxiosResponse } from "axios";
//TODO : 나중에 token instance로 바꿀것
import apiTokenInstance from "./apiInstance";

async function getChatRooms(userIndex: number) {
  const response: AxiosResponse = await apiTokenInstance.get(
    `/api/chat/rooms/${userIndex}`
  );
  return response;
}

async function getChatContent(chatRoomIndex: number) {
  const response: AxiosResponse = await apiTokenInstance.post(
    `/api/chat/rooms`,
    {
      roomIndex: chatRoomIndex,
    }
  );
  return response;
}

async function getChatRoomInfo(OpIndex: number) {
  const response: AxiosResponse = await apiTokenInstance.post(
    `/api/chat/rooms/ask`,
    {
      OpIndex: OpIndex,
    }
  );
  return response;
}

export { getChatRooms, getChatContent, getChatRoomInfo };
