<template>
  <alert-box></alert-box>
  <main-header></main-header>
  <router-view></router-view>
  <chat-window v-if="isLog"></chat-window>
</template>

<script lang="ts">
import MainHeader from "@/components/common/MainHeader.vue";
import { computed } from "vue";
import AlertBox from "@/components/common/AlertComponent.vue";
import chatWindow from "@/components/common/ChatWindow.vue";
import { useStore } from "vuex";
export default {
  name: "app",
  components: { MainHeader, AlertBox, chatWindow },
  setup() {
    const store = useStore();
    const isLog = computed(() => store.getters["userStore/isLogin"]);
    return { isLog };
  },
};
</script>

<style>
@import "./assets/css/reset.css";
html {
  scroll-behavior: smooth;
}
template::-webkit-scrollbar-thumb {
  height: 30%; /* 스크롤바의 길이 */
  background: #217af4; /* 스크롤바의 색상 */

  border-radius: 10px;
}
</style>
