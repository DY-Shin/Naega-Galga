<template>
  <div id="chat-window"></div>
  <!-- <el-button
    v-on:click="OpenChat"
    id="chat-btn"
    circle
    color="#393B44"
    size="large"
  >
    <el-icon size="40" color="#F1F3F8"><ChatDotRound /></el-icon
  ></el-button> -->
  <el-button id="chat-btn" circle color="#393B44" size="large">
    <el-icon size="40" color="#F1F3F8"><ChatDotRound /></el-icon
  ></el-button>
</template>

<script lang="ts">
export default {
  data() {
    return {
      isOpen: false,
    };
  },
  methods: {
    // OpenChat(): void {
    //   this.isOpen = true;
    // },
  },
};
</script>

<style scoped>
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
