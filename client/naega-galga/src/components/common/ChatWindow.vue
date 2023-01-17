<template>
  <div id="chat-window"></div>
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
  <el-scrollbar v-show="isOpenList" class="chat-list">
    <div v-for="(item, index) in list" :key="item" class="scrollbar-demo-item">
      <button @click="OpenChat(index)" class="list-item">{{ item }}</button>
    </div>
  </el-scrollbar>
  <div class="chat-room" v-show="isOpenChat">
    <el-icon
      id="close-btn"
      @click="closeChat"
      :size="40"
      color="#FF4444"
      style="cursor: pointer; left: 400px; position: absolute; top: -30px"
      ><CircleCloseFilled
    /></el-icon>
    <el-scrollbar>
      <div
        v-for="item in list2[list_idx]"
        :key="item"
        class="scrollbar-demo-item"
      >
        <button class="list-item">{{ item }}</button>
      </div>
    </el-scrollbar>
  </div>
</template>
<script lang="ts">
import { defineComponent, ref } from "vue";

export default defineComponent({
  setup() {
    // const list = ["싸피부동산"];
    const list: string[] = [
      "싸피부동산",
      "에듀부동산",
      "와우정부동산",
      "싸싸부동산",
      "피피부동산",
    ];

    const list2: string[][] = [
      ["1", "2", "3"],
      ["4", "5", "6"],
      ["7", "8", "9"],
      ["10", "11", "12"],
      ["13", "14", "15"],
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
      isOpenList,
      isOpenChat,
      list,
      list2,
      OpenChatList,
      OpenChat,
      list_idx,
      closeChat,
    };
  },
});
</script>

<style scoped>
.list-item {
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
  height: 600px;
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

#chat-window {
  position: fixed;
  bottom: 50px;
  z-index: 11;
  right: 50px;
  width: 200px;
  height: 300px;
}
</style>
