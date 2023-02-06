import { AxiosResponse } from "axios";
import apiTokenInstance from "./apiInstance";

async function getChatRooms() {
  console.log("getRooms");
  const response: AxiosResponse = await apiTokenInstance.get(`/api/chat/rooms`);
  return response;
}

async function getChatContent(opIndex: number) {
  const response: AxiosResponse = await apiTokenInstance.post(
    `/api/chat/rooms/${opIndex}`
  );
  return response;
}

export { getChatRooms, getChatContent };
