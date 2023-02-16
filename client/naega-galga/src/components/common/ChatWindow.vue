<template>
  <!-- <div id="chat-window"></div> -->
  <!-- --------------chat icon start-------------- -->

  <div id="new-icon" v-if="isNewChat">new</div>
  <el-button
    id="chat-btn"
    @click="OpenChatRooms"
    circle
    color="#393B44"
    size="large"
  >
    <el-icon size="35" color="#F1F3F8">
      <ChatRound />
    </el-icon>
  </el-button>
  <!-- --------------chat icon end-------------- -->
  <!-- --------------chat list start-------------- -->
  <el-scrollbar v-show="isOpenChatRooms" class="chat-list" height="300px">
    <div v-for="(item, index) in chatRooms" :key="item.chatRoomIndex">
      <button @click="setOpIndex(index)" class="chat-list-item">
        {{ item.opName }}
      </button>
    </div>
  </el-scrollbar>
  <!-- --------------chat room start-------------- -->
  <div class="chat-room" v-show="isOpenChat">
    <div class="send-box box"></div>
    <el-icon
      id="close-btn"
      @click="CloseChat"
      :size="40"
      color="#FF4444"
      style="
        cursor: pointer;
        position: absolute;
        left: 400px;
        top: -30px;
        z-index: 2021;
      "
      ><CircleCloseFilled
    /></el-icon>
    <div>
      <div
        id="chatTitle"
        style="font-size: 20px; margin: 10px 15px 0; float: left"
      >
        {{ nowOpName }}
      </div>
      <div id="reserve-btn" style="float: right" @click="openReserve">
        <el-button type="primary" style="margin-left: 5px">예약</el-button>
      </div>
    </div>
    <div style="clear: both"></div>

    <div>
      <div class="chat-content">
        <div
          v-for="item in chatContents.slice().reverse()"
          :key="item.createdAt"
          class="msg"
        >
          <div class="item mymsg" v-if="userIndex == item.sender">
            <div class="box">
              <p class="msg">{{ item.message }}</p>
              <span class="time">{{ item.createdAt }}</span>
            </div>
          </div>
          <div class="item" v-else>
            <div class="box">
              <p class="msg">{{ item.message }}</p>
              <span class="time">{{ item.createdAt }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="message-input" style="display: inline-flex">
      <el-input v-model="inputMsg" @keyup.enter="sendMessage" />
      <img
        src="@/assets/image/icon-send.png"
        width="20"
        height="20"
        style="padding: 7px 10px 7px 7px; margin: 0 5px"
        @click="sendMessage"
      />
    </div>
  </div>
  <!-- --------------chat room end-------------- -->
  <!-- --------------reserve start-------------- -->
  <div v-if="isOpenReserve" class="reserveWindow">
    <div style="text-align: center">
      <el-config-provider :locale="ko">
        <el-calendar v-model="dateValue" @click="getDate" />
      </el-config-provider>
      <form id="reserveForm">
        <div style="padding: 5px 10px 15px">
          {{ year }}년 {{ month }}월 {{ date }}일
        </div>
        <div style="padding-left: 10px">
          <el-select v-model="ampm" placeholder="오전" style="width: 75px">
            <el-option
              v-for="item in timeOptions"
              :key="item"
              :value="item"
            /> </el-select
          ><el-select
            class="m-1"
            v-model="hour"
            placeholder="시"
            style="width: 60px; margin-left: 5px"
          >
            <el-option
              v-for="item in hourOptions"
              :key="item"
              :value="item"
            /> </el-select
          ><el-select
            v-model="minute"
            placeholder="분"
            style="width: 60px; margin-left: 5px"
          >
            <el-option
              v-for="item in minuteOptions"
              :key="item"
              :value="item"
            />
          </el-select>
          <el-button
            type="primary"
            @click="onClickReserve"
            style="margin-left: 5px"
            >예약하기</el-button
          >
        </div>
      </form>
    </div>
  </div>
  <!-- --------------reserve end-------------- -->
</template>
<script lang="ts">
import { defineComponent, ref, reactive, computed, watch } from "vue";
import { ChatRound, CircleCloseFilled } from "@element-plus/icons-vue";
import { getChatRooms, getChatContent } from "@/api/chatApi";
import { checkReserve } from "@/api/chatApi";
import { useStore } from "vuex";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

import ko from "element-plus/dist/locale/ko.mjs";

export default defineComponent({
  props: {
    getChatUserIndex: { type: Number },
    getChatUserName: { type: String },
    getChatOpen: { type: Boolean },
  },
  components: {
    ChatRound,
    CircleCloseFilled,
  },
  setup() {
    const store = useStore();
    const userIndex = computed(() => store.getters["userStore/userIndex"]);
    const nowOpIndex = computed(() => store.getters["chatStore/opIndex"]); // 현재 채팅의 상대 인덱스
    const nowOpName = computed(() => store.getters["chatStore/opName"]); // 현재 채팅의 상대 이름

    const nowRoomIndex = computed(
      () => store.getters["chatStore/chatRoomIndex"]
    );

    const isOpenChat = computed(
      () => store.getters["chatStore/isChatRoomOpen"]
    );

    const isOpenList = ref(false); // 채팅 목록 열림 여부
    const isNewChat = ref(false); // 새로운 채팅 여부
    const isOpenReserve = ref(false); // 예약 창 열림 여부
    const inputMsg = ref("");

    watch(
      () => nowOpIndex.value,
      () => {
        if (isOpenChat.value) {
          openChat();
        }
      }
    );

    // ------------------------------------채팅 start------------------------------------
    interface chatRoomsItem {
      chatRoomIndex: number;
      opIndex: number;
      opName: string;
    }
    interface message {
      // 메세지
      sender: number;
      message: string;
      createdAt: string;
    }
    interface chatMessage {
      // 주고받는 메세지
      chatRoomIndex: number;
      message: message;
    }
    const isOpenChatRooms = ref(false);
    let chatRooms = reactive<Array<chatRoomsItem>>([]); // 채팅방 목록

    const OpenChatRooms = async () => {
      // 채팅방 목록 요청
      if (!isOpenChatRooms.value) {
        chatRooms.splice(0);
        const list = await getChatRooms();
        list.data.forEach(item => chatRooms.push(item));
      }
      isOpenChatRooms.value = !isOpenChatRooms.value;
    };

    const chatProduct = ref();
    let chatContents = reactive<Array<message>>([]);

    const setOpIndex = (index: number) => {
      // 목록에서 열 때
      store.commit("chatStore/CHANGE_OP_INDEX", chatRooms[index].opIndex); // 현재 채팅 상대 업데이트해주고
      store.commit("chatStore/CHANGE_OP_NAME", chatRooms[index].opName);
      openChat(); // 채팅방 열기
    };

    const openChat = async () => {
      // 채팅방 컨텐츠 요청
      if (nowOpIndex.value == userIndex.value) {
        return;
      }

      store.commit("chatStore/CHANGE_CHATROOM_STATUS", true);
      const list = await getChatContent(nowOpIndex.value);
      store.commit("chatStore/CHANGE_CHATROOM_INDEX", list.data.chatRoomIndex);
      chatContents.splice(0); // 채팅방 내용 초기화(전에 열었던 채팅 목록 남아있음)

      list.data.messageList.forEach(item => chatContents.push(item));
      isOpenChatRooms.value = false;

      connect();
    };

    const CloseChat = () => {
      // 채팅 닫기 버튼
      store.commit("chatStore/CHANGE_CHATROOM_STATUS", false);
      store.commit("chatStore/CHANGE_OP_INDEX", -1);
      isOpenReserve.value = false;
      dateValue.value = new Date();
      ampm.value = "";
      hour.value = "";
      minute.value = "";
    };

    // ------------------------------------채팅 end------------------------------------
    // ------------------------------------메세지 전송 start------------------------------------
    let serverURL;
    let socket;

    let socketConnectedIdx = -1;

    const connect = () => {
      if (socketConnectedIdx == nowOpIndex.value) {
        return;
      }

      console.log("소켓 연결 시도 !");
      serverURL = `${process.env.VUE_APP_API_BASE_URL}api/stomp`;
      socket = new SockJS(serverURL);
      socket.stompClient = Stomp.over(socket);

      socket.stompClient.debug = () => {
        // 로그가 출력 되지 않게
        ("");
      };

      socket.stompClient.connect(
        {},
        () => {
          let addr = `/sub/chat/room/${nowRoomIndex.value}`;

          socketConnectedIdx = nowOpIndex.value;
          socket.connected = true;
          console.log("소켓 연결 성공 : ");
          socket.stompClient.subscribe(addr, res => {
            let str = JSON.parse(res.body);
            if (str.message.sender !== userIndex.value) {
              chatContents.push(str.message);
              console.log("메세지 수신");
            }
          });
        },
        error => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패 : ", error);
          socket.connected = false;
        }
      );
    };

    const sendMessage = e => {
      // 메세지 보냄
      if (e.isComposing || e.keyCode === 229) {
        return;
      }
      if (inputMsg.value == "") {
        return;
      }
      send(inputMsg.value);
      inputMsg.value = ""; // 입력 초기화
    };

    const send = (inputMsg: string) => {
      let today = new Date();
      let str =
        today.getFullYear() +
        "-" +
        (today.getMonth() + 1).toString(10).padStart(2, "0") +
        "-" +
        today.getDate().toString(10).padStart(2, "0") +
        " " +
        today.getHours().toString(10).padStart(2, "0") +
        ":" +
        today.getMinutes().toString(10).padStart(2, "0") +
        ":" +
        today.getSeconds().toString(10).padStart(2, "0") +
        "." +
        today.getMilliseconds().toString(10);

      if (socket.stompClient && socket.stompClient.connected) {
        const msg: chatMessage = {
          // 서버에 보내줄 거
          chatRoomIndex: nowRoomIndex.value,
          message: {
            sender: userIndex.value,
            message: inputMsg,
            createdAt: str,
          },
        };
        console.log("메세지 송신");
        socket.stompClient.send(`/pub/chat/message`, JSON.stringify(msg), {});

        chatContents.push({
          //화면에 띄울 컨텐츠 배열에 넣음
          sender: userIndex.value,
          message: inputMsg,
          createdAt:
            today.getHours().toString().padStart(2, "0") +
            ":" +
            today.getMinutes().toString().padStart(2, "0"),
        });
      }
    };

    // ------------------------------------메세지 전송 end------------------------------------
    // ------------------------------------달력 예약 start------------------------------------
    const ampm = ref("");
    const hour = ref();
    const minute = ref();
    const timeOptions = ["오전", "오후"];
    const hourOptions = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
    const minuteOptions = ["0", "30"];
    const dateValue = ref(new Date());
    const year = ref(dateValue.value.getFullYear());
    const month = ref(dateValue.value.getMonth() + 1);
    const date = ref(dateValue.value.getDate());

    const typeValue = ref("");

    const getDate = () => {
      year.value = dateValue.value.getFullYear();
      month.value = dateValue.value.getMonth() + 1;
      date.value = dateValue.value.getDate();
    };

    const openReserve = () => {
      // 예약 창 열기
      isOpenReserve.value = !isOpenReserve.value;
    };

    // 예약 등록
    const onClickReserve = async () => {
      if (ampm.value == "" || hour.value == "" || minute.value == "") {
        // 빈칸 안돼
        alert("시간을 입력해주세요");
        return;
      }

      if (ampm.value == "오전" && hour.value == 12) {
        hour.value = 0;
      }

      //"2023-02-01 12:00:00"
      const str =
        year.value +
        "-" +
        month.value.toString(10).padStart(2, "0") +
        "-" +
        date.value.toString(10).padStart(2, "0") +
        " " +
        hour.value.toString(10).padStart(2, "0") +
        ":" +
        minute.value.toString(10).padStart(2, "0");

      let isPossible = await checkReserve(
        userIndex.value,
        nowOpIndex.value,
        str
      );

      if (isPossible.data) {
        send(str + "\u000A 예약이 완료 되었습니다.");
        isOpenReserve.value = false;
      } else {
        alert("다른 시간을 선택해주세요.");
      }

      dateValue.value = new Date(); // 예약 형식 초기화해줌
      ampm.value = "";
      hour.value = "";
      minute.value = "";
    };
    // ------------------------------------달력 예약 end------------------------------------
    return {
      ko,
      isNewChat,
      inputMsg,
      OpenChatRooms,
      openChat,
      isOpenChatRooms,
      openReserve,
      isOpenList,
      isOpenChat,
      isOpenReserve,
      CloseChat,
      dateValue,
      getDate,
      date,
      year,
      month,
      typeValue,
      timeOptions,
      ampm,
      hourOptions,
      minuteOptions,
      hour,
      minute,
      onClickReserve,
      chatProduct,
      chatRooms,
      chatContents,
      sendMessage,
      setOpIndex,
      userIndex,
      nowOpIndex,
      nowRoomIndex,
      nowOpName,
    };
  },
});
</script>

<style>
::-webkit-scrollbar {
  width: 8px; /* 스크롤바의 너비 */
  /* display: none; */
}

::-webkit-scrollbar-thumb {
  height: 30%; /* 스크롤바의 길이 */
  background: #e3e3e3; /* 스크롤바의 색상 */
  border-radius: 10px;
}

::-webkit-scrollbar-track {
  background: rgba(218, 218, 218, 0.1); /*스크롤바 뒷 배경 색상*/
}
/* -----------------reserve css start----------------- */
.el-calendar__body {
  padding: 15px 20px;
}
.el-calendar__header {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
  padding: 12px 20px;
  border-bottom: var(--el-calendar-header-border-bottom);
}
.el-calendar-table .el-calendar-day {
  font-size: small;
  box-sizing: border-box;
  padding: 8px;
  height: 40px;
}

.reserveWindow {
  background: rgb(255, 255, 255);
  border: 1px solid rgb(184, 184, 184);
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
  border-radius: 15px;
  z-index: 102;
  position: fixed;
  top: calc(50vh - 200px);
  left: calc(100vw - 500px);
  right: 100px;
  width: 320px;
  height: 450px;
}
/* -----------------reserve css end----------------- */
/* -----------------chat css start----------------- */
#chatTitle {
  padding: 10px 0 15px 10px;
  font-size: 15px;
}
#reserve-btn {
  cursor: pointer;
  padding: 10px;
}
.chat-room {
  margin: 100px;
  z-index: 101;
  position: fixed;
  top: calc(50vh - 350px);
  left: calc(50vw - 200px);
  width: 400px;
  height: 565px;
  background: rgb(255, 255, 255);
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 20px;
  border-radius: 15px;
}
.chat-room .chat-content {
  display: flex;
  flex-direction: column-reverse;
  overflow-y: auto;
  border-radius: 5px;
  padding: 10px;
  height: 420px;
}
.chat-room .item {
  margin-top: 15px;
}
.chat-room .item:first-child {
  margin-top: 0px;
}
.chat-room .item .box {
  display: inline-block;
  max-width: 220px;
  position: relative;
}
.chat-room .item .box .msg {
  background: rgb(223, 223, 223);
  border-radius: 20px;
  padding: 8px 15px;
  text-align: left;
  word-break: break-word;
  /* box-shadow: rgba(0, 0, 0, 0.1) 0px 5px 5px; */
}
.chat-room .item .box .time {
  font-size: 11px;
  color: #999;
  position: absolute;
  right: -75px;
  bottom: 20px;
  width: 70px;
}
.chat-room .item.mymsg {
  text-align: right;
}
.chat-room .item.mymsg .box .msg {
  border: 1px solid rgb(111, 111, 111);
  background: #ffffff;
}
.chat-room .item.mymsg .box .time {
  right: auto;
  left: -75px;
  bottom: 20px;
}
.chat-room .item.on .box {
  opacity: 1;
}
.message-input {
  z-index: 101;
  /* position: fix; */
  top: calc(50vh + 170px);
  left: calc(50vw - 100px);
  padding: 15px 15px;
  width: 390px;
  height: 40px;
}
.chat-list {
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
  position: fixed;
  z-index: 100;
  bottom: 140px;
  right: 50px;
  width: 250px;
  height: 300px;
  background-color: rgb(255, 255, 255);
  border-radius: 15px;
}

.chat-list-item {
  cursor: pointer;
  font-weight: 900;
  color: rgb(93, 93, 93);
  text-align: left;
  font-size: 17px;
  border: none;
  background-color: transparent;
  width: 100%;
  padding: 20px 30px;
  border-bottom: 1px solid rgb(215, 215, 215);
}

#chat-btn {
  position: fixed;
  bottom: 50px;
  right: 50px;
  z-index: 11;
  width: 70px;
  height: 70px;
}
#new-icon {
  padding: 5px 7px 7px 7px;
  background-color: #ff4444;
  color: white;
  font-weight: 1000;
  border-radius: 25px;
  text-align: center;
  align-items: center;
  border: 1px solid red;
  position: fixed;
  bottom: 105px;
  right: 23px;
  z-index: 111;
  width: 50px;
}
</style>
