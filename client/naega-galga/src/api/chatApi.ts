import axios, { AxiosResponse } from "axios";
import localStorageManager from "@/utils/localStorageManager";

async function getChatRooms() {
  const response: AxiosResponse = await axios.get(`/api/chat/rooms`, {
    headers: {
      Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
    },
  });
  return response;
}

async function getChatContent(opIndex: number) {
  const response: AxiosResponse = await axios.get(
    `/api/chat/rooms/${opIndex}`,
    {
      headers: {
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
    }
  );

  return response;
}

async function checkReserve(userIndex: number, opIndex: number, date: string) {
  const response: AxiosResponse = await axios.post(
    `/api/reserve`,
    {
      userIndex: userIndex,
      opIndex: opIndex,
      date: date,
    },
    {
      headers: {
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
    }
  );
  return response;
}

export { getChatRooms, getChatContent, checkReserve };
