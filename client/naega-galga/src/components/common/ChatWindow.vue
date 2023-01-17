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
      style="cursor: pointer; left: 400px; position: absolute; top: -30px"
      ><CircleCloseFilled
    /></el-icon>
    <el-scrollbar height="400px">
      <div class="chat-item" v-for="item in chatHistory" :key="item.time">
        <div class="send-box common-box" v-if="item.type == 'send'">
          <div class="box">{{ item.time }}{{ item.content }}</div>
        </div>
        <div class="get-box common-box" v-else-if="item.type == 'get'">
          <div class="box">{{ item.time }}{{ item.content }}</div>
        </div>
      </div>
    </el-scrollbar>
    <div class="message-input" style="display: inline-flex">
      <el-input v-model="input" />
      <el-icon
        style="z-index: 1012; left: 15px; top: 5px"
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
      content: "hi",
    });
    chatHistory.push({
      time: "오전 2월 2일 15시 15분",
      type: "send",
      content: "hello",
    });
    chatHistory.push({
      time: "오전 3월 3일 17시 17분",
      type: "get",
      content: "nice to meet you",
    });
    for (let i = 0; i < 20; i++) {
      chatHistory.push({
        time: "오전 3월 3일 17시 17분",
        type: "get",
        content: "nice to meet you",
      });
    }

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
.message-input {
  z-index: 101;
  position: fixed;
  top: calc(50vh + 160px);
  left: calc(50vw - 100px);
  padding-left: 10px;
  width: 360px;
  height: 40px;
}
.el-scrollbar__view {
  height: 0px;
}
.chat-item {
  text-align: right;
}
.common-box {
  margin: 15px;

  text-align: right;
  border-radius: 25px;
}
.box {
  padding: 10px 15px;
  border-radius: 20px;
  width: fit-content;
}
.get-box {
  float: left;
  background-color: rgb(185, 185, 185);
}
.send-box {
  float: right;
  background-color: rgb(212, 255, 155);
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
  border-radius: 15px;
}
.chat-list {
  box-shadow: 2px 2px 2px 2px rgb(175, 175, 175);
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

/* #chat-window {
  position: fixed;
  bottom: 50px;
  z-index: 11;
  right: 50px;
  width: 200px;
  height: 300px;
} */
</style>
