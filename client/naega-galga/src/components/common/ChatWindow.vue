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
  <div v-show="isOpen" class="chat-list">
    <el-scrollbar class="chat-list">
      <div v-for="item in list" :key="item" class="scrollbar-demo-item">
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
    const isOpen = ref(false);
    const OpenChatList = () => {
      isOpen.value = !isOpen.value;
    };

    return {
      isOpen,
      OpenChatList,
      list,
    };
  },
});
</script>

<style scoped>
.list-item :hover {
  /* list-style: none; */ /* width: 100%; */
  background-color: aqua;
}

.list-item {
  font-weight: 700;
  color: rgb(93, 93, 93);
  text-align: left;
  font-size: 15px;
  /* list-style: none; */
  /* width: 100%; */
  border: none;
  background-color: transparent;
  width: 100%;
  padding: 20px 30px;
  border-bottom: 1px solid rgb(163, 163, 163);
}
.chat-list {
  box-shadow: 2px 2px 2px 2px rgb(175, 175, 175);
  position: fixed;
  z-index: 100;
  bottom: 140px;
  right: 50px;
  width: 250px;
  height: 300px;
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
