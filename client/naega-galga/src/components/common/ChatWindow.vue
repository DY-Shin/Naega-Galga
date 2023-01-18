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
      <button
        @click="OpenChat(index)"
        onclick="window.scrollTo(0, 0);"
        class="chat-list-item"
      >
        {{ item }}
      </button>
    </div>
  </el-scrollbar>
  <!-- --------------chat room start-------------- -->
  <div class="chat-room" v-show="isOpenChat">
    <!-- <div class="send-box box">{sd</div> -->
    <el-icon
      id="close-btn"
      @click="closeChat"
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
      <el-scrollbar class="inner">
        <div v-for="item in chatHistory" :key="item.time">
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
      </el-scrollbar>
    </div>

    <div class="message-input" style="display: inline-flex">
      <el-input v-model="input" />
      <el-icon
        style="z-index: 1012; left: 15px; top: 5px; cursor: pointer"
        :size="25"
        color="rgb(121, 121, 121)"
        ><Promotion
      /></el-icon>
    </div>
  </div>
  <!-- --------------chat room end-------------- -->
</template>
<script lang="ts">
import { defineComponent, ref } from "vue";
import { Plus, Promotion } from "@element-plus/icons-vue";

export default defineComponent({
  setup() {
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
    const OpenChatList = () => {
      isOpenList.value = !isOpenList.value;
    };

    const OpenChat = (index: number) => {
      list_idx.value = index;
      isOpenChat.value = true;
      window.scrollTo(0, 0);
    };

    const closeChat = () => {
      isOpenChat.value = false;
    };

    return {
      input,
      isOpenList,
      isOpenChat,
      list,
      OpenChatList,
      OpenChat,
      list_idx,
      closeChat,
      chatHistory,
      Plus,
      Promotion,
    };
  },
});
</script>
<style scoped>
.chat-room {
  margin: 100px;
  z-index: 101;
  position: fixed;
  top: calc(50vh - 350px);
  left: calc(50vw - 200px);
  width: 400px;
  height: 500px;
  background: rgb(255, 255, 255);
  border: 1px solid rgb(184, 184, 184);
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
  border-radius: 15px;
}
.chat-room .inner {
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
  position: fixed;
  top: calc(50vh + 170px);
  left: calc(50vw - 100px);
  padding-left: 10px;
  width: 360px;
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
.el-popper .is-light .el-popover .el-popover--plain {
  background-color: black;
  width: 100px;
}

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
