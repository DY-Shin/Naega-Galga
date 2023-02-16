<template>
  <main-header></main-header>
  <router-view></router-view>
  <chat-window v-if="isLog"></chat-window>
  <main-footer></main-footer>
</template>

<script lang="ts">
import MainHeader from "@/components/common/MainHeader.vue";
import MainFooter from "./components/common/MainFooter.vue";
import { computed } from "vue";
import chatWindow from "@/components/common/ChatWindow.vue";
import { useStore } from "vuex";
export default {
  name: "app",
  components: { MainHeader, chatWindow, MainFooter },
  setup() {
    const store = useStore();
    const isLog = computed(() => store.getters["userStore/isLogin"]);
    store.commit("chatStore/CHANGE_CHATROOM_STATUS", false);
    return { isLog };
  },
};
</script>

<style>
@import "./assets/css/reset.css";
@import "./assets/css/font-family.css";
@import "./assets/css/color.css";

html {
  scroll-behavior: smooth;
}
template::-webkit-scrollbar-thumb {
  height: 30%; /* 스크롤바의 길이 */
  background: #217af4; /* 스크롤바의 색상 */

  border-radius: 10px;
}
</style>
