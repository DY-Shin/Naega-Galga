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
  console.log(
    userIndex + " " + opIndex + " " + date + "!!!!!!!!!!!!!!!!!!!!!!!!"
  );
  const response: AxiosResponse = await axios.post(
    `/api/reserve`,
    {
      userIndex: userIndex,
      opIndex: opIndex,
      date: date,
    },
    {
      headers: {
        "Content-Type": "multipart/form-data",
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
    }
  );
  return response.status;
}

export { getChatRooms, getChatContent, checkReserve };
