<template>
  <div id="chat-window"></div>
  <el-button
    v-on:click="OpenChat"
    id="chat-btn"
    circle
    color="#393B44"
    size="large"
  >
    <el-icon size="40" color="#F1F3F8"><ChatDotRound /></el-icon
  ></el-button>
</template>

<script lang="ts">
export default {
  methods: {
    OpenChat: function () {
      let easyChat = document.querySelector("#easyChat");
      if (easyChat != null) {
        easyChat.innerHTML = easyChat.textContent + "!!!!";
      }
    },
  },
};
</script>

<style scoped>
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
  /* border: 1px solid red; */
}
</style>
