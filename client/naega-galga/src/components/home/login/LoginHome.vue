<template>
  <div class="common-layout">
    <el-container>
      <main-side
        @addr_idx="sendIdx"
        @productClick="sendClick"
        @productList="sendList"
      ></main-side>
      <el-container>
        <ka-kao-map
          :getIdx="idxProxy"
          :getList="listProxy"
          :getClick="clickProxy"
          @chatUserIndex="sendUserIndex"
          @chatOpen="sendChatOpen"
        ></ka-kao-map>
      </el-container>
    </el-container>
    <chat-window
      :getChatUserIndex="chatUserIndexProxy"
      :getChatOpen="chatOpenProxy"
    ></chat-window>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import MainSide from "@/components/home/login/MainSide.vue";
import KaKaoMap from "@/components/home/login/KaKaoMap.vue";
import ChatWindow from "@/components/common/ChatWindow.vue";

export default defineComponent({
  name: "addressInfo",
  components: { MainSide, KaKaoMap, ChatWindow },

  setup() {
    const idxProxy = ref();
    const sendIdx = (sendIdx: number) => {
      idxProxy.value = sendIdx;
    };
    let clickProxy = ref(false);
    const sendClick = () => {
      clickProxy.value = !clickProxy.value;
    };

    let listProxy = ref([]);
    const sendList = (list: []) => {
      listProxy.value.splice(0);
      for (let i = 0; i < list.length; i++) {
        listProxy.value.push(list[i]);
      }
    };
    const chatUserIndexProxy = ref();
    const sendUserIndex = (product: number) => {
      chatUserIndexProxy.value = product;
    };
    const chatOpenProxy = ref(false);
    const sendChatOpen = (isOpen: boolean) => {
      chatOpenProxy.value = isOpen;
    };

    return {
      sendIdx,
      idxProxy,
      sendList,
      listProxy,
      sendUserIndex,
      sendChatOpen,
      chatOpenProxy,
      sendClick,
      clickProxy,
      chatUserIndexProxy,
    };
  },
});
</script>

<style scoped></style>
