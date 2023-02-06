import axios, { AxiosResponse } from "axios";
import localStorageManager from "@/utils/localStorageManager";

async function getChatRooms() {
  console.log("getRooms");
  const response: AxiosResponse = await axios.get(`/api/chat/rooms`, {
    headers: {
      Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
    },
  });
  return response;
}

async function getChatContent(chatRoomIndex: number) {
  const response: AxiosResponse = await axios.post(
    `/api/chat/rooms`,
    {
      roomIndex: chatRoomIndex,
    },
    {
      headers: {
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
    }
  );
  return response;
}

async function getChatRoomInfo(opIndex: number) {
  const response: AxiosResponse = await axios.post(
    `/api/chat/rooms/ask`,
    {
      opIndex: opIndex,
    },
    {
      headers: {
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
    }
  );
  return response;
}

export { getChatRooms, getChatContent, getChatRoomInfo };
