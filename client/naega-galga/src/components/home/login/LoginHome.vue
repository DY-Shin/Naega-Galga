<template>
  <div class="common-layout">
    <el-container>
      <main-side @addr_idx="sendIdx" @productList="sendList"></main-side>
      <el-container>
        <ka-kao-map
          :GetIdx="idxProxy"
          :GetList="listProxy"
          @chatOpen="sendChatOpen"
        ></ka-kao-map>
      </el-container>
    </el-container>
    <chat-window :GetOpen="chatOpenProxy"></chat-window>
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
    let listProxy = ref([]);
    const sendList = (list: []) => {
      listProxy.value.splice(0);
      for (let i = 0; i < list.length; i++) {
        listProxy.value.push(list[i]);
      }
    };
    const chatOpenProxy = ref(false);
    const sendChatOpen = (chatOpen: boolean) => {
      console.log(chatOpen);
      chatOpenProxy.value = chatOpen;
    };

    return {
      sendIdx,
      idxProxy,
      sendList,
      listProxy,
      sendChatOpen,
      chatOpenProxy,
    };
  },
});
</script>

<style scoped></style>
