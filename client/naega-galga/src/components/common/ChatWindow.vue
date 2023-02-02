<template>
  <!-- <div id="chat-window"></div> -->
  <!-- --------------chat icon start-------------- -->
  <el-button
    id="chat-btn"
    @click="OpenChatRooms"
    circle
    color="#393B44"
    size="large"
  >
    <el-icon size="40" color="#F1F3F8">
      <ChatDotRound />
    </el-icon>
  </el-button>
  <!-- --------------chat icon end-------------- -->
  <!-- --------------chat list start-------------- -->
  <el-scrollbar v-show="isOpenChatRooms" class="chat-list" height="400px">
    <div v-for="(item, index) in chatRooms" :key="item.chatRoomIndex">
      <button @click="OpenChat(index)" class="chat-list-item">
        {{ item.OpName }}
      </button>
    </div>
  </el-scrollbar>
  <!-- --------------chat room start-------------- -->
  <div class="chat-room" v-show="isOpenChat">
    <!-- <div class="send-box box">{sd</div> -->
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
    <div id="chatTitle" style="font-size: 20px; margin: 10px 15px 0">{{}}</div>
    <div>
      <div class="chat-content">
        <div v-for="item in chatContents" :key="item.chatRoomIndex" class="msg">
          <div class="item" v-if="nowChatRoomIndex == item.senderIndex">
            <div class="box">
              <p class="msg">{{ item.message }}</p>
              <span class="time">{{ item.time }}</span>
            </div>
          </div>

          <div class="item mymsg" v-else>
            <div class="box">
              <p class="msg">{{ item.message }}</p>
              <span class="time">{{ item.time }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="message-input" style="display: inline-flex">
      <el-input v-model="inputMsg" />
      <img
        src="@/assets/image/icon-send.png"
        width="30"
        height="30"
        style="padding: 3px 7px"
      />
    </div>
    <div id="clock-icon" @click="OpenReserve">
      <img src="@/assets/image/icon-clock.png" width="30" />
    </div>
  </div>
  <!-- --------------chat room end-------------- -->
  <!-- --------------reserve start-------------- -->
  <div v-if="isOpenReserve" class="reserveWindow">
    <div style="text-align: center">
      <el-calendar v-model="dateValue" @click="getDate" />
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
import { defineComponent, ref, watch, reactive, computed } from "vue";
import { Plus, Promotion } from "@element-plus/icons-vue";
import { getChatRooms, getChatContent } from "@/api/chatApi";
import { addProductReserve } from "@/api/productApi";
import ResponseStatus from "@/api/responseStatus";
import { useStore } from "vuex";

export default defineComponent({
  props: {
    GetProduct: { type: Object },
  },
  setup(props) {
    const nowChatRoomIndex = ref(0);
    const userIndex = ref(0);
    const OpIndex = ref(0);
    const isOpenList = ref(false);
    const isOpenChat = ref(false);
    const isOpenReserve = ref(false);
    const inputMsg = ref("");

    watch(
      () => isOpenChat.value,
      () => {
        console.log("isOpenChat " + isOpenChat.value);
      },
      { deep: true }
    );

    watch(
      () => props.GetProduct,
      () => {
        chatProduct.value = props.GetProduct;
        isOpenChat.value = true;
      },
      { deep: true }
    );
    // ------------------------------------채팅 start------------------------------------

    interface chatRoom {
      chatRoomIndex: number;
      OpIndex: number;
      OpName: string;
    }

    interface chatContent {
      chatRoomIndex: number;
      senderIndex: number;
      message: string;
      time: string;
    }

    const isOpenChatRooms = ref(false);
    let chatRooms = reactive<Array<chatRoom>>([]);
    const store = useStore();
    const OpenChatRooms = async () => {
      // 채팅방 목록 요청
      userIndex.value = computed(
        () => store.getters["userStore/userIndex"]
      ).value;
      const list = await getChatRooms(userIndex.value);
      list.data.forEach((product: chatRoom) => chatRooms.push(product));
      isOpenChatRooms.value = !isOpenChatRooms.value;
    };

    const chatProduct = ref();
    let chatContents = reactive<Array<chatContent>>([]);

    const OpenChat = async (index: number) => {
      // 채팅방 컨텐츠 요청
      OpIndex.value = chatRooms[index].OpIndex;
      nowChatRoomIndex.value = chatRooms[index].chatRoomIndex;
      let list = await getChatContent(nowChatRoomIndex.value);
      list.data.forEach((content: chatContent) => chatContents.push(content));

      isOpenChat.value = true;
      isOpenChatRooms.value = false;
    };

    const CloseChat = () => {
      // 채팅 닫기 버튼
      isOpenChat.value = false;
      isOpenReserve.value = false;
      dateValue.value = new Date();
      ampm.value = "";
      hour.value = "";
      minute.value = "";
    };

    // ------------------------------------채팅 end------------------------------------
    // ------------------------------------달력 예약 start------------------------------------
    const ampm = ref("");
    const hour = ref();
    const minute = ref();
    const timeOptions = ["오전", "오후"];
    const hourOptions = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
    const minuteOptions = [0, 10, 20, 30, 40, 50];
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

    const OpenReserve = () => {
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

      const data = new Date( // Date 형식으로 보냄
        year.value,
        month.value,
        date.value,
        hour.value,
        minute.value
      );
      //"2023-02-01 12:00:00"
      const str =
        data.getFullYear() +
        "-" +
        data.getMonth().toString(10).padStart(2, "0") +
        "-" +
        data.getDate().toString(10).padStart(2, "0") +
        " " +
        data.getHours().toString(10).padStart(2, "0") +
        ":" +
        data.getMinutes().toString(10).padStart(2, "0");

      const userIndex = 1; // 나중에 로그인 정보로 바꾸기
      // chatProduct.value = chatRooms[listIdx.value];
      const status = await addProductReserve(
        // 예약 신청 수락되면
        chatProduct.value.sellerIndex,
        userIndex,
        str
      );

      if (status == ResponseStatus.Ok) {
        alert("예약 완료");
        isOpenReserve.value = false;
      } else {
        alert("다시 시도");
      }
      dateValue.value = new Date(); // 예약 형식 초기화해줌
      ampm.value = "";
      hour.value = "";
      minute.value = "";
    };
    // ------------------------------------달력 예약 end------------------------------------
    return {
      inputMsg,
      OpenChatRooms,
      OpenChat,
      isOpenChatRooms,
      OpenReserve,
      isOpenList,
      isOpenChat,
      isOpenReserve,
      CloseChat,
      Plus,
      Promotion,
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
      nowChatRoomIndex,
    };
  },
});
</script>

<style>
::-webkit-scrollbar {
  width: 8px; /* 스크롤바의 너비 */
  display: none;
}

::-webkit-scrollbar-thumb {
  height: 30%; /* 스크롤바의 길이 */
  background: #c8c8c8; /* 스크롤바의 색상 */

  border-radius: 10px;
}

::-webkit-scrollbar-track {
  background: rgba(218, 218, 218, 0.1); /*스크롤바 뒷 배경 색상*/
}
/* -----------------달력 css start----------------- */
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
/* -----------------달력 css end----------------- */
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

/* -----------------chat css start----------------- */
#chatTitle {
  margin: 10px 20px;
  font-size: 15px;
}
#clock-icon {
  padding: 10px;
}
.chat-room {
  margin: 100px;
  z-index: 101;
  position: fixed;
  top: calc(50vh - 350px);
  left: calc(50vw - 200px);
  width: 400px;
  height: 550px;
  background: rgb(255, 255, 255);
  border: 1px solid rgb(184, 184, 184);
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
  border-radius: 15px;
}
.chat-room .chat-content {
  display: flex;
  flex-direction: column-reverse;
  overflow-y: auto;
  border-radius: 5px;
  padding: 10px;
  height: 400px;
}
.chat-room .item {
  margin-top: 15px;
}
.chat-room .item:first-child {
  margin-top: 0px;
}
.chat-room .item .box {
  display: inline-block;
  max-width: 180px;
  position: relative;
}
.chat-room .item .box .msg {
  background: rgb(196, 196, 196);
  border-radius: 25px;
  padding: 8px 15px;
  text-align: left;
  word-break: break-word;
  box-shadow: 2px 2px 2px 2px rgba(203, 203, 203, 0.2);
}
.chat-room .item .box .time {
  font-size: 11px;
  color: #999;
  position: absolute;
  right: -75px;
  bottom: 5px;
  width: 70px;
}
.chat-room .item.mymsg {
  text-align: right;
}
.chat-room .item.mymsg .box .msg {
  background: #d9ff71;
}
.chat-room .item.mymsg .box .time {
  right: auto;
  left: -75px;
}
.chat-room .item.on .box {
  /* margin: 0; */
  opacity: 1;
}
.message-input {
  z-index: 101;
  /* position: fix; */
  top: calc(50vh + 170px);
  left: calc(50vw - 100px);
  padding-left: 10px;
  width: 390px;
  height: 40px;
}

.chat-list-item {
  cursor: pointer;
  font-weight: 700;
  color: rgb(93, 93, 93);
  text-align: left;
  font-size: 15px;
  border: none;
  background-color: transparent;
  width: 100%;
  padding: 20px 30px;
  border-bottom: 1px solid rgb(163, 163, 163);
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
/* .el-popper .is-light .el-popover .el-popover--plain {
  background-color: black;
  width: 100px;
} */

#chat-btn {
  position: fixed;
  bottom: 50px;
  right: 50px;
  z-index: 11;
  width: 70px;
  height: 70px;
  float: right;
}
</style>
