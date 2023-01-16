<template>
  <main-header></main-header>
  <router-view></router-view>
  <chat-window></chat-window>
</template>

<script lang="ts">
import MainHeader from "@/components/common/MainHeader.vue";
import ChatWindow from "@/components/common/ChatWindow.vue";
export default {
  name: "app",
  components: { MainHeader, ChatWindow },
};
</script>

<style>
html {
  scroll-behavior: smooth;
}
template::-webkit-scrollbar-thumb {
  height: 30%; /* 스크롤바의 길이 */
  background: #217af4; /* 스크롤바의 색상 */

  border-radius: 10px;
}
</style>
