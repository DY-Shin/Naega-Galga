// import { AxiosResponse } from "axios";
//TODO : 나중에 token instance로 바꿀것
// import apiTokenInstance from "./apiInstance";

async function getChatRooms() {
  console.log("getRooms");
  // const response: AxiosResponse = await apiTokenInstance.get(`/api/chat/rooms`);
  // return response;
  const list = [
    {
      chatRoomIndex: 1,
      opIndex: 2,
      opName: "백소원",
    },
    {
      chatRoomIndex: 2,
      opIndex: 3,
      opName: "이준",
    },
    {
      chatRoomIndex: 3,
      opIndex: 4,
      opName: "박재희",
    },
  ];
  return list;
}

async function getChatContent(opIndex: number) {
  // const response: AxiosResponse = await apiTokenInstance.post(
  //   `/api/chat/rooms`,
  //   {
  //     roomIndex: chatRoomIndex,
  //   }
  // );
  // return response;
  console.log(opIndex);
  const list = [
    {
      chatRoomIndex: 1,
      sender: 2,
      message: "hi1",
      createdAt: "01:01",
    },
    {
      chatRoomIndex: 1,
      sender: 1,
      message: "hello2",
      createdAt: "01:02",
    },
    {
      chatRoomIndex: 1,
      sender: 2,
      message: "hi3",
      createdAt: "01:01",
    },
    {
      chatRoomIndex: 1,
      sender: 1,
      message: "hello4",
      createdAt: "01:02",
    },
    {
      chatRoomIndex: 1,
      sender: 2,
      message: "hi5",
      createdAt: "01:01",
    },
    {
      chatRoomIndex: 1,
      sender: 1,
      message: "hello6",
      createdAt: "01:02",
    },
    {
      chatRoomIndex: 1,
      sender: 2,
      message: "hi7",
      createdAt: "01:01",
    },
    {
      chatRoomIndex: 1,
      sender: 1,
      message: "hello8",
      createdAt: "01:02",
    },
    {
      chatRoomIndex: 1,
      sender: 2,
      message: "hi9",
      createdAt: "01:01",
    },
    {
      chatRoomIndex: 1,
      sender: 1,
      message: "hello10",
      createdAt: "01:02",
    },
  ];

  const content = {
    chatRoomIndex: 1,
    list: list,
  };
  return content;
}

export { getChatRooms, getChatContent };
