<template>
  <!-- <div id="chat-window"></div> -->
  <!-- --------------chat icon start-------------- -->
  <el-button
    id="chat-btn"
    @click="OpenChatList"
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
  <el-scrollbar v-show="isOpenList" class="chat-list" height="400px">
    <div v-for="(item, index) in list" :key="item">
      <button @click="OpenChat(index)" class="chat-list-item">
        {{ item }}
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
    <div id="chatTitle" style="font-size: 20px; margin: 10px 15px 0">
      {{ list[list_idx] }}
    </div>
    <div>
      <div class="chat-content">
        <div v-for="item in chatHistory" :key="item.time" class="msg">
          <div class="item" v-if="item.type == 'get'">
            <div class="box">
              <p class="msg">{{ item.content }}</p>
              <span class="time">{{ item.time }}</span>
            </div>
          </div>

          <div class="item mymsg" v-if="item.type == 'send'">
            <div class="box">
              <p class="msg">{{ item.content }}</p>
              <span class="time">{{ item.time }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="message-input" style="display: inline-flex">
      <el-input v-model="input" />
      <img
        src="@/assets/image/icon-send.png"
        width="30"
        height="30"
        style="padding: 3px 7px"
      />
    </div>
    <div id="clock-icon" @click="OpenBook">
      <img src="@/assets/image/icon-clock.png" width="30" />
    </div>
  </div>
  <!-- --------------chat room end-------------- -->
  <!-- --------------book start-------------- -->
  <div v-if="isOpenBook" class="BookWindow">
    <div style="text-align: center">
      <el-calendar v-model="dateValue" @click="getDate" />
      <form id="bookForm">
        <div style="padding: 5px 10px 15px">
          {{ year }}년 {{ month }}월 {{ date }}일
        </div>
        <div style="padding-left: 10px">
          <el-select v-model="timeValue" placeholder="오전" style="width: 75px">
            <el-option
              v-for="item in timeOptions"
              :key="item"
              :value="item"
            /> </el-select
          ><el-select
            class="m-1"
            v-model="hourValue"
            placeholder="시"
            style="width: 60px; margin-left: 5px"
          >
            <el-option
              v-for="item in hourOptions"
              :key="item"
              :value="item"
            /> </el-select
          ><el-select
            v-model="minuteValue"
            placeholder="분"
            style="width: 60px; margin-left: 5px"
          >
            <el-option
              v-for="item in minuteOptions"
              :key="item"
              :value="item"
            />
          </el-select>
          <el-button type="primary" @click="book" style="margin-left: 5px"
            >예약하기</el-button
          >
        </div>
      </form>
    </div>
  </div>
  <!-- --------------book end-------------- -->
</template>
<script lang="ts">
import { defineComponent, ref } from "vue";
import { Plus, Promotion } from "@element-plus/icons-vue";

export default defineComponent({
  setup() {
    const timeValue = ref("");
    const hourValue = ref();
    const minuteValue = ref();
    const timeOptions = ["오전", "오후"];
    const hourOptions = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
    const minuteOptions = [0, 10, 20, 30, 40, 50];
    // const timeOptions = ["오전", "오후"];
    const dateValue = ref(new Date());
    const year = ref(dateValue.value.getFullYear());
    const month = ref(dateValue.value.getMonth() + 1);
    const date = ref(dateValue.value.getDate());
    const time = ref("");
    const getDate = () => {
      console.log(dateValue);
      year.value = dateValue.value.getFullYear();
      month.value = dateValue.value.getMonth() + 1;
      date.value = dateValue.value.getDate();
      console.log(date.value);
    };

    const book = () => {
      console.log(
        year.value +
          "년 " +
          month.value +
          "월 " +
          date.value +
          "일 " +
          timeValue.value +
          " " +
          hourValue.value +
          "시 " +
          minuteValue.value +
          "분"
      );
      dateValue.value = new Date();
      timeValue.value = "";
      hourValue.value = "";
      minuteValue.value = "";
    };
    const input = ref("");
    interface chat {
      time: string;
      type: string;
      content: string;
    }
    const chatHistory: chat[] = [];

    chatHistory.push({
      time: "오전 1월 1일 13시 13분",
      type: "send",
      content:
        "hisdfsdfddsfdfsdsdsfsfsfddsfsddsfdfhisdfsdfddhisdfsdfddsfdfsd<br/>sdsfsfsfddsfsddsfdfhisdfsdfddsfdfsdsdsfsfsfddsfsddsfdfsdsdsfsfsfddsfsfsdsdsfsfsfddsfsfsfdfsdsdsfsfsfddsfsddsfdfsdsdsfsfsfddsfsfsdsdsfsfsfddsfsf",
    });
    chatHistory.push({
      time: "오전 2월 2일 15시 15분",
      type: "get",
      content: "hellosfsdfsfffsdffsdff",
    });
    chatHistory.push({
      time: "오전 3월 3일 17시 17분",
      type: "send",
      content: "nice to meet you",
    });
    chatHistory.push({
      time: "오전 3월 3일 17시 17분",
      type: "get",
      content: "nice to meet youzdfgfdgzfgzgfzfgf",
    });

    // const list = ["싸피부동산"];
    const list: string[] = [
      "싸피부동산",
      "에듀부동산",
      "와우정부동산",
      "싸싸부동산",
      "피피부동산",
      "와우정부동산",
      "싸싸부동산",
      "피피부동산",
      "와우정부동산",
      "싸싸부동산",
      "피피부동산",
    ];

    const list_idx = ref(-1);
    const isOpenList = ref(false);
    const isOpenChat = ref(false);
    const isOpenBook = ref(false);

    const OpenChatList = () => {
      isOpenList.value = !isOpenList.value;
    };

    const OpenChat = (index: number) => {
      list_idx.value = index;
      isOpenChat.value = true;
      isOpenList.value = false;
    };

    const CloseChat = () => {
      isOpenChat.value = false;
      isOpenBook.value = false;
    };

    const OpenBook = () => {
      isOpenBook.value = !isOpenBook.value;
    };

    return {
      input,
      list,
      OpenChatList,
      OpenChat,
      OpenBook,
      isOpenList,
      isOpenChat,
      isOpenBook,
      list_idx,
      CloseChat,
      chatHistory,
      Plus,
      Promotion,
      dateValue,
      getDate,
      date,
      year,
      month,
      time,
      timeOptions,
      timeValue,
      hourOptions,
      minuteOptions,
      hourValue,
      minuteValue,
      book,
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
.BookWindow {
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
  height: 309px;
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
